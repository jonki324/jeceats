package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import config.MessageConfig;
import entity.BaseEntity;
import exception.DBException;
import exception.DBException.ErrorType;

public abstract class BaseDAOImpl<T extends BaseEntity> {
    private Class<T> tClass;

    @PersistenceContext
    private EntityManager em;

    @Inject
    protected MessageConfig msgConfig;

    public BaseDAOImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    public BaseDAOImpl(Class<T> tClass, EntityManager em, MessageConfig msgConfig) {
        this(tClass);
        this.em = em;
        this.msgConfig = msgConfig;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(T entity) {
        try {
            getEntityManager().persist(entity);
            flush();
        } catch (EntityExistsException e) {
            throw createDBException(ErrorType.ENTITY_EXISTS, e);
        } catch (PersistenceException e) {
            throw createDBException(ErrorType.PERSISTENCE, e);
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
            throw createDBException(ErrorType.OPTIMISTIC_LOCK, e);
        } catch (PersistenceException e) {
            throw createDBException(ErrorType.PERSISTENCE, e);
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
                throw createDBException(ErrorType.OPTIMISTIC_LOCK, e);
            } catch (PersistenceException e) {
                throw createDBException(ErrorType.PERSISTENCE, e);
            }
        }, () -> {
            throw createDBException(ErrorType.NOT_EXIST, new IllegalArgumentException());
        });
    }

    public List<T> readAll() {
        String jpql = String.format("SELECT t FROM %s t", tClass.getSimpleName());
        List<T> resultList = null;
        try {
            resultList = getEntityManager().createQuery(jpql, tClass).getResultList();
        } catch (PersistenceException e) {
            throw createDBException(ErrorType.PERSISTENCE, e);
        }
        return Objects.isNull(resultList) ? new ArrayList<T>() : resultList;
    }

    public void detach(T entity) {
        getEntityManager().detach(entity);
    }

    public void flush() {
        getEntityManager().flush();
    }

    protected DBException createDBException(ErrorType errorType, Throwable cause) {
        String msg;
        switch (errorType) {
            case OPTIMISTIC_LOCK:
                msg = msgConfig.OPTIMISTIC_LOCK;
                break;
            case ENTITY_EXISTS:
                msg = msgConfig.ENTITY_EXISTS;
                break;
            case PERSISTENCE:
                msg = msgConfig.PERSISTENCE;
                break;
            case NOT_EXIST:
                msg = msgConfig.NOT_EXIST;
                break;
            case OTHER:
            default:
                msg = msgConfig.OPTIMISTIC_LOCK;
                break;
        }
        return new DBException(msg, cause, errorType);
    }
}
