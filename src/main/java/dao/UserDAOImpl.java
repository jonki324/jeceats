package dao;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import common.Constants.ErrorType;
import entity.User;

@Dependent
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    public UserDAOImpl(EntityManager em) {
        super(User.class, em);
    }

    @Override
    public Optional<User> findByLoginIdAndPassword(String loginId, String password) {
        Optional<User> user = Optional.empty();
        String jpql = "SELECT u FROM User u WHERE u.loginId = :loginId AND u.password = :password";
        try {
            user = Optional.ofNullable(this.getEntityManager().createQuery(jpql, User.class)
                    .setParameter("loginId", loginId).setParameter("password", password).getSingleResult());
        } catch (NoResultException | NonUniqueResultException e) {
            user = Optional.empty();
        } catch (Exception e) {
            throw createAppException(ErrorType.PERSISTENCE, e);
        }
        return user;
    }
}
