package view;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Bill;
import model.Cashier;
import model.Manager;
import util.FileHandler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class MonitorCashierPerformanceView {

    private Manager manager;
    private FileHandler fileHandler;

    // Constructor to pass Manager and FileHandler instances
    public MonitorCashierPerformanceView(Manager manager, FileHandler fileHandler) {
        if (manager == null || fileHandler == null) {
            throw new IllegalArgumentException("Manager and FileHandler cannot be null.");
        }
        this.manager = manager;
        this.fileHandler = fileHandler;
    }

    public VBox getViewContent() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2C3E50; -fx-padding: 30px; -fx-border-radius: 15px;");

        Label cashierLabel = new Label("Select Cashier:");
        cashierLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // ComboBox to select cashier
        ComboBox<Cashier> cashierComboBox = new ComboBox<>();
        cashierComboBox.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff;");

        // Load only cashiers with Role.Cashier into the ComboBox
        ArrayList<Cashier> cashiers = fileHandler.loadCashiersByRole();  // This filters based on Role
        if (cashiers != null && !cashiers.isEmpty()) {
            // Set display to cashier names
            cashierComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Cashier>() {
                @Override
                protected void updateItem(Cashier item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName());  // Display the cashier's name
                    }
                }
            });

            cashierComboBox.setButtonCell(new javafx.scene.control.ListCell<Cashier>() {
                @Override
                protected void updateItem(Cashier item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName());  // Display the cashier's name
                    }
                }
            });

            cashierComboBox.getItems().addAll(cashiers);  // Add the filtered cashiers to the ComboBox
        } else {
            showError("No cashiers found in the file.");
            cashierComboBox.setDisable(true);  // Disable the ComboBox if no cashiers exist
        }

        Label dateLabel = new Label("Select Date:");
        dateLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff;");

        Button monitorButton = new Button("Monitor Performance");
        monitorButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px;");


        Label totalBillsLabel = new Label("Total Bills: 0");
        Label totalItemsSoldLabel = new Label("Total Items Sold: 0");
        Label totalRevenueLabel = new Label("Total Revenue: $0.00");

        monitorButton.setOnAction(event -> {
            LocalDate selectedDate = datePicker.getValue();
            Cashier selectedCashier = cashierComboBox.getValue();

            if (selectedDate == null) {
                showError("Please select a date.");
                return;
            }

            if (selectedCashier == null) {
                showError("Please select a cashier.");
                return;
            }

            int totalBills = 0;
            int totalItemsSold = 0;
            double totalRevenue = 0.0;

            // Get total revenue for the selected cashier for the selected date
            double dailyTotal = selectedCashier.calculateDailyTotal();
            totalRevenue += dailyTotal;

            // Iterate through selected cashier's bills to calculate total items sold and total bills
            ArrayList<Bill> cashierBills = selectedCashier.getBills();
            for (Bill bill : cashierBills) {
                if (isSameDay(bill.getSaleDate(), selectedDate)) {
                    totalBills++;
                    totalItemsSold += bill.getItems().size();
                }
            }

            totalBillsLabel.setText("Total Bills: " + totalBills);
            totalItemsSoldLabel.setText("Total Items Sold: " + totalItemsSold);
            totalRevenueLabel.setText("Total Revenue: $" + String.format("%.2f", totalRevenue));
        });
        totalBillsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        totalItemsSoldLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        totalRevenueLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");


        layout.getChildren().addAll(cashierLabel, cashierComboBox, dateLabel, datePicker, monitorButton, totalBillsLabel, totalItemsSoldLabel, totalRevenueLabel);
        return layout;
    }

    private boolean isSameDay(Date billDate, LocalDate selectedDate) {
        // Convert Date to LocalDate
        LocalDate billLocalDate = billDate.toInstant()
                                         .atZone(ZoneId.systemDefault())
                                         .toLocalDate();
        
        // Compare the two LocalDate objects
        return billLocalDate.isEqual(selectedDate);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
