package dao.custom.impl;

import entity.CustomerEntity;
import dao.custom.CustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


    @Override
    public CustomerEntity search(String id) {
        Session session = HibernateUtil.getSession();
        return session.get(CustomerEntity.class, id);
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<CustomerEntity> customerList = session.createQuery("FROM customer").list();
        ObservableList<CustomerEntity> list= FXCollections.observableArrayList();
        session.close();
        customerList.forEach(customerEntity -> {
            list.add(customerEntity);
        });
        return list;
    }

    @Override
    public boolean save(CustomerEntity customerEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(customerEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(CustomerEntity customerEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(customerEntity.getId(),customerEntity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(session.get(CustomerEntity.class,id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public CustomerEntity searchByName(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction();

        Query query = session.createQuery("FROM customer WHERE name=:name");
        query.setParameter("name",name);
        CustomerEntity cusEntity = (CustomerEntity) query.uniqueResult();
        session.close();
        return cusEntity;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }
}
