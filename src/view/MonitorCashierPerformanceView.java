package view;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Cashier;
import model.Manager;
import model.Sector;
import util.FileHandlerMANAGER;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MonitorCashierPerformanceView {

    private Manager manager;
    private FileHandlerMANAGER fileHandler;


    public MonitorCashierPerformanceView(Manager manager, FileHandlerMANAGER fileHandler) {
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

       
        ArrayList<Sector> managerSectors = manager.getSectors(); 
        ArrayList<Cashier> cashiers = fileHandler.loadCashiersByRole(managerSectors); 

        if (cashiers != null && !cashiers.isEmpty()) {
            // Set display to cashier names
            cashierComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Cashier>() {
                @Override
                protected void updateItem(Cashier item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName()); 
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
                        setText(item.getName());  
                    }
                }
            });

            cashierComboBox.getItems().addAll(cashiers);  
        } else {
            showError("No cashiers found in the file.");
            cashierComboBox.setDisable(true); 
        }

        Button monitorButton = new Button("Monitor Performance");
        monitorButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px;");

        Label totalBillsLabel = new Label("Total Bills: 0");
        Label totalRevenueLabel = new Label("Total Revenue: $0.00");

        // Apply style to both labels
        String labelStyle = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;";

        totalBillsLabel.setStyle(labelStyle);
        totalRevenueLabel.setStyle(labelStyle);

        monitorButton.setOnAction(event -> {
            Cashier selectedCashier = cashierComboBox.getValue();

            if (selectedCashier == null) {
                showError("Please select a cashier.");
                return;
            }

            int totalBills = 0;
            double totalRevenue = 0.0;

            
            String summaryFilePath = "C:\\Users\\Evina\\git\\Electronics-Store\\src\\BinaryFiles\\sales_summary.txt";
            File summaryFile = new File(summaryFilePath);

            if (!summaryFile.exists()) {
                showError("Sales summary file does not exist.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(summaryFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    if (parts.length >= 4) {
                        String billNumber = parts[0].split(": ")[1].trim();
                        String totalAmountStr = parts[2].split(": ")[1].trim();
                        String cashierName = parts[3].split(": ")[1].trim();

                        // Convert the total amount to double
                        double totalAmount = 0.0;
                        try {
                            totalAmount = Double.parseDouble(totalAmountStr);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid total amount format for bill " + billNumber + ": " + totalAmountStr);
                            continue;  
                        }

                        if (cashierName.equals(selectedCashier.getName())) {
                            totalBills++;  
                            totalRevenue += totalAmount;  
                        }
                    }
                }
            } catch (IOException e) {
                showError("Error reading the sales summary file: " + e.getMessage());
                return;
            }

            totalBillsLabel.setText("Total Bills: " + totalBills);
            totalRevenueLabel.setText("Total Revenue: $" + String.format("%.2f", totalRevenue));

            totalBillsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
            totalRevenueLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        });

        layout.getChildren().addAll(cashierLabel, cashierComboBox, monitorButton, totalBillsLabel, totalRevenueLabel);
        return layout;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
