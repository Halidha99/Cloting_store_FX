package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import model.Order;
import model.TmReturn;
import service.ServiceFactory;
import service.custom.ItemService;
import service.custom.OrderService;
import util.ServiceType;

import java.time.LocalDate;

public class ItemReturnFormController {

    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnReturn;
    @FXML
    private TableColumn<TmReturn, String> colItemCode;
    @FXML
    private TableColumn<TmReturn, String> colItemName;
    @FXML
    private TableColumn<TmReturn, Integer> colQty;
    @FXML
    private TableColumn<TmReturn, String> colSize;
    @FXML
    private TableColumn<TmReturn, Double> colUnitPrice;
    @FXML
    private TableColumn<TmReturn, LocalDate> colDate;
    @FXML
    private TableView<TmReturn> tblReturnItems;

    @FXML
    private TableColumn<?, ?> colcet;
    @FXML
    private JFXTextField txtOrderID;
    @FXML
    private JFXTextField txtQty;

    private final OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);
    private final ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    private ObservableList<TmReturn> returnList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
      colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
       colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
       colcet.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("id"));
       colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
       colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblReturnItems.setItems(returnList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        txtOrderID.clear();
        txtQty.clear();
        tblReturnItems.getSelectionModel().clearSelection();
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        String orderId = txtOrderID.getText().trim();
        String qtyText = txtQty.getText().trim();


        if (orderId.isEmpty() || qtyText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter the Order ID and Quantity.");
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Quantity must be a valid integer.");
            return;
        }


       Order order = orderService.searchOrderByID(orderId);
        if (order == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Order not found.");
            return;
        }

       Item item = itemService.searchItemByID(orderId);
        if (item == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No items found for the specified Order ID.");
            return;
        }


        if (qty > item.getQty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Return quantity exceeds available stock.");
            return;
        }

        item.setQty(item.getQty() + qty);
        itemService.updateItem(item);


        TmReturn returnEntry = new TmReturn(
                item.getId(),
                item.getName(),
                qty,
                item.getSize(),
                item.getUnitPrice(),
                LocalDate.now()
        );

        returnList.add(returnEntry);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Item returned successfully.");
        btnClearOnAction(event);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
