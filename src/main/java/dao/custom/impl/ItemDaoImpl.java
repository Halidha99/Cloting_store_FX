package dao.custom.impl;

import dao.custom.ItemDao;
import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ItemDaoImpl  implements ItemDao {
    @Override
    public ItemEntity search(String id) {
        Session session = HibernateUtil.getSession();
        return session.get(ItemEntity.class, id);
    }

    @Override
    public ObservableList<ItemEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<ItemEntity> userList = session.createQuery("FROM item").list();
        ObservableList<ItemEntity> list= FXCollections.observableArrayList();
        session.close();
        userList.forEach(userEntity -> {
            list.add(userEntity);
        });
        return list;
    }

    @Override
    public boolean save(ItemEntity itemEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(itemEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(ItemEntity itemEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(itemEntity.getId(),itemEntity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(session.get(ItemEntity.class,id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public ItemEntity searchByName(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction();
        Query query = session.createQuery("FROM item WHERE name=:name");
        query.setParameter("name",name);
        ItemEntity itemEntity = (ItemEntity) query.uniqueResult();
        session.close();
        return itemEntity;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT id FROM item ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }
}
