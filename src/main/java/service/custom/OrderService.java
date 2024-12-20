package service.custom;

import entity.OrderDetailEntity;
import javafx.collections.ObservableList;
import model.Order;
import service.SuperService;

import java.util.List;

public interface OrderService  extends SuperService {
    String generateOrderId();

    boolean addOrder(Order order);


    boolean addOrder(Order order, List<OrderDetailEntity> orderDetailList);

    ObservableList getAllOrders();

    boolean deleteOrderById(String id);

    Order searchOrderByID(String id);
}
