package controller;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.element.Cell;
//import com.jfoenix.controls.JFXComboBox;
//import com.jfoenix.controls.JFXTextField;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import model.Report;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.element.Cell;
//import service.custom.impl.ReportServiceImpl;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.time.LocalDate;
//import java.util.List;
//
public class ReportController {
//
//    @FXML
//    private TableColumn<Report, LocalDate> colDate;
//
//    @FXML
//    private TableColumn<Report, Integer> colTotalItemsSold;
//
//    @FXML
//    private TableColumn<Report, Integer> colTotalReturns;
//
//    @FXML
//    private TableColumn<Report, Double> colTotalSales;
//
//    @FXML
//    private DatePicker datePicker;
//
//    @FXML
//    private JFXComboBox<String> monthCombo;
//
//    @FXML
//    private TableView<Report> reportTable;
//
//    @FXML
//    private JFXTextField yearText;
//
//    private final ReportServiceImpl reportService = new ReportServiceImpl();
//
//    @FXML
//    void exportToPdf(ActionEvent event) {
//        String pdfFilePath = "reports/report.pdf";
//        File file = new File(pdfFilePath);
//
//
//        file.getParentFile().mkdirs();
//
//        try {
//
//            PdfWriter writer = new PdfWriter(pdfFilePath);
//            PdfDocument pdfDocument = new PdfDocument(writer);
//            Document document = new Document(pdfDocument);
//
//
//            document.add(new Paragraph("Report Data").setBold());
//
//
//            Table table = new Table(4);
//            table.addHeaderCell(new Cell().add("Date"));
//            table.addHeaderCell(new Cell().add("Total Items Sold"));
//            table.addHeaderCell(new Cell().add("Total Returns"));
//            table.addHeaderCell(new Cell().add("Total Sales"));
//
//
//            for (Report data : reportTable.getItems()) {
//                table.addCell(new Cell().add(data.getDate().toString()));
//                table.addCell(new Cell().add(String.valueOf(data.getTotalItemsSold())));
//                table.addCell(new Cell().add(String.valueOf(data.getTotalReturns())));
//                table.addCell(new Cell().add(String.valueOf(data.getTotalSales())));
//
//            }
//            document.add(table);
//            document.close();
//
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Export PDF");
//            alert.setContentText("PDF exported successfully to " + pdfFilePath);
//            alert.showAndWait();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            showAlert("Error", "Could not create PDF file: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    void loadDailyReport(ActionEvent event) {
//        LocalDate selectedDate = datePicker.getValue();
//        if (selectedDate == null) {
//            showAlert("Error", "Please select a date to load the daily report.");
//            return;
//        }
//
//        List<Report> reportData = reportService.getDailyReport(selectedDate);
//        updateReportTable(reportData);
//    }
//
//    @FXML
//    void loadMonthlyReport(ActionEvent event) {
//        String selectedMonth = monthCombo.getValue();
//        if (selectedMonth == null) {
//            showAlert("Error", "Please select a month to load the monthly report.");
//            return;
//        }
//
//        int year;
//        try {
//            year = Integer.parseInt(yearText.getText());
//        } catch (NumberFormatException e) {
//            showAlert("Error", "Please enter a valid year.");
//            return;
//        }
//
//        List<Report> reportData = reportService.getMonthlyReport(selectedMonth, year);
//        updateReportTable(reportData);
//    }
//
//    @FXML
//    void loadYearlyReport(ActionEvent event) {
//        int year;
//        try {
//            year = Integer.parseInt(yearText.getText());
//        } catch (NumberFormatException e) {
//            showAlert("Error", "Please enter a valid year.");
//            return;
//        }
//
//        List<Report> reportData = reportService.getYearlyReport(year);
//        updateReportTable(reportData);
//    }
//
//    private void updateReportTable(List<Report> reportData) {
//        ObservableList<Report> observableList = FXCollections.observableArrayList(reportData);
//        reportTable.setItems(observableList);
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle(title);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    @FXML
//    public void initialize() {
//
//        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
//        colTotalItemsSold.setCellValueFactory(new PropertyValueFactory<>("totalItemsSold"));
//        colTotalReturns.setCellValueFactory(new PropertyValueFactory<>("totalReturns"));
//        colTotalSales.setCellValueFactory(new PropertyValueFactory<>("totalSales"));
//
//
//        monthCombo.getItems().addAll("January", "February", "March", "April", "May", "June",
//                "July", "August", "September", "October", "November", "December");
//    }
}
