package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import service.ServiceFactory;
import service.custom.impl.OrderServiceImpl;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrderFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> custID;

    @FXML
    private TableColumn<?, ?> date;

    @FXML
    private TableColumn<?, ?> netTotal;

    @FXML
    private TableColumn<?, ?> orderID;

    @FXML
    private TableView<?> tblOrder;

    @FXML
    void printBtnOnAction(ActionEvent event) {

    }
   OrderServiceImpl orderService= ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        netTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        custID.setCellValueFactory(new PropertyValueFactory<>("custID"));


        System.out.println(orderService.getAllOrders());
        tblOrder.setItems(orderService.getAllOrders());
    }
}
