package service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.OrderDao;
import entity.CustomerEntity;
import entity.OrderDetailEntity;
import entity.OrderEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Order;
import model.OrderTablecart;
import service.custom.OrderService;
import util.DaoType;

import java.util.List;

public class OrderServiceImpl implements OrderService {

 OrderDao orderDao = DaoFactory.getInstance().getDaoType(DaoType.ORDER);


    @Override
    public String generateOrderId() {
        String lastItemId = orderDao.getLatestId();
        if (lastItemId==null){
            return "OR001";
        }
        int number = Integer.parseInt(lastItemId.split("OR")[1]);
        number++;
        return String.format("OR%03d", number);
    }

    @Override
    public boolean addOrder(Order order) {
       return  true;
    }

    @Override
    public boolean addOrder(Order order, List<OrderDetailEntity> orderDetailList) {
        System.out.println(order);
        CustomerEntity customer=new ObjectMapper().convertValue(order.getCustomer(),CustomerEntity.class);
        OrderEntity orderEntity = new OrderEntity();


        for(OrderDetailEntity orderDetailEntity:orderDetailList){
            orderDetailEntity.setOrder(orderEntity);
            System.out.println(orderDetailEntity);
        }
        orderEntity.setOrderDetails(orderDetailList);
        return orderDao.save(orderEntity);
    }

    @Override
    public ObservableList getAllOrders() {
        ObservableList<OrderEntity> list = orderDao.getAll();

        ObservableList<OrderTablecart> orderList = FXCollections.observableArrayList();
        list.forEach(i -> {
            orderList.add(new OrderTablecart(i.getId(),i.getDate(),i.getNetTotal(),i.getCustomer().getId()));
        });


        return orderList;
    }

    @Override
    public boolean deleteOrderById(String id) {
        return orderDao.delete(id);
    }

    @Override
    public Order searchOrderByID(String id) {
        OrderEntity orderEntity = orderDao.search(id);
        return new ObjectMapper().convertValue(orderEntity,Order.class);
    }
}
