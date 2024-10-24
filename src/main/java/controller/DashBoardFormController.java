package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
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

public class DashBoardFormController implements Initializable {

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private BarChart<?, ?> chart;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblInvoice;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblItemAmount;

    @FXML
    private Label lblShopName;

    @FXML
    private Label lblSuppAmount;

    @FXML
    private Label lblSupplier;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblUserType;

    @FXML
    private Label lblinAmount;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
loadDateAndTime();
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addCustomer_form.fxml"));


            AnchorPane root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) btnCustomer.getScene().getWindow();


            stage.setScene(scene);
            stage.setTitle("Customer Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addEmployee_form.fxml"));


            AnchorPane root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) btnEmployee.getScene().getWindow();


            stage.setScene(scene);
            stage.setTitle("Employee Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
    void btnOrderOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/order_form.fxml"));


            AnchorPane root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) btnOrder.getScene().getWindow();


            stage.setScene(scene);
            stage.setTitle("Order Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnReportOnAction(ActionEvent event) {


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


}
