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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddPlaceOrderFormController implements Initializable {

    @FXML
    private JFXTextField Discount;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private TableColumn<TmCart, String> colItemCode;

    @FXML
    private TableColumn<TmCart, String> colitemname;

    @FXML
    private TableColumn<TmCart, Integer> colqty;

    @FXML
    private TableColumn<TmCart, Double> colprice;

    @FXML
    private Pane discount;

    @FXML
    private Label finalTotal;
    @FXML
    private TableColumn<?, ?> colUnitPrice;


    @FXML
    private TableView<TmCart> tblCart;

    @FXML
    private JFXComboBox<String> txtCustID;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private Label txtDate;

    @FXML
    private JFXComboBox<String> txtItemCode;

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


    OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);
    ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);


    ObservableList<TmCart> cartList = FXCollections.observableArrayList();
    List<OrderDetailEntity> orderDetailList = new ArrayList<>();

    @FXML
    void RemoveOnAction(ActionEvent event) {
        // Implement logic to remove item from cart
    }

    @FXML
    void addCartOnAction(ActionEvent event) {
        try {

            String itemCode = txtItemCode.getValue();
            String itemName = txtItemName.getText();
            Integer qty = Integer.parseInt(txtQTY.getText());
            Double unitPrice = Double.parseDouble(txtPrice.getText());
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
                TmCart cart = new TmCart(itemCode, itemName, qty, unitPrice, total);
                cartList.add(cart);
            }


            tblCart.setItems(FXCollections.observableArrayList(cartList));
            Double net = Double.parseDouble(finalTotal.getText()) + total;
            finalTotal.setText(String.valueOf(net));

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please ensure all fields are filled correctly.");
            alert.showAndWait();
        }
    }

    @FXML
    void adddiscountKey(KeyEvent event) {
        // Handle discount logic
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        // Handle back button logic if needed
    }

    @FXML
    void btnComboOnAction(ActionEvent event) {
        try {
            txtCustID.setValue(customerService.searchCustomerByName(txtCustName.getText()).getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Customer Found");
            alert.showAndWait();
        }
    }

    @FXML
    void custIDonAction(ActionEvent event) {
        try {
            txtCustName.setText(customerService.searchItemByID(txtCustID.getValue()).getName());
        } catch (Exception e) {
            System.out.println("Customer ID not found.");
        }
    }

    @FXML
    void itemCodeOnAction(ActionEvent event) {
        try {
            Item item = itemService.searchItemByID(txtItemCode.getValue());
            txtItemName.setText(item.getName());
            txtPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQTY.setText(String.valueOf(item.getQty()));
        } catch (Exception e) {
            System.out.println("Item not found.");
        }
    }

    @FXML
    void placeOrderOnAction(ActionEvent event) {
        try {
            LocalDate orderDate = LocalDate.now();
            String orderID = txtOrderId.getText();
            LocalTime orderTime = LocalTime.now();
            Double orderTotal = Double.parseDouble(finalTotal.getText());
            Customer customer = customerService.searchItemByID(txtCustID.getValue());


            Order order = new Order(orderID, customer, orderDate, orderTime, orderTotal);

            for (TmCart cart : cartList) {
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity(cart.getItemCode(), cart.getQty(), cart.getTotal());
                Item item = itemService.searchItemByID(orderDetailEntity.getItemID());
                Integer newQty = item.getQty() - orderDetailEntity.getQty();

                if (newQty < 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Out of stock: Item Code " + item.getId());
                    alert.showAndWait();
                    cartList.remove(cart);
                    tblCart.setItems(cartList);
                    tblCart.refresh();
                    return;
                }

                item.setQty(newQty);
                itemService.updateItem(item);
                orderDetailList.add(orderDetailEntity);
            }

           orderService.addOrder(order,orderDetailList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Order Placed Successfully");
            alert.showAndWait();
            clear();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Order Error");
            alert.setContentText("Failed to place the order: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void printBillOnAction(ActionEvent event) {
        // Implement bill printing logic if needed
    }

    @FXML
    void searchItemOnAction(ActionEvent event) {
        try {
            Item item = itemService.searchItemByName(txtItemName.getText());
            txtItemCode.setValue(item.getId());
            txtPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQTY.setText(String.valueOf(item.getQty()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Item Found");
            alert.showAndWait();
            txtItemCode.setValue("");
            txtPrice.setText("0");
        }
    }

    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = f.format(date);
        txtDate.setText(dateNow);


        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            txtTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colitemname.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        loadCustomerId();
        loadItemId();
        clear();
    }

    private void loadCustomerId() {

        ObservableList<String> customers = FXCollections.observableArrayList();
        ObservableList<Customer> customerList = customerService.getAllCustomer();
        for (Customer customer : customerList) {
            customers.add(customer.getId());
        }
        txtCustID.setItems(customers);
    }

    private void loadItemId() {

        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<Item> itemList = itemService.getAllItem();
        for (Item item : itemList) {
            items.add(item.getId());
        }
        txtItemCode.setItems(items);
    }

    private void clear() {

        txtItemName.clear();
        txtPrice.clear();
        txtQTY.clear();
        txtCustName.clear();
        finalTotal.setText("0");
        cartList.clear();
        tblCart.setItems(cartList);
    }
}
