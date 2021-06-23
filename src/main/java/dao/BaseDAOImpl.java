package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import common.AppException;
import common.Constants.DBErrorType;
import entity.BaseEntity;

public abstract class BaseDAOImpl<T extends BaseEntity> {
    private Class<T> tClass;

    @PersistenceContext
    private EntityManager em;

    public BaseDAOImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    public BaseDAOImpl(Class<T> tClass, EntityManager em) {
        this(tClass);
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(T entity) {
        try {
            getEntityManager().persist(entity);
            flush();
        } catch (EntityExistsException e) {
            throw createAppException(DBErrorType.ENTITY_EXISTS, e);
        } catch (PersistenceException e) {
            throw createAppException(DBErrorType.PERSISTENCE, e);
        }
    }

    public Optional<T> read(Integer id) {
        return Optional.ofNullable(getEntityManager().find(tClass, id));
    }

    public T update(T entity) {
        Integer id = entity.getId();
        T target = read(id)
                .orElseThrow(() -> createAppException(DBErrorType.OPTIMISTIC_LOCK, new OptimisticLockException()));
        T updatedEntity = null;
        try {
            entity.setVersion(target.getVersion());
            updatedEntity = getEntityManager().merge(entity);
            flush();
        } catch (OptimisticLockException e) {
            throw createAppException(DBErrorType.OPTIMISTIC_LOCK, e);
        } catch (PersistenceException e) {
            throw createAppException(DBErrorType.PERSISTENCE, e);
        }
        return updatedEntity;
    }

    public void delete(T entity) {
        Integer id = entity.getId();
        read(id).ifPresentOrElse(target -> {
            try {
                entity.setVersion(target.getVersion());
                getEntityManager().remove(entity);
                flush();
            } catch (OptimisticLockException e) {
                throw createAppException(DBErrorType.OPTIMISTIC_LOCK, e);
            } catch (PersistenceException e) {
                throw createAppException(DBErrorType.PERSISTENCE, e);
            }
        }, () -> {
            throw createAppException(DBErrorType.OPTIMISTIC_LOCK, new OptimisticLockException());
        });
    }

    public List<T> readAll() {
        String jpql = String.format("SELECT t FROM %s t", tClass.getSimpleName());
        List<T> resultList = null;
        try {
            resultList = getEntityManager().createQuery(jpql, tClass).getResultList();
        } catch (PersistenceException e) {
            throw createAppException(DBErrorType.PERSISTENCE, e);
        }
        return Objects.isNull(resultList) ? new ArrayList<T>() : resultList;
    }

    public void detach(T entity) {
        getEntityManager().detach(entity);
    }

    public void flush() {
        getEntityManager().flush();
    }

    protected AppException createAppException(DBErrorType errorType, Throwable cause) {
        String msg = ResourceBundle.getBundle("messages").getString(errorType.toString());
        return new AppException(msg, cause);
    }
}
