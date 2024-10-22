package dao.custom.impl;

import entity.CustomerEntity;
import dao.custom.CustomerDao;
import javafx.collections.ObservableList;
import model.Customer;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public Customer search(String s) {
        return null;
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        return null;
    }

    @Override
    public boolean save(Customer customer) {
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public Customer searchByName(String s) {
        return null;
    }

    @Override
    public String getLatestId() {
        return "";
    }
}
