package dao;

import java.util.Optional;

import entity.User;

public interface UserDAO extends BaseDAO<User> {
    public abstract Optional<User> findByLoginIdAndPassword(String loginId, String password);
}
