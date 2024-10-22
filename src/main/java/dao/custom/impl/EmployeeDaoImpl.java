package dao.custom.impl;

import dao.custom.EmployeeDao;
import entity.EmployeeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class EmployeeDaoImpl  implements EmployeeDao {
    @Override
    public EmployeeEntity search(String id) {
        Session session = HibernateUtil.getSession();
        return session.get(EmployeeEntity.class, id);
    }

    @Override
    public ObservableList<EmployeeEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<EmployeeEntity> userList = session.createQuery("FROM employee").list();
        ObservableList<EmployeeEntity> list= FXCollections.observableArrayList();
        session.close();
        userList.forEach(userEntity -> {
            list.add(userEntity);
        });
        return list;
    }

    @Override
    public boolean save(EmployeeEntity employeeEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(employeeEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(EmployeeEntity employeeEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(employeeEntity.getId(),employeeEntity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(session.get(EmployeeEntity.class,id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public EmployeeEntity searchByName(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction();
        Query query = session.createQuery("FROM employee WHERE name=:name");
        query.setParameter("name",name);
        EmployeeEntity userEntity = (EmployeeEntity) query.uniqueResult();
        session.close();
        return userEntity;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT id FROM employee ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }
}
