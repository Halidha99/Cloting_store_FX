package service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;

import dao.custom.EmployeeDao;
import entity.EmployeeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;
import service.custom.EmployeeService;
import util.DaoType;

public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDao employeeDaoImpl= DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
    @Override
    public String generateEmployeeId() {

        String lastEmployeeId = employeeDaoImpl.getLatestId();
        if (lastEmployeeId==null){
            return "E0001";
        }
        int number = Integer.parseInt(lastEmployeeId.split("E")[1]);
        number++;
        return String.format("E%03d", number);
    }

    @Override
    public boolean addEmployee(Employee employee) {
        EmployeeEntity userEntity = new ObjectMapper().convertValue(employee, EmployeeEntity.class);
        return employeeDaoImpl.save(userEntity);
    }

    @Override
    public ObservableList getAllEmployee() {
        ObservableList<EmployeeEntity> list = employeeDaoImpl.getAll();
        ObservableList<Employee> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,Employee.class));
        });
        return userList;
    }

    @Override
    public boolean updateEmployee(Employee employee) {

        EmployeeEntity userEntity = new ObjectMapper().convertValue(employee, EmployeeEntity.class);
        return employeeDaoImpl.update(userEntity);
    }

    @Override
    public boolean deleteEmployeeById(String text) {

        return employeeDaoImpl.delete(text);
    }

    @Override
    public Employee searchEmployeeByName(String name) {

        EmployeeEntity employeeEntity = employeeDaoImpl.searchByName(name);
        return new ObjectMapper().convertValue(employeeEntity,Employee.class);
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
