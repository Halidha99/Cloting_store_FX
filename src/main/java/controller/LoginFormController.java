package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtxPassword;

    @FXML
    private CheckBox txtShowPass;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @FXML
    void LoginOnAction(ActionEvent event) {
        String username = txtUserName.getText().trim();
        String password = txtxPassword.getText().trim();


        if (isAdmin(username, password)) {
            openMainForm();
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private boolean isAdmin(String username, String password) {

        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    private void openMainForm() {
        Stage stage = new Stage();
        stage.setTitle("Main-form");
        stage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/maindash-board-form.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


            Stage currentStage = (Stage) btnSignIn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the main form.");
            e.printStackTrace();
        }
    }

    @FXML
    void showPasswordOnAction(ActionEvent event) {
        if (txtShowPass.isSelected()) {
            txtxPassword.setPromptText(txtxPassword.getText());
            txtxPassword.setText("");
            txtxPassword.setVisible(false);
        } else {
            txtxPassword.setText(txtxPassword.getPromptText());
            txtxPassword.setVisible(true);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnRegisterOnAction(ActionEvent event) {

    }
}
