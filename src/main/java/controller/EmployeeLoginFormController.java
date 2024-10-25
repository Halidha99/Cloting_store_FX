package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeLoginFormController {

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private Label lblShopName;

    @FXML
    private JFXTextField txtEmpName;

    @FXML
    private JFXPasswordField txtEmpPassword;

    @FXML
    private CheckBox txtShowPass;


    private static final String USERNAME_1 = "kumar@gmail.com";
    private static final String PASSWORD_1 = "1234";
    private static final String USERNAME_2 = "nimalir@gmail.com";
    private static final String PASSWORD_2 = "1234";

    @FXML
    void LoginOnAction(ActionEvent event) {
        String username = txtEmpName.getText();
        String password = txtEmpPassword.getText();


        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Username and password cannot be empty.");
            return;
        }


        if (authenticateUser(username, password)) {
            System.out.println("Login successful!");
            loadEmployeeDashboard();
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private boolean authenticateUser(String username, String password) {
        return (username.equals(USERNAME_1) && password.equals(PASSWORD_1)) ||
                (username.equals(USERNAME_2) && password.equals(PASSWORD_2));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadEmployeeDashboard() {
        try {

            Stage currentStage = (Stage) btnSignIn.getScene().getWindow();
            currentStage.close();


            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Employee Dashboard");
            dashboardStage.setResizable(false);
            dashboardStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employee_dashboard_form.fxml"))));
            dashboardStage.show();

        } catch (IOException e) {
            showAlert("Error", "Failed to load the employee dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void showPasswordOnAction(ActionEvent event) {
        if (txtShowPass.isSelected()) {
            txtEmpPassword.setStyle("-fx-opacity: 1;");
            txtEmpPassword.setPromptText("");
        } else {
            txtEmpPassword.setStyle("-fx-opacity: 0;");
        }
    }
}
