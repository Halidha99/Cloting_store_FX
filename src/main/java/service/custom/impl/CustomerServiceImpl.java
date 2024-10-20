package service.custom.impl;


import javafx.collections.ObservableList;
import model.Customer;
import service.custom.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public String generateCustomerId() {
        return "";
    }

    @Override
    public boolean addCustomer(Customer customer) {
        return false;
    }

    @Override
    public ObservableList getAllCustomer() {
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomerUserById(String text) {
        return false;
    }

    @Override
    public Customer searchCustomerByName(String name) {
        return null;
    }

    @Override
    public Customer searchItemByID(String id) {
        return null;
    }
}
