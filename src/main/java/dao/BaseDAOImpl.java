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
import common.Constants.ErrorType;
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
            throw createAppException(ErrorType.ENTITY_EXISTS, e);
        } catch (PersistenceException e) {
            throw createAppException(ErrorType.PERSISTENCE, e);
        }
    }

    public Optional<T> read(Integer id) {
        return Optional.ofNullable(getEntityManager().find(tClass, id));
    }

    public T update(T entity) {
        T updatedEntity = null;
        try {
            updatedEntity = getEntityManager().merge(entity);
            flush();
        } catch (OptimisticLockException e) {
            throw createAppException(ErrorType.OPTIMISTIC_LOCK, e);
        } catch (PersistenceException e) {
            throw createAppException(ErrorType.PERSISTENCE, e);
        }
        return updatedEntity;
    }

    public void delete(T entity) {
        Integer id = entity.getId();
        read(id).ifPresentOrElse(target -> {
            try {
                target.setVersion(entity.getVersion());
                getEntityManager().remove(target);
                flush();
            } catch (OptimisticLockException e) {
                throw createAppException(ErrorType.OPTIMISTIC_LOCK, e);
            } catch (PersistenceException e) {
                throw createAppException(ErrorType.PERSISTENCE, e);
            }
        }, () -> {
            throw createAppException(ErrorType.OPTIMISTIC_LOCK, new OptimisticLockException());
        });
    }

    public List<T> readAll() {
        String jpql = String.format("SELECT t FROM %s t", tClass.getSimpleName());
        List<T> resultList = null;
        try {
            resultList = getEntityManager().createQuery(jpql, tClass).getResultList();
        } catch (PersistenceException e) {
            throw createAppException(ErrorType.PERSISTENCE, e);
        }
        return Objects.isNull(resultList) ? new ArrayList<T>() : resultList;
    }

    public void detach(T entity) {
        getEntityManager().detach(entity);
    }

    public void flush() {
        getEntityManager().flush();
    }

    protected AppException createAppException(ErrorType errorType, Throwable cause) {
        String msg = ResourceBundle.getBundle("messages").getString(errorType.toString());
        return new AppException(errorType, msg, cause);
    }
}
