package dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import entity.Item;

@Dependent
public class ItemDAOImpl extends BaseDAOImpl<Item> implements ItemDAO {

    public ItemDAOImpl() {
        super(Item.class);
    }

    public ItemDAOImpl(EntityManager em) {
        super(Item.class, em);
    }
}
