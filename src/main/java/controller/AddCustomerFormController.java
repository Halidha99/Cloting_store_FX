package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import service.ServiceFactory;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

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
    private JFXComboBox<String> cmbCustomerTittle;

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

   //ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustomerTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues((Customer) newValue);
            }
        }));
        ObservableList<String> cusTittle = FXCollections.observableArrayList();
        cusTittle.add("MRS");
        cusTittle.add("MR");
        cusTittle.add("MISS");
        cmbCustomerTittle.setItems(cusTittle);
    }

    private void setTextToValues(Customer newValue) {
        txtCustomerId.setText(newValue.getId());
        txtCustomerName.setText(newValue.getName());
        txtCustomerEmail.setText(newValue.getEmail());
        txtCustomerAddress.setText(newValue.getAddress());
    }

    private void clear(){
        //txtCusID.setText(customerBoImpl.generateCustomerId());
        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerAddress.setText("");
    }
    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
   public void btnCustAddOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtCustomerId.getText(),
                cmbCustomerTittle.getValue(),
                txtCustomerName.getText(),
                txtCustomerEmail.getText(),
                txtCustomerAddress.getText()


        );


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
