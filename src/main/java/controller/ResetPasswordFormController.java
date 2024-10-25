package controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Employee;
import service.ServiceFactory;
import service.custom.EmployeeService;
import util.ServiceType;
import util.Validator;

public class ResetPasswordFormController{

    @FXML
    private JFXPasswordField password1;

    @FXML
    private JFXPasswordField password2;
    private Employee employee;
    EmployeeService  employeeService= ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
    Validator validator=new Validator();
    @FXML
    void resetPasswordOnAction(ActionEvent event) {
        if (password1.getText()==null&&password2.getText()==null){
            new Alert(Alert.AlertType.INFORMATION, "Eenter  password").show();
        }else {
            if (password1.getText().equals(password2.getText())){
                this.employee.setPassword(validator.hashPassword(password1.getText()));
                employeeService.updateEmployee(this.employee);
                new Alert(Alert.AlertType.INFORMATION, "Login Success").show();
                Stage stage0 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage0.close();
            }else {
                new Alert(Alert.AlertType.ERROR, "Enter Password Correctly").show();
            }
        }
    }
    public void init(Employee employee){
        this.employee=employee;
        System.out.println(employee);
    }

}
