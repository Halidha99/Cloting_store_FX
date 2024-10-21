package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXComboBox<?> cmbUserType;

    @FXML
    private Label lblShopName;

    @FXML
    private CheckBox txtShowPass;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtxPassword;

    @FXML
    void LoginOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Main-form");
        stage.setResizable(false);
        try {
            stage.setScene(FXMLLoader.load(getClass().getResource("/view/maindash-board-form.fxml")));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Register-form");
        stage.setResizable(false);
        try {
            stage.setScene(FXMLLoader.load(getClass().getResource("/view/register_form.fxml")));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
