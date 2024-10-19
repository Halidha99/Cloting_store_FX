package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddEmployeeFormController {

    @FXML
    private TableView<?> EmployeeTable;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<?> cmbEmpTittle;

    @FXML
    private TableColumn<?, ?> colEmpEmail;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpMobile;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colEmpNic;

    @FXML
    private JFXTextField txtEmpEmail;

    @FXML
    private JFXTextField txtEmpId;

    @FXML
    private JFXTextField txtEmpMobile;

    @FXML
    private JFXTextField txtEmpName;

    @FXML
    private JFXTextField txtEmpNic;

    @FXML
    private JFXTextField txtEmpPassw;

    @FXML
    private TextField txtxSearch;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmployeeAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmployeeDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmployeeupdateOnAction(ActionEvent event) {

    }

    @FXML
    void searchEmployeeOnAction(ActionEvent event) {

    }

}
