package controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Bill;
import model.Item;
import util.FileHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/*Handles the logic and interactions for the Daily Bills screen.
Displays bills created by the cashier for the current day, with options for filtering, sorting, and exporting bills.
*/
public class DailyBillsController {
    private final FileHandler fileHandler; // FileHandler for accessing bills
    private final VBox billsContainer; // Container to display daily bills
    private final ComboBox<String> filterCategoryDropdown; // Dropdown to filter by category
    private final ComboBox<String> filterTimeDropdown; // Dropdown to filter by time
    private final String assignedSector; // Cashier's assigned sector
    private List<Bill> dailyBills; // Cache for all daily bills

    // Constructor
    public DailyBillsController(VBox billsContainer, ComboBox<String> filterCategoryDropdown, ComboBox<String> filterTimeDropdown, String assignedSector) {
        this.fileHandler = new FileHandler();
        this.billsContainer = billsContainer;
        this.filterCategoryDropdown = filterCategoryDropdown;
        this.filterTimeDropdown = filterTimeDropdown;
        this.assignedSector = assignedSector;
        this.dailyBills = new ArrayList<>();
        setupCategoryFilter();
        setupTimeFilter();
    }

    //Display bills filtered by the selected category.
    private void setupCategoryFilter() {
        filterCategoryDropdown.setOnAction(event -> {
            String selectedCategory = filterCategoryDropdown.getValue();
            filterAndDisplayBills(selectedCategory, filterTimeDropdown.getValue(), null);
        });
    }

    //Display bills filtered by the selected time of day.
    private void setupTimeFilter() {
        filterTimeDropdown.setOnAction(event -> {
            String selectedTime = filterTimeDropdown.getValue();
            filterAndDisplayBills(filterCategoryDropdown.getValue(), selectedTime, null);
        });
    }

    //Load and display all bills generated today by a specific cashier
    public void loadDailyBills(String cashierName) {
        try {
            Date today = new Date(); // Get today's date
            this.dailyBills = fileHandler.loadBills(cashierName, today); // Load bills for today
            filterAndDisplayBills("All Categories", "All Times", null); // Display all bills initially
        } catch (Exception e) {
            billsContainer.getChildren().add(new Label("Error loading bills: " + e.getMessage()));
        }
    }

    //Filter and display bills based on selected filters and sorting criteria
    private void filterAndDisplayBills(String selectedCategory, String selectedTime, Comparator<Bill> sortingComparator) {
        List<Bill> filteredBills = dailyBills.stream()
                .filter(bill -> filterByCategory(bill, selectedCategory))
                .filter(bill -> filterByTime(bill, selectedTime))
                .collect(Collectors.toList());

        if (sortingComparator != null) {
            filteredBills.sort(sortingComparator);
        }

        displayBills(filteredBills);
    }

    // Display bills in the container
    private void displayBills(List<Bill> bills) {
        billsContainer.getChildren().clear();
        if (bills.isEmpty()) {
            billsContainer.getChildren().add(new Label("No bills found for the selected filters."));
            return;
        }

        double totalAmount = 0; //Calculate total sales

        for (Bill bill : bills) {
            totalAmount += bill.getTotalAmount();
            String billInfo = String.format(
                    "Bill Number: %s\nDate: %s\nTotal Amount: %.2f\nNumber of Items: %d",
                    bill.getBillNumber(),
                    bill.getSaleDate(),
                    bill.getTotalAmount(),
                    bill.getItems().size()
            );

            Label billLabel = new Label(billInfo);
            billLabel.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");
            billLabel.setOnMouseClicked(event -> viewBillDetails(bill)); //Click to view details
            billsContainer.getChildren().add(billLabel);
        }

        // Display total sales
        Label totalSalesLabel = new Label(String.format("Total Sales for Today: %.2f", totalAmount));
        totalSalesLabel.setStyle("-fx-padding: 10; -fx-font-weight: bold;");
        billsContainer.getChildren().add(totalSalesLabel);
    }

    //Filter bills by category
    private boolean filterByCategory(Bill bill, String selectedCategory) {
        if (selectedCategory == null || selectedCategory.equals("All Categories")) {
            return true;
        }

        return bill.getItems().stream().anyMatch(item -> item.getCategory().equalsIgnoreCase(selectedCategory));
    }

    //Filter bills by the selected time
    private boolean filterByTime(Bill bill, String timePeriod) {
        if (timePeriod == null || timePeriod.equals("All Times")) {
            return true;
        }

        int hour = bill.getSaleDate().getHours();
        switch (timePeriod) {
            case "Morning":
                return hour >= 6 && hour < 12;
            case "Afternoon":
                return hour >= 12 && hour < 18;
            case "Evening":
                return hour >= 18 && hour <= 23;
            default:
                return true;
        }
    }

    //View details of a specific bill
    private void viewBillDetails(Bill bill) {
        VBox detailsContainer = new VBox(10);
        detailsContainer.getChildren().add(new Label("Bill Details"));

        for (Item item : bill.getItems()) {
            String itemInfo = String.format("Item: %s | Category: %s | Quantity: %d | Price: %.2f",
                    item.getItemName(), item.getCategory(), item.getItemQuantity(), item.getSellingPrice());
            detailsContainer.getChildren().add(new Label(itemInfo));
        }

        detailsContainer.getChildren().add(new Label(String.format("Total Amount: %.2f", bill.getTotalAmount())));

        // Replace billsContainer content with details view
        billsContainer.getChildren().clear();
        billsContainer.getChildren().add(detailsContainer);
    }

    //Sort bills based on selected criterion
    public void sortBills(String criterion) {
        Comparator<Bill> sortingComparator = switch (criterion) {
            case "Date" -> Comparator.comparing(Bill::getSaleDate);
            case "Total Amount" -> Comparator.comparing(Bill::getTotalAmount).reversed();
            case "Bill Number" -> Comparator.comparing(Bill::getBillNumber);
            default -> null;
        };

        filterAndDisplayBills(filterCategoryDropdown.getValue(), filterTimeDropdown.getValue(), sortingComparator);
    }

    //Export bills to a text file
    public void exportToTextFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Bill bill : dailyBills) {
                writer.write(bill.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            showError("Failed to export bills: " + e.getMessage());
        }
    }

    //Show error message
    private void showError(String message) {
        billsContainer.getChildren().add(new Label("Error: " + message));
    }
}
