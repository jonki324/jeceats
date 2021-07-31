package dao;

import entity.Item;

public interface ItemDAO extends BaseDAO<Item> {
    public abstract Long countByIdAndObjectName(Integer id, String objectName);
}
