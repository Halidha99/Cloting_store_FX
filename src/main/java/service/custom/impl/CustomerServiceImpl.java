package service.custom.impl;

import entity.CustomerEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import service.custom.CustomerService;
import util.DaoType;

public class CustomerServiceImpl implements CustomerService {

   CustomerDao customerDaoImpl= DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

    @Override
    public String generateCustomerId() {

        String lastCustomerId = customerDaoImpl.getLatestId();
        if (lastCustomerId==null){
            return "C001";
        }
        int number = Integer.parseInt(lastCustomerId.split("C")[0]);
        number++;
        return String.format("C%03d", number);
    }

    @Override
    public boolean addCustomer(Customer customer) {

       Customer customerEntity= new ObjectMapper().convertValue(customer, Customer.class);
        return customerDaoImpl.save(customerEntity);
    }

    @Override
    public ObservableList getAllCustomer() {

        ObservableList<CustomerEntity> list = customerDaoImpl.getAll();
        ObservableList<Customer> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,Customer.class));
        });
        return userList;
    }

    @Override
    public boolean updateCustomer(Customer customer) {

        Customer customerEntity = new ObjectMapper().convertValue(customer, Customer.class);
        return customerDaoImpl.update(customerEntity);
    }

    @Override
    public boolean deleteCustomerUserById(String text) {

        return customerDaoImpl.delete(text);
    }

    @Override
    public Customer searchCustomerByName(String name) {
        Customer customerEntity = customerDaoImpl.searchByName(name);
        return new ObjectMapper().convertValue(customerEntity,Customer.class);
    }

    @Override
    public Customer searchItemByID(String id) {

        Customer customerEntity = customerDaoImpl.search(id);
        return new ObjectMapper().convertValue(customerEntity, Customer.class);
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }


        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }


        if (atIndex == 0) {
            return false;
        }


        String domain = email.substring(atIndex + 1);


        int dotIndex = domain.indexOf('.');
        if (dotIndex == -1 || dotIndex == 0 || dotIndex == domain.length() - 1) {
            return false;
        }


        if (email.contains(" ")) {
            return false;
        }

        return true;
    }
}
