package controller;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class AddRegisterFormController {

    @FXML
    private TextField address;

    @FXML
    private JFXButton btnRegister;

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


    public static void main(String[] args) {
        registerUser("kumar@gmail.com", "1234");
        registerUser("Nimalir@gmail.com", "1234");
    }

    private static void registerUser(String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO employees (email, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, hashedPassword);
                statement.executeUpdate();
                System.out.println("User registered: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

