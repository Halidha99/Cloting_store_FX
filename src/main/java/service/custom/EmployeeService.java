package service.custom;

import javafx.collections.ObservableList;
import model.Employee;
import service.SuperService;

public interface EmployeeService extends SuperService {
    String generateEmployeeId();
    boolean addEmployee(Employee employee);
    ObservableList getAllEmployee();
    boolean updateEmployee(Employee employee);
    boolean deleteEmployeeById(String text);
    Employee searchEmployeeByName(String name);
}
