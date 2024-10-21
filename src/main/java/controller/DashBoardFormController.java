package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {

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

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        Stage stage=new Stage();
        stage.setTitle("Customer Form");
        try {
            stage.setScene(FXMLLoader.load(getClass().getResource("/view/addCustomer_form.fxml")));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnlogOutOnAction(ActionEvent event) {

    }

}
