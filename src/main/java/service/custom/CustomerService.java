package service.custom;

import javafx.collections.ObservableList;
import model.Customer;
import service.SuperService;

public interface CustomerService extends SuperService {
    String generateCustomerId();
    boolean addCustomer(Customer customer);
    ObservableList getAllCustomer();
    boolean updateCustomer(Customer customer);
    boolean deleteCustomerUserById(String text);
    Customer searchCustomerByName(String name);
    Customer searchItemByID(String id);

    boolean isValidEmail(String text);
}
