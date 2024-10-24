package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt; // Ensure BCrypt is available

public class LoginFormController {

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXComboBox<String> cmbUserType;

    @FXML
    private Label lblShopName;

    @FXML
    private CheckBox txtShowPass;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtxPassword;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clothingshop";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    // Predefined admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @FXML
    void LoginOnAction(ActionEvent event) {
        String username = txtUserName.getText();
        String password = txtxPassword.getText();


        if (isAdmin(username, password)) {
            openMainForm();
        } else if (authenticateUser(username, password)) {
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


    private boolean authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT password FROM employees WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Register-form");
        stage.setResizable(false);
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/register_form.fxml")))); // Ensure Scene is instantiated correctly
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the registration form.");
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
}
