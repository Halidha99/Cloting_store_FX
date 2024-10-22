package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import service.ServiceFactory;
import service.custom.CustomerService;

import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TableView<Customer> CustomerTable;

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
    private TableColumn<Customer, String> colCusId;

    @FXML
    private TableColumn<Customer, String> colCustEmail;

    @FXML
    private TableColumn<Customer, String> colCustName;

    @FXML
    private TableColumn<Customer, String> colCustomerAddress;

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

  CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        ObservableList<String> cusTitle = FXCollections.observableArrayList("MRS", "MR", "MISS");
        cmbCustomerTittle.setItems(cusTitle);

        CustomerTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        });


       loadTable();
    }

    private void setTextToValues(Customer newValue) {
        txtCustomerId.setText(newValue.getId());
        txtCustomerName.setText(newValue.getName());
        txtCustomerEmail.setText(newValue.getEmail());
        txtCustomerAddress.setText(newValue.getAddress());
        cmbCustomerTittle.setValue(newValue.getTittle());
    }

    private void clear() {
        txtCustomerId.setText(customerService.generateCustomerId());
        txtCustomerName.clear();
        txtCustomerEmail.clear();
        txtCustomerAddress.clear();
        cmbCustomerTittle.getSelectionModel().clearSelection();
    }

 public void loadTable() {
        ObservableList<Customer> customers = customerService.getAllCustomer();

        if (customers != null && !customers.isEmpty()) {
            CustomerTable.setItems(customers);
        } else {
            System.out.println("No customers found or customer list is null.");
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Login-Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/maindash-board-form.fxml"))));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Unable to load the dashboard.");
        }
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
        System.out.println(customer);
        if (!txtCustomerName.getText().isEmpty() && customerService.isValidEmail(txtCustomerEmail.getText()) && !txtCustomerAddress.getText().isEmpty()) {
            boolean isAdd = customerService.addCustomer(customer);
            if (isAdd) {
                showAlert("Customer Added", "Customer Added Successfully..!");
                clear();
                loadTable();
            }
        } else {
            showAlert("Error", "Please fill all fields correctly.");
        }
    }

    @FXML
    void btnCustDeleteOnAction(ActionEvent event) {
        if (!txtCustomerId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDeleted = customerService.deleteCustomerUserById(txtCustomerId.getText());
                if (isDeleted) {
                    showAlert("Customer Deleted", "Customer deleted successfully.");
                    clear();
                    loadTable();
                } else {
                    showAlert("Error", "Failed to delete customer.");
                }
            }
        }
    }

    @FXML
    void btnCustupdateOnAction(ActionEvent event) {
        if (!txtCustomerName.getText().isEmpty() && customerService.isValidEmail(txtCustomerEmail.getText()) && !txtCustomerAddress.getText().isEmpty()) {
            Customer customer = new Customer(
                    txtCustomerId.getText(),
                    cmbCustomerTittle.getValue(),
                    txtCustomerName.getText(),
                    txtCustomerEmail.getText(),
                    txtCustomerAddress.getText()
            );

            boolean isUpdated = customerService.updateCustomer(customer);
            if (isUpdated) {
                showAlert("Customer Updated", "Customer Updated Successfully..!");
                clear();
                loadTable();
            } else {
                showAlert("Error", "Couldn't update customer!");
            }
        } else {
            showAlert("Missing Fields", "Please check your form again..!!!");
        }
    }

    @FXML
    void searchCustOnAction(ActionEvent event) {
        try {
            Customer customer = customerService.searchCustomerByName(txtCustomerName.getText());
            if (customer != null) {
                setTextToValues(customer);
            } else {
                showAlert("Not Found", "Customer not found.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred during the search.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
