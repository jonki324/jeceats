package dao;

import java.util.Objects;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import config.MessageConfig;
import entity.Item;

@Dependent
public class ItemDAOImpl extends BaseDAOImpl<Item> implements ItemDAO {

    public ItemDAOImpl() {
        super(Item.class);
    }

    public ItemDAOImpl(EntityManager em, MessageConfig msgConfig) {
        super(Item.class, em, msgConfig);
    }

    @Override
    public Long countByIdAndObjectName(Integer id, String objectName) {
        String jpql = "SELECT COUNT(i) FROM Item i WHERE i.id = :id AND i.objectName = :objectName";
        Long count = this.getEntityManager().createQuery(jpql, Long.class).setParameter("id", id)
                .setParameter("objectName", objectName).getSingleResult();
        return Objects.isNull(count) ? 0 : count;
    }
}
