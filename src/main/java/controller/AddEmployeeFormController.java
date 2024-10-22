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
import model.Employee;
import service.ServiceFactory;

import service.custom.EmployeeService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEmployeeFormController implements Initializable {

    @FXML
    private TableView<Employee> EmployeeTable;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbEmpTittle;

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

    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
         colEmpNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmpMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
       colEmpEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        ObservableList<String> empTitle = FXCollections.observableArrayList("MRS", "MR", "MISS");
        cmbEmpTittle.setItems(empTitle);

       EmployeeTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        });
       loadTable();

    }

    private void setTextToValues(Employee newValue) {
       txtEmpId.setText(newValue.getId());
        txtEmpName.setText(newValue.getName());
        txtEmpNic.setText(newValue.getNic());
        txtEmpMobile.setText(newValue.getMobile());
        txtEmpEmail.setText(newValue.getEmail());

       cmbEmpTittle.setValue(newValue.getTittle());

    }
    private void clear() {
        txtEmpId.setText(employeeService.generateEmployeeId());
        txtEmpName.clear();
        txtEmpNic.clear();
        txtEmpMobile.clear();
        txtEmpEmail.clear();

        cmbEmpTittle.getSelectionModel().clearSelection();
    }
    public void loadTable() {
        ObservableList<Employee> employee = employeeService.getAllEmployee();

        if (employee!= null && !employee.isEmpty()) {
            EmployeeTable.setItems(employee);
        } else {
            System.out.println("No Employee found or employeelist is null.");
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
    void btnEmployeeAddOnAction(ActionEvent event) {
        Employee employee = new Employee(
               txtEmpId.getText(),
               cmbEmpTittle.getValue(),
                txtEmpName.getText(),
                txtEmpNic.getText(),
                txtEmpMobile.getText(),
                txtEmpEmail.getText(),

                txtEmpPassw.getText()
        );
        System.out.println(employee);
        if (!txtEmpName.getText().isEmpty() && employeeService.isValidEmail(txtEmpEmail.getText()) && !txtEmpNic.getText().isEmpty()) {
            boolean isAdd = employeeService.addEmployee(employee);
            if (isAdd) {
                showAlert("Employee Added", "Employee Added Successfully..!");
                clear();
                loadTable();
            }
        } else {
            showAlert("Error", "Please fill all fields correctly.");
        }

    }

    @FXML
    void btnEmployeeDeleteOnAction(ActionEvent event) {

            if (!txtEmpId.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting");
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean isDeleted = employeeService.deleteEmployeeById(txtEmpId.getText());
                    if (isDeleted) {
                        showAlert("Employee Deleted", "Employee deleted successfully.");
                        clear();
                        loadTable();
                    } else {
                        showAlert("Error", "Failed to delete employee.");
                    }
                }
            }

    }

    @FXML
    void btnEmployeeupdateOnAction(ActionEvent event) {
        if (!txtEmpName.getText().isEmpty() && employeeService.isValidEmail(txtEmpEmail.getText()) && !txtEmpNic.getText().isEmpty()) {
           Employee employee= new Employee(
                    txtEmpId.getText(),
                    cmbEmpTittle.getValue(),
                   txtEmpName.getText(),
                    txtEmpNic.getText(),
                   txtEmpMobile.getText(),
                    txtEmpEmail.getText(),
                   txtEmpPassw.getText()
            );

            boolean isUpdated = employeeService.updateEmployee(employee);
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
    void searchEmployeeOnAction(ActionEvent event) {
        try {
            Employee employee = employeeService.searchEmployeeByName(txtEmpName.getText());
            if (employee != null) {
                setTextToValues(employee);
            } else {
                showAlert("Not Found", "Employee not found.");
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
