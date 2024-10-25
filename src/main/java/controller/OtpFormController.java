package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Employee;
import service.ServiceFactory;
import service.custom.EmployeeService;
import service.custom.UserService;
import service.custom.impl.UserServiceImpl;
import util.OtpUtil;
import util.ServiceType;
import util.Validator;

import java.io.IOException;

public class OtpFormController {

    @FXML
    private JFXPasswordField txtCode;

    @FXML
    private JFXTextField txtEmail;
    UserService userService=new UserServiceImpl();
   Validator validator=new Validator();
   EmployeeService employeeService= ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
    private String email=null;
    private String otp=null;
    private Employee employee=null;
    @FXML
    void sendOtpBtnAddOnaction(ActionEvent event) {
        if (validator.isValidEmail(txtEmail.getText())){
            employee= employeeService.searchUserByEmail(txtEmail.getText());
            if (employee==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("User not found");
                alert.showAndWait();
                return;
            }
            this.email=txtEmail.getText();
            otp = OtpUtil.generateOTP();
            userService.storeOTP(email, otp);
            System.out.println("Generated OTP: " + otp);
            boolean emailSent = userService.sendOTPEmail(email, otp);
            if (emailSent) {
                new Alert(Alert.AlertType.INFORMATION, "OTP has been sent to your email.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to send OTP. Please try again.").show();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Enter valid Email");
            alert.showAndWait();
        }
    }

    @FXML
    void submitBtnOnAction(ActionEvent event) {
        if (otp.equals(txtCode.getText().trim())){
            try {
                Stage stage0 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage0.close();
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPasswordForm.fxml"));
                Parent root = loader.load();
                ResetPasswordFormController controller = loader.getController();
                controller.init(employee);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Wrong OTP Code").show();
            txtCode.setText("");
        }
    }

}
