package service.custom.impl;

import javafx.collections.ObservableList;
import model.Employee;
import service.custom.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public String generateEmployeeId() {
        return "";
    }

    @Override
    public boolean addEmployee(Employee employee) {
        return false;
    }

    @Override
    public ObservableList getAllEmployee() {
        return null;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean deleteEmployeeById(String text) {
        return false;
    }

    @Override
    public Employee searchEmployeeByName(String name) {
        return null;
    }
}
