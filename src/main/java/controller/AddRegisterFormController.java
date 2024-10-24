package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt; // Add this import for BCrypt hashing

public class AddRegisterFormController {

    @FXML
    private TextField address; // Fixed spelling: 'adress' to 'address'

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private PasswordField checkpassword;

    @FXML
    private TextField contact;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private AnchorPane register;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clothingshop";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    @FXML
    void LoginOnAction(ActionEvent event) {

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        if (validateFields()) {
            String empName = name.getText();
            String empEmail = email.getText();
            String empPassword = hashPassword(password.getText());
            String empContact = contact.getText();
            String empAddress = address.getText();

            if (saveEmployeeDetails(empName, empEmail, empPassword, empContact, empAddress)) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Employee registered successfully.");
                LoginOnAction(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "An error occurred while registering the employee.");
            }
        }
    }

    private boolean validateFields() {
        if (name.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty() ||
                checkpassword.getText().isEmpty() || contact.getText().isEmpty() || address.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields must be filled out.");
            return false;
        }

        if (!isValidEmail(email.getText())) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a valid email address.");
            return false;
        }

        if (!password.getText().equals(checkpassword.getText())) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Passwords do not match.");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    private boolean saveEmployeeDetails(String name, String email, String password, String contact, String address) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO employees (name, email, password, contact, address) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, contact);
                statement.setString(5, address);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
