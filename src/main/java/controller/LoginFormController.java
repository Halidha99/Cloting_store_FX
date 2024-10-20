package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

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

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

    }

}
