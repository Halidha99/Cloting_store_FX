package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeDashBoardFormController implements Initializable {

    @FXML
    private JFXButton btnItem;
    @FXML
    private JFXButton btnlogOutOnAction;
    @FXML
    private JFXButton btnOrDetail;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblShopName;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserType1;

    @FXML
    private Label lblUserType11;

    @FXML
    private Label lblUserType111;

    @FXML
    private Label lblUserType1111;

    @FXML
    private Label lblUserType12;

    @FXML
    private Label lblUserType121;

    @FXML
    void btnItemOnAction(ActionEvent event) {
        try {
            System.out.println("Attempting to load FXML...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addItem_form.fxml"));

            AnchorPane root = loader.load();
            System.out.println("FXML Loaded");

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnItem.getScene().getWindow();
            if (stage == null) {
                System.out.println("Stage is null");
            }

            stage.setScene(scene);
            stage.setTitle("Item Form");
            stage.show();

            System.out.println("New scene set and shown.");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @FXML
    void btnOrderDetailOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        try {
            URL resource = getClass().getResource("/view/placeOrder_form.fxml");
            if (resource == null) {
                System.out.println("FXML file not found!");
                return;
            }
            FXMLLoader loader = new FXMLLoader(resource);
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnOrder.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Order Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the order form. Please check the FXML file and its path.");
        }

    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void btnReturnOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addSupplier_form.fxml"));


            AnchorPane root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) btnSupplier.getScene().getWindow();


            stage.setScene(scene);
            stage.setTitle("Supplier Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnlogOutOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/employee_login_form.fxml"));
            Parent loginRoot = loader.load();


            Stage currentStage = (Stage) btnlogOutOnAction.getScene().getWindow();


            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(loginRoot, 400, 300));
            loginStage.setTitle("Employee Login");


            currentStage.hide();
            loginStage.show();
        } catch (Exception e) {
            showAlert("Error", "Could not load login screen.");
            e.printStackTrace();
        }
    }
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = f.format(date);

        lblDate.setText(dateNow);

//-------------------------------------------------------------------------------

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
    }
}
