package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class AddPlaceOrderFormController {

    @FXML
    private JFXComboBox<?> CustomerComboTxt;

    @FXML
    private JFXTextField Discount;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colitemname;

    @FXML
    private TableColumn<?, ?> colprice;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private Pane discount;

    @FXML
    private Label finalTotal;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private Label txtDate;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private Label txtOrderId;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQTY;

    @FXML
    private Label txtQtyCount;

    @FXML
    private Label txtTime;

    @FXML
    private Label txtTotal;

    @FXML
    void CustomerCombo(ActionEvent event) {

    }

    @FXML
    void RemoveOnAction(ActionEvent event) {

    }

    @FXML
    void addCartOnAction(ActionEvent event) {

    }

    @FXML
    void adddiscountKey(KeyEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnComboOnAction(ActionEvent event) {

    }

    @FXML
    void placeOrderOnAction(ActionEvent event) {

    }

    @FXML
    void printBillOnAction(ActionEvent event) {

    }

    @FXML
    void searchItemOnAction(ActionEvent event) {

    }

}
