package controller;

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
import model.Employee;
import model.Item;
import model.Supplier;
import service.ServiceFactory;
import service.custom.ItemService;
import service.custom.SupplierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {

    @FXML
    private TableView<Item> ItemTable;

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbPrdSize;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colPrdName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtSupName;

    @FXML
    private JFXTextField txtxName;

    @FXML
    private TextField txtxSearch;

    @FXML
    private JFXTextField txtxUnitPrice;

    @FXML
    private JFXTextField txxtQty;

    ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPrdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        ObservableList<String> category = FXCollections.observableArrayList("Casual Dresses", "Formal Dresses", "Evening Dresses", "Summer Dresses", "Party Dresses");
        cmbCategory.setItems(category);

        ObservableList<String> size = FXCollections.observableArrayList("XS", "S", "M", "L", "XL", "XXL");
        cmbPrdSize.setItems(size);

        ItemTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues (newValue);
            }
        });
        ItemTable.setItems(itemService.getAllItem());
        txtItemCode.setText(itemService.generateItemId());

        loadTable();
        loadSupplierID();



    }
    private void loadSupplierID() {
        ObservableList<String> suppliers = FXCollections.observableArrayList();
        ObservableList<Supplier> sup=supplierService.getAllSupplier();
        for (Supplier supplier:sup){
            suppliers.add(supplier.getId());
        }
        cmbSupplierId.setItems(suppliers);
    }

    private void setTextToValues(Item newValue) {
        txtItemCode.setText(newValue.getId());
        txtxName.setText(newValue.getName());
        txxtQty.setText(newValue.getQty()+"");
        cmbCategory.setValue(newValue.getCategory());
        cmbPrdSize.setValue(newValue.getSize());
        cmbSupplierId.setValue(newValue.getSupplier().getId());
        txtxUnitPrice.setText(newValue.getUnitPrice()+"");


    }


    @FXML
    void btnAddStockOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtxName.getText(),
                Integer.parseInt(txxtQty.getText()),
                cmbCategory.getValue(),
                cmbPrdSize.getValue(),
                supplierService.searchSupplierByID(cmbSupplierId.getValue()),
                Integer.parseInt(txtxUnitPrice.getText())
        );
        System.out.println(item);
        if (!txtxName.getText().equals("")) {
            System.out.println(item.toString());
            boolean isInsert = itemService.addItem(item);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Added");
                alert.setContentText("Item Added Successfully..!");
                alert.showAndWait();
                clear();
               loadTable();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }
    public void loadTable() {

            ObservableList<Item> items = itemService.getAllItem();
            if (items == null || items.isEmpty()) {
                System.out.println("No items found.");
            } else {
                ItemTable.setItems(items);
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
    private void clear() {

        txtItemCode.setText("");
        txtxName.setText("");
        txtxUnitPrice.setText("");
        txxtQty.setText("");
        cmbCategory.setValue(null);
        cmbPrdSize.setValue(null);
        cmbSupplierId.setValue(null);


        txtItemCode.setText(itemService.generateItemId());
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting");
        alert.setContentText("Are you sure want to delete this Item");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()== ButtonType.OK){
            boolean isDeleted = itemService.deleteItemById(txtItemCode.getText());
            if (isDeleted){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Item Deleted");
                alert2.setContentText("Item deleted successfully");
                alert2.showAndWait();
                clear();
                loadTable();
            }
        }


    }

    @FXML
    void btnSearchItem(ActionEvent event) {
        try {
            Item item = itemService.searchItemByName(txtxName.getText());
            if (item!=null){
                txtItemCode.setText(item.getId());
                txtxName.setText(item.getName());
                cmbCategory.setValue(item.getCategory());
                cmbPrdSize.setValue(item.getSize());
                txtxUnitPrice.setText(item.getUnitPrice()+"");
                txxtQty.setText(item.getQty()+"");
                cmbSupplierId.setValue(item.getSupplier().getId());
                txtSupName.setText(item.getSupplier().getName());
            }
        } catch (Exception e) {
            System.out.println("not found");
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!txtxName.getText().equals("")){
            Item item = new Item(
                    txtItemCode.getText(),
                    txtxName.getText(),
                    Integer.parseInt(txxtQty.getText()),
                    cmbCategory.getValue(),
                    cmbPrdSize.getValue(),
                    supplierService.searchSupplierByID(cmbSupplierId.getValue()),
                    Integer.parseInt(txtxUnitPrice.getText())
            );

            boolean isInsert = itemService.updateItem(item);
            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item update");
                alert.setContentText("Item Updated Successfully..!");
                alert.showAndWait();
                clear();
                loadTable();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Couldn't update!");
                alert.showAndWait();
                clear();
                loadTable();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }



    }

    @FXML

    void supIDOnAction(ActionEvent event) {
        String supplierId = cmbSupplierId.getValue();

        if (supplierId != null) {
            Supplier supplier = supplierService.searchSupplierByID(supplierId);
            if (supplier != null) {
                txtSupName.setText(supplier.getName());
            } else {
                showAlert("Error", "Supplier not found.");
            }
        } else {
            showAlert("Input Error", "Please select a supplier ID.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
