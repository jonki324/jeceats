package dao;

import javax.persistence.EntityManager;

import entity.Item;

public class ItemDAOImpl extends BaseDAOImpl<Item> implements ItemDAO {

    public ItemDAOImpl() {
        super(Item.class);
    }

    public ItemDAOImpl(EntityManager em) {
        super(Item.class, em);
    }
}
