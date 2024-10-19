package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddSupplierFormController {

    @FXML
    private TableView<?> SupplierTable;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<?> cmbItem;

    @FXML
    private JFXComboBox<?> cmbSupTittle;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colSuppCompany;

    @FXML
    private TableColumn<?, ?> colSuppId;

    @FXML
    private TableColumn<?, ?> colSupplierEmail;

    @FXML
    private TableColumn<?, ?> colSuppliername;

    @FXML
    private TableColumn<?, ?> colsuppMobile;

    @FXML
    private JFXTextField txtSuppCmpany;

    @FXML
    private JFXTextField txtSuppEmail;

    @FXML
    private JFXTextField txtSuppId;

    @FXML
    private JFXTextField txtSuppName;

    @FXML
    private JFXTextField txtSupplierMob;

    @FXML
    private TextField txtxSearch;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnupdateOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(ActionEvent event) {

    }

}
