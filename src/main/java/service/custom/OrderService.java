package service.custom;

import javafx.collections.ObservableList;
import model.Order;
import service.SuperService;

public interface OrderService  extends SuperService {
    String generateOrderId();
    boolean addOrder(Order order);
    ObservableList getAllOrders();
    boolean deleteOrderById(String text);
    Order searchOrderByID(String id);
}
