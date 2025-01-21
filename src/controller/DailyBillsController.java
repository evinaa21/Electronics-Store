package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Bill;
import util.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class DailyBillsController {
    private final FileHandler fileHandler; // FileHandler for accessing bills
    private final VBox billsContainer; // Container to display daily bills
    private List<Bill> dailyBills; // Cache for all daily bills
    // Constructor
    public DailyBillsController(VBox billsContainer) {
        this.fileHandler = new FileHandler();
        this.billsContainer = billsContainer;
        this.dailyBills = new ArrayList<>();
    }

    // Load and display all bills generated today
    public void showTodaysBills() {
        try {
            this.dailyBills = fileHandler.loadBills(); // Load bills for today
            displayBills(this.dailyBills); // Display all bills
        } catch (Exception e) {
            billsContainer.getChildren().add(new Label("Error loading bills: " + e.getMessage()));
        }
    }

    // Display bills in the container
    private void displayBills(List<Bill> bills) {
        billsContainer.getChildren().clear(); // Clear previous data
        if (bills.isEmpty()) {
            billsContainer.getChildren().add(new Label("No bills found for today."));
            return;
        }

        for (Bill bill : bills) {
            String billInfo = String.format(
                "Bill Number: %s\nDate: %s\nTotal Amount: %.2f\nNumber of Items: %d",
                bill.getBillNumber(),
                bill.getSaleDate(),
                bill.getTotalAmount(),
                bill.getItems().size()
            );

            Label billLabel = new Label(billInfo);
            billLabel.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");
            billsContainer.getChildren().add(billLabel);
        }
    }

    // Calculate and display the total sales for today
    public void calculateTotalSales() {
        if (dailyBills.isEmpty()) {
            billsContainer.getChildren().add(new Label("No sales data available for today."));
            return;
        }

        // Calculate total sales without mapping
        double totalAmount = 0.0;
        for (Bill bill : dailyBills) {
            totalAmount += bill.getTotalAmount();
        }

        Label totalSalesLabel = new Label(String.format("Total Sales for Today: %.2f", totalAmount));
        totalSalesLabel.setStyle("-fx-padding: 10; -fx-font-weight: bold; -fx-text-fill: #4169E1;");
        billsContainer.getChildren().add(totalSalesLabel);
    }
}
