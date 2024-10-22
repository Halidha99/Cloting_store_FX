package dao.custom.impl;

import dao.custom.SupplierDao;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public SupplierEntity search(String id) {
        Session session = HibernateUtil.getSession();
        return session.get(SupplierEntity.class, id);
    }

    @Override
    public ObservableList<SupplierEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<SupplierEntity> userList = session.createQuery("FROM supplier").list();
        ObservableList<SupplierEntity> list= FXCollections.observableArrayList();
        session.close();
        userList.forEach(userEntity -> {
            list.add(userEntity);
        });
        return list;
    }

    @Override
    public boolean save(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(supplierEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(supplierEntity.getId(),supplierEntity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(session.get(SupplierEntity.class,id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public SupplierEntity searchByName(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction();
        Query query = session.createQuery("FROM supplier WHERE name=:name");
        query.setParameter("name",name);
        SupplierEntity userEntity = (SupplierEntity) query.uniqueResult();
        session.close();
        return userEntity;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT id FROM supplier ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }
}
