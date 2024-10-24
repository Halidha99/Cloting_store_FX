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

        if (lastCustomerId == null || lastCustomerId.isEmpty()) {

            return "C001";
        }

        try {

            String[] parts = lastCustomerId.split("C");

            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new NumberFormatException("Invalid customer ID format.");
            }


            int number = Integer.parseInt(parts[1]);
            number++;


            return String.format("C%03d", number);
        } catch (NumberFormatException e) {

            e.printStackTrace();
            throw new RuntimeException("Failed to generate customer ID from: " + lastCustomerId);
        }
    }


    @Override
    public boolean addCustomer(Customer customer) {

        CustomerEntity userEntity = new ObjectMapper().convertValue(customer, CustomerEntity.class);
        return customerDaoImpl.save(userEntity);
    }

    @Override

    public ObservableList<Customer> getAllCustomer() {
        ObservableList<CustomerEntity> entityList = customerDaoImpl.getAll();
        ObservableList<Customer> cusList = FXCollections.observableArrayList();

        if (entityList != null) {
            entityList.forEach(custEntity -> {
                if (custEntity != null) {
                    Customer customer = new ObjectMapper().convertValue(custEntity, Customer.class);
                    cusList.add(customer);
                }
            });
        } else {
            System.out.println("Customer list is null.");
        }

        return cusList;
    }


    @Override
    public boolean updateCustomer(Customer customer) {

        CustomerEntity userEntity = new ObjectMapper().convertValue(customer, CustomerEntity.class);
        return customerDaoImpl.update(userEntity);
    }

    @Override
    public boolean deleteCustomerUserById(String text) {

        return customerDaoImpl.delete(text);
    }

    @Override
    public Customer searchCustomerByName(String name) {

        CustomerEntity customerEntity = customerDaoImpl.searchByName(name);
        return new ObjectMapper().convertValue(customerEntity,Customer.class);
    }

    @Override
    public Customer searchItemByID(String id) {

        CustomerEntity customerEntity = customerDaoImpl.search(id);
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
