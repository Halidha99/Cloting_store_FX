package dao.custom.impl;

import dao.custom.OrderDao;
import entity.OrderEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public OrderEntity search(String id) {
        Session session = HibernateUtil.getSession();
        return session.get(OrderEntity.class, id);
    }

    @Override
    public ObservableList<OrderEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        List<OrderEntity> orderList = session.createQuery("FROM orders").list();
        for (OrderEntity i:orderList){
            i.setOrderDetails(null);
        }
        ObservableList<OrderEntity> list= FXCollections.observableArrayList();
        session.close();
        orderList.forEach(orderItem -> {
            list.add(orderItem);
        });
        return list;
    }

    @Override
    public boolean save(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(orderEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(orderEntity.getId(),orderEntity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(session.get(OrderEntity.class,id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public OrderEntity searchByName(String s) {

        return null;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT id FROM orders ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }
}
