package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class clothingmainFormController {

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnEmployeee;

    @FXML
    private Label lblShopName;

    @FXML
    void btnAdminOnAction(ActionEvent event) {
        try {
            System.out.println("Attempting to load FXML...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));

            AnchorPane root = loader.load();
            System.out.println("FXML Loaded");

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnAdmin.getScene().getWindow();
            if (stage == null) {
                System.out.println("Stage is null");
            }

            stage.setScene(scene);
            stage.setTitle("Main ");
            stage.show();
            System.out.println("New scene set and shown.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        try {
            System.out.println("Attempting to load FXML...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employee_login_form.fxml"));

            AnchorPane root = loader.load();
            System.out.println("FXML Loaded");

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnEmployeee.getScene().getWindow();
            if (stage == null) {
                System.out.println("Stage is null");
            }

            stage.setScene(scene);
            stage.setTitle("Main ");
            stage.show();
            System.out.println("New scene set and shown.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
