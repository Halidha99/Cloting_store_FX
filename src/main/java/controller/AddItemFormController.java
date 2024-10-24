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
    private TableColumn<?, ?> colPrdName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSize;

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
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier.id"));



        ObservableList<String> category = FXCollections.observableArrayList("Casual Dresses", "Formal Dresses", "Evening Dresses", "Summer Dresses", "Party Dresses");
        cmbCategory.setItems(category);

        ObservableList<String> size = FXCollections.observableArrayList("XS", "S", "M", "L", "XL", "XXL");
        cmbPrdSize.setItems(size);


        ItemTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        });

        loadTable();
        txtItemCode.setText(itemService.generateItemId());
        loadSupplierID();



    }
    private void loadSupplierID() {
        ObservableList<String> suppliers = FXCollections.observableArrayList();
        ObservableList<Supplier> sup = supplierService.getAllSupplier();
        for (Supplier supplier : sup) {
            suppliers.add(supplier.getId());
        }
        cmbSupplierId.setItems(suppliers);
    }
    private void setTextToValues(Item newValue) {
        txtItemCode.setText(newValue.getId());
        txtxName.setText(newValue.getName());
        txxtQty.setText(String.valueOf(newValue.getQty()));
        cmbCategory.setValue(newValue.getCategory());
        cmbPrdSize.setValue(newValue.getSize());
        cmbSupplierId.setValue(newValue.getSupplier().getId());
        txtxUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
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
        if (!txtxName.getText().isEmpty()) {
            boolean isInsert = itemService.addItem(item);
            if (isInsert) {
                showAlert("Item Added", "Item Added Successfully..!");
                clear();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
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
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Main Dashboard");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/maindash-board-form.fxml"))));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Unable to load the dashboard.");
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting");
        alert.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = itemService.deleteItemById(txtItemCode.getText());
            if (isDeleted) {
                showAlert("Item Deleted", "Item deleted successfully");
                clear();
                loadTable();
            }
        }

    }

    @FXML
    void btnSearchItem(ActionEvent event) {
        try {
            Item item = itemService.searchItemByName(txtxName.getText());
            if (item != null) {
                setTextToValues(item);
            } else {
                showAlert("Not Found", "Item not found!");
            }
        } catch (Exception e) {
            System.out.println("Item not found.");
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!txtxName.getText().isEmpty()) {
            Item item = new Item(
                    txtItemCode.getText(),
                    txtxName.getText(),
                    Integer.parseInt(txxtQty.getText()),
                    cmbCategory.getValue(),
                    cmbPrdSize.getValue(),
                    supplierService.searchSupplierByID(cmbSupplierId.getValue()),
                    Integer.parseInt(txtxUnitPrice.getText())
            );

            boolean isUpdated = itemService.updateItem(item);
            if (isUpdated) {
                showAlert("Item Updated", "Item Updated Successfully!");
                clear();
                loadTable();
            } else {
                showAlert("Error", "Couldn't update the item.");
                clear();
                loadTable();
            }
        } else {
            showAlert("Missing Data", "Please check your form again!");
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
    private void loadTable() {
        ObservableList<Item> items = itemService.getAllItem();
        System.out.println("Items loaded: " + items);
        if (items == null || items.isEmpty()) {
            System.out.println("No items returned from the service.");
        }
        ItemTable.setItems(items);
    }



}
