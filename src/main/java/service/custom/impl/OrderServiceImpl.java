package service.custom.impl;

import javafx.collections.ObservableList;
import model.Order;
import service.custom.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public String generateOrderId() {
        return "";
    }

    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    @Override
    public ObservableList getAllOrders() {
        return null;
    }

    @Override
    public boolean deleteOrderById(String text) {
        return false;
    }

    @Override
    public Order searchOrderByID(String id) {
        return null;
    }
}
