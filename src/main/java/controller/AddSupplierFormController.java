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
import model.Supplier;
import service.ServiceFactory;

import service.custom.SupplierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddSupplierFormController implements Initializable {

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
    private JFXComboBox<String> cmbSupTittle;

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
   SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSuppId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSuppliername.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSuppCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colsuppMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        ObservableList<String> supTitle = FXCollections.observableArrayList("MRS", "MR", "MISS");
        cmbSupTittle.setItems(supTitle);
        SupplierTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues((Supplier) newValue);
            }
        }));

       SupplierTable.setItems(supplierService.getAllSupplier());
        txtSuppId.setText(supplierService.generateSupplierId());
        refreshTable();

    }

    private void setTextToValues(Supplier newValue) {
        txtSuppId.setText(newValue.getId());
        txtSuppName.setText(newValue.getName());
       txtSuppCmpany.setText(newValue.getEmail());
        txtSupplierMob.setText(newValue.getMobile());
        txtSuppEmail.setText(newValue.getCompany());
    }
    private void refreshTable(){
        SupplierTable.setItems(supplierService.getAllSupplier());
    }

    private void clear(){
        txtSuppId.setText(supplierService.generateSupplierId());
        txtSuppName.setText("");
        txtSuppCmpany.setText("");
        txtSupplierMob.setText("");
       txtSuppEmail.setText("");
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        Supplier supplier = new Supplier(
                txtSuppId.getText(),
                cmbSupTittle.getValue(),
                txtSuppName.getText(),
                txtSuppCmpany.getText(),
                txtSupplierMob.getText(),
                txtSuppEmail.getText()
        );
        if (!txtSuppName.getText().equals("") && supplierService.isValidEmail(txtSuppEmail.getText()) && !txtSupplierMob.getText().equals("")) {
            System.out.println(supplier.toString());
            boolean isInsert = supplierService.addSupplier(supplier);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Supplier Added Successfully..!");
                alert.showAndWait();
                clear();
                refreshTable();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier Not Added..!!!").show();
        }

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Dash-Form");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/maindash-board-form.fxml"))));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Unable to load the dashboard.");
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting");
        alert.setContentText("Are you  want to delete this Supplier");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()== ButtonType.OK){
            boolean isDeleted = supplierService.deleteSupplierById(txtSuppId.getText());
            if (isDeleted){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Supplier Deleted");
                alert2.setContentText("Supplier deleted successfully");
                alert2.showAndWait();
                clear();
                refreshTable();
            }
        }

    }

    @FXML
    void btnupdateOnAction(ActionEvent event) {
        if (!txtSuppName.getText().equals("")  && supplierService.isValidEmail(txtSuppEmail.getText()) && !txtSupplierMob.getText().equals("")){
            Supplier supplier = new Supplier(
                   txtSuppId.getText(),
                    cmbSupTittle.getValue(),
                    txtSuppName.getText(),
                    txtSuppCmpany.getText(),
                   txtSupplierMob.getText(),
                    txtSuppEmail.getText()
            );

            boolean isAdd = supplierService.updateSupplier(supplier);
            if (isAdd) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier update");
                alert.setContentText("Supplier Updated Successfully..!");
                alert.showAndWait();
                clear();
                refreshTable();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Couldn't update!");
                alert.showAndWait();
                clear();
                refreshTable();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }


    }

    @FXML
    void searchOnAction(ActionEvent event) {
        try {
            Supplier supplier = supplierService.searchSupplierByName(txtSuppName.getText());
            if (supplier!=null){
                txtSuppId.setText(supplier.getId());
                txtSuppName.setText(supplier.getName());
                txtSuppCmpany.setText(supplier.getEmail());
               txtSupplierMob.setText(supplier.getMobile());
                txtSuppEmail.setText(supplier.getCompany());
            }
        } catch (Exception e) {
            System.out.println("not found");
        }

    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
