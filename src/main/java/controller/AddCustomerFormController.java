package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddCustomerFormController {

    @FXML
    private TableView<?> CustomerTable;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<?> cmbCustomerTittle;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colCustEmail;

    @FXML
    private TableColumn<?, ?> colCustName;

    @FXML
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerEmail;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private TextField txtxSearch;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustupdateOnAction(ActionEvent event) {

    }

    @FXML
    void searchCustOnAction(ActionEvent event) {

    }

}
