package controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entity.OrderDetailEntity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.TmCart;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import util.ServiceType;

import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddPlaceOrderFormController implements Initializable {

    @FXML private Button btnLogOut;
    @FXML private JFXButton btnRemove;
    @FXML private TableColumn<?, ?> colItemCode;
    @FXML private TableColumn<?, ?> colUnitPrice;
    @FXML private TableColumn<?, ?> colitemname;
    @FXML private TableColumn<?, ?> colprice;
    @FXML private TableColumn<?, ?> colqty;
    @FXML private Pane discount;
    @FXML private Label netTotal;
    @FXML private TableView<TmCart> tblCart;
    @FXML private JFXComboBox<String> txtCustID;
    @FXML private JFXTextField txtCustName;
    @FXML private Label txtDate;
    @FXML private JFXComboBox<String> txtItemCode;
    @FXML private JFXTextField txtItemName;
    @FXML private JFXTextField txtOrderID;
    @FXML private JFXTextField txtQty;
    @FXML private Label txtSize;
    @FXML private Label txtTime;
    @FXML private Label txtUnitPrice;

    ObservableList<TmCart> cartList = FXCollections.observableArrayList();
    List<OrderDetailEntity> orderDetailList = new ArrayList<>();

    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
    OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colitemname.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        loadItemIdmenu();
        loadCustomerIdmenu();

        txtItemCode.setOnAction(this::itemCodeOnAction);
        clear();
    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getValue();
        String itemname = txtItemName.getText().trim();
        Integer qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        Double total = unitPrice * qty;
        boolean available = false;
        for (TmCart c : cartList) {
            if (c.getItemCode().equals(itemCode)) {
                available = true;
                c.setQty(c.getQty() + qty);
                c.setTotal(c.getTotal() + total);
                break;
            }
        }
        if (!available) {
            TmCart cart = new TmCart(itemCode, itemname, qty, unitPrice, total);
            cartList.add(cart);
        }
        tblCart.setItems(FXCollections.observableArrayList(cartList));

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        LocalDate orderDate = LocalDate.now();
        LocalTime orderTime = LocalTime.now();
        Double orderTotal = Double.parseDouble(netTotal.getText());
        Customer customer = customerService.searchItemByID(txtCustID.getValue());
        String orderID = txtOrderID.getText();
        Order order = new Order(orderID, customer, orderDate, orderTime, orderTotal);

        for (TmCart cart : cartList) {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity(cart.getItemCode(), cart.getQty(), cart.getTotal());
            Item item = itemService.searchItemByID(orderDetailEntity.getItemID());
            Integer newQty = item.getQty() - orderDetailEntity.getQty();
            if (newQty < 0) {
                showAlert("Error", "Out of stock: Item Code " + item.getId());
                return;
            }
            item.setQty(newQty);
            itemService.updateItem(item);
            orderDetailList.add(orderDetailEntity);
        }
        orderService.addOrder(order, orderDetailList);
        showAlert("Success", "Order Placed Successfully");
        clear();
    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        try {
            String itemName = txtItemName.getText().trim();
            Item item = itemService.searchItemByName(itemName);
            setItemDetails(item);
        } catch (Exception e) {
            showAlert("Error", "No Item Found");
        }
    }

    private void setItemDetails(Item item) {
        if (item != null) {
            txtItemCode.setValue(item.getId());
            txtItemName.setText(item.getName());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtSize.setText(item.getSize());
        } else {
            showAlert("Error", "No Item Found");
        }
    }

    @FXML
    void custIDonAction(ActionEvent event) {
        try {
            Customer customer = customerService.searchItemByID(txtCustID.getValue());
            if (customer != null) {
                txtCustName.setText(customer.getName());
            }
        } catch (Exception e) {
            showAlert("Error", "Error retrieving customer");
        }
    }

    @FXML
    void itemCodeOnAction(ActionEvent event) {
        try {
            Item item = itemService.searchItemByID(txtItemCode.getValue());
            setItemDetails(item);
        } catch (Exception e) {
            showAlert("Error", "Error retrieving item");
        }
    }

    private void loadCustomerIdmenu() {
        ObservableList<String> customers = FXCollections.observableArrayList();
        ObservableList<Customer> sup = customerService.getAllCustomer();
        for (Customer customer : sup) {
            customers.add(customer.getId());
        }
        txtCustID.setItems(customers);
    }

    private void loadItemIdmenu() {
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<Item> itemList = itemService.getAllItem();
        for (Item item : itemList) {
            items.add(item.getId());
        }
        txtItemCode.setItems(items);
    }

    private void loadDateAndTime() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f.format(new Date()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            txtTime.setText(String.format("%02d : %02d : %02d", now.getHour(), now.getMinute(), now.getSecond()));
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void clear() {
        txtOrderID.setText(orderService.generateOrderId());
        txtCustID.setValue(null);
        txtCustName.setText(null);
        txtItemCode.setValue(null);
        txtItemName.setText(null);
        txtQty.setText(null);
        netTotal.setText("0");
        txtUnitPrice.setText("0");
        txtSize.setText("0");
        cartList.clear();
        orderDetailList.clear();
        tblCart.setItems(cartList);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
