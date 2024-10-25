package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
import java.io.IOException;
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

    @FXML
    void RemoveOnAction(ActionEvent event) {
        TmCart selectedCart = tblCart.getSelectionModel().getSelectedItem();
        if (selectedCart != null) {
            cartList.remove(selectedCart);
            tblCart.refresh();
            updateNetTotal();
        }
    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getValue();
        String itemname = txtItemName.getText();
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
        updateNetTotal();
    }

    private void updateNetTotal() {
        double net = cartList.stream().mapToDouble(TmCart::getTotal).sum();
        netTotal.setText(String.format("%.2f", net));
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
    void btnLogOutOnAction(ActionEvent event) {
        Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
        currentStage.close();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employee_dashboard_form.fxml"));
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Employee Dashboard");
            dashboardStage.setScene(new Scene(loader.load()));
            dashboardStage.show();
        } catch (IOException e) {
            showAlert("Error", "Unable to load the dashboard.");
        }


    }

    private void loadCustomerIdmenu(){
        ObservableList<String> customers = FXCollections.observableArrayList();
        ObservableList<Customer> sup = customerService.getAllCustomer();
        for (Customer customer : sup) {
            customers.add(customer.getId());
        }
        txtCustID.setItems(customers);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {

            if (txtCustID.getValue() == null || txtOrderID.getText().isEmpty()) {
                showAlert("Error", "Please select a customer and provide an order ID.");
                return;
            }


            if (cartList.isEmpty()) {
                showAlert("Error", "Your cart is empty. Please add items.");
                return;
            }


            LocalDate orderDate = LocalDate.now();
            String orderID = txtOrderID.getText();
            LocalTime orderTime = LocalTime.now();
            Double orderTotal;


            try {
                orderTotal = Double.parseDouble(netTotal.getText());
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid total value.");
                return;
            }


            Customer customer = customerService.searchItemByID(txtCustID.getValue());
            if (customer == null) {
                showAlert("Error", "Selected customer not found.");
                return;
            }


            Order order = new Order(orderID, customer, orderDate, orderTime, orderTotal);
            List<OrderDetailEntity> orderDetailList = new ArrayList<>(); // Ensure this is initialized


            for (TmCart cart : cartList) {
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity(cart.getItemCode(), cart.getQty(), cart.getTotal());


                Item item = itemService.searchItemByID(orderDetailEntity.getItemID());
                if (item == null) {
                    showAlert("Error", "Item not found: " + cart.getItemCode());
                    return;
                }


                Integer newQty = item.getQty() - orderDetailEntity.getQty();
                if (newQty < 0) {
                    showAlert("Error", "Out of stock: Item Code " + item.getId());
                    return;
                }


                item.setQty(newQty);
                itemService.updateItem(item);
                orderDetailList.add(orderDetailEntity);
            }


            boolean orderPlaced = orderService.addOrder(order, orderDetailList);
            if (orderPlaced) {
                showAlert("Success", "Order placed successfully!");
                clear();
            } else {
                showAlert("Error", "Failed to place order. Please try again.");
            }

        } catch (Exception e) {
            showAlert("Order Error", "Failed to place the order: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void loadItemIdmenu(){
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<Item> itemList = itemService.getAllItem();
        for (Item item : itemList) {
            items.add(item.getId());
        }
        txtItemCode.setItems(items);
    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        try {
            Item item = itemService.searchItemByName(txtItemName.getText());
            setItemDetails(item);
            System.out.println(item);
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
            System.out.println("Error retrieving customer");
        }
    }

    @FXML
    void itemCodeOnAction(ActionEvent event) {
        try {
            Item item = itemService.searchItemByID(txtItemCode.getValue());
            setItemDetails(item);
        } catch (Exception e) {
            System.out.println("Error retrieving item");
        }
    }



    @FXML
    void printBillOnAction(ActionEvent event) {
        try {
            Document document = new Document(PageSize.A5, 20, 20, 30, 20);  // A5 size with margins
            PdfWriter.getInstance(document, new FileOutputStream("Bill.pdf"));
            document.open();


            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Paragraph title = new Paragraph("Order Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);


            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLUE.darker());
            Paragraph header = new Paragraph("Order ID: " + txtOrderID.getText() +
                    "\nDate: " + LocalDate.now() +
                    "\nTime: " + LocalTime.now() +
                    "\nCustomer: " + txtCustName.getText(), headerFont);
            header.setAlignment(Element.ALIGN_RIGHT);
            header.setSpacingAfter(15);
            document.add(header);


            document.add(new LineSeparator());


            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 5, 2, 3});

            Font headerCellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Item Code", headerCellFont));
            cell.setBackgroundColor(BaseColor.BLUE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Item Name", headerCellFont));
            cell.setBackgroundColor(BaseColor.BLUE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity", headerCellFont));
            cell.setBackgroundColor(BaseColor.BLUE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total Price", headerCellFont));
            cell.setBackgroundColor(BaseColor.BLUE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);


            Font itemFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
            for (TmCart cart : cartList) {
                table.addCell(new PdfPCell(new Phrase(cart.getItemCode(), itemFont)));
                table.addCell(new PdfPCell(new Phrase(cart.getItemName(), itemFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(cart.getQty()), itemFont)));
                table.addCell(new PdfPCell(new Phrase(String.format("%.2f", cart.getTotal()), itemFont)));
            }

            document.add(table);


            document.add(new LineSeparator());


            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE.darker());
            Paragraph total = new Paragraph("Total: Rs" + netTotal.getText(), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(15);
            document.add(total);

            document.close();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(" bill generated successfully as Bill.pdf");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to generate bill: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    private Double calculateTotal() {
        Double total = 0.0;
        for (TmCart cart : cartList) {
            total += cart.getTotal();
        }
        return total;
    }

    private boolean checkStockAvailability() {
        for (TmCart cart : cartList) {
            Item item = itemService.searchItemByID(cart.getItemCode());
            if (item.getQty() < cart.getQty()) {
                return false;
            }
        }
        return true;
    }


    @FXML
    void supBtnSearchOnAction(ActionEvent event) {
        try {
            txtCustID.setValue(customerService.searchCustomerByName(txtCustName.getText()).getId());
        } catch (Exception e) {
            showAlert("Error", "No Customer Found");
        }
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
