package dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    public abstract void create(T entity);

    public abstract Optional<T> read(Integer id);

    public abstract T update(T entity);

    public abstract void delete(T entity);

    public abstract List<T> readAll();

    public abstract void detach(T entity);

    public abstract void flush();
}
