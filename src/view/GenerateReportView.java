package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Manager;
import util.FileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GenerateReportView {

    public GenerateReportView(Manager manager, FileHandler fileHandler) {
    }

    public VBox getViewContent() {
        Label titleLabel = new Label("Generate Sales Report");
        titleLabel.setFont(new javafx.scene.text.Font("Arial", 28));
        titleLabel.setTextFill(Color.WHITE);

        VBox parentLayout = new VBox(20);
        parentLayout.setStyle("-fx-background-color: #2C3E50;");
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(20));

        Label timePeriodLabel = new Label("Select Time Period:");
        timePeriodLabel.setTextFill(Color.WHITE);

        ComboBox<String> timePeriodCombo = new ComboBox<>();
        timePeriodCombo.getItems().addAll("Last 7 Days", "Last Month");
        timePeriodCombo.setValue("Last 7 Days");

        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setPrefHeight(150);
        reportArea.setWrapText(true);

        Button generateButton = new Button("Generate Report");
        generateButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-padding: 12px 20px; -fx-border-radius: 5;");
        generateButton.setFont(new javafx.scene.text.Font("Arial", 14));
        generateButton.setOnAction(event -> {
            String timePeriod = timePeriodCombo.getValue();
            if (timePeriod == null || timePeriod.isEmpty()) {
                reportArea.setText("Please select a valid time period.");
            } else {
                reportArea.setText(generateSalesReport(timePeriod, null, null));
            }
        });

        parentLayout.setEffect(createDropShadowEffect());
        parentLayout.getChildren().addAll(titleLabel, timePeriodLabel, timePeriodCombo, generateButton, reportArea);

        return parentLayout;
    }

    public String generateSalesReport(String timePeriod, LocalDate startDate, LocalDate endDate) {
        // Calculate the total sales from the filtered bills (using data from SalesSummary.txt)
        double totalSales = calculateTotalSalesFromSummary();

        return String.format("Total Sales: $%.2f", totalSales);
    }

    public double calculateTotalSalesFromSummary() {
        String summaryFilePath = "C:\\Users\\Evina\\git\\Electronics-Store\\src\\BinaryFiles\\sales_summary.txt";
        File summaryFile = new File(summaryFilePath);
        double totalSales = 0.0;

        if (!summaryFile.exists()) {
            System.out.println("Sales summary file does not exist.");
            return totalSales;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(summaryFile))) {
            String line;
            // Read each line
            while ((line = reader.readLine()) != null) {
              
                if (line.contains("Sales Report")) {
                    continue; 
                }
                
                if (line.contains("Total Amount:") && line.contains("Cashier:")) {
                    // Split the line into parts by the "Total Amount:" label
                    String[] parts = line.split("Total Amount:");
                    if (parts.length > 1) {
                        // extract the total amount
                        String totalAmountStr = parts[1].split(",")[0].trim();  // Get the total amount part before the comma
                        try {
                            double totalAmount = Double.parseDouble(totalAmountStr);//skips 0 amounts
                            if (totalAmount > 0.0) {
                                totalSales += totalAmount; 
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing total amount: " + parts[1]);
                        }

                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading sales summary: " + e.getMessage());
        }

        return totalSales;
    }

    public ArrayList<String> filterBills(ArrayList<String> billsData, String timePeriod, LocalDate startDate, LocalDate endDate) {
        ArrayList<String> filteredBills = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (String bill : billsData) {
            // this ignores dividers and non-relevant lines
            if (bill.contains("=========================================") || bill.contains("THANK YOU FOR SHOPPING")) {
                continue;
            }

            // it initialize date, total amount, and cashier name variables
            String billDateStr = null;
            String cashierName = null;  

            String[] lines = bill.split("\n");
            for (String line : lines) {
                if (line.startsWith("Date:")) {
                    billDateStr = line.split(":")[1].trim();
                } else if (line.startsWith("Total Amount:")) {
                    // Extract total amount
                    String amountStr = line.split(":")[1].trim();
                    try {
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid total amount: " + amountStr);
                    }
                } else if (line.startsWith("Cashier:")) {
                    // Extract cashier name 
                    cashierName = line.split(":")[1].trim();
                }
            }

            if (billDateStr != null) {
                try {
                    LocalDate billDate = LocalDate.parse(billDateStr, formatter); // Parse the date
                    // Filter based on time period
                    if (timePeriod.equals("Last 7 Days") && billDate.isAfter(now.minusDays(7))) {
                        filteredBills.add(bill);
                    } else if (timePeriod.equals("Last Month") && billDate.isAfter(now.minusMonths(1))) {
                        filteredBills.add(bill);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping invalid date in bill: " + billDateStr);
                }
            }

            if (cashierName != null) {
            }
        }

        return filteredBills;
    }

    private DropShadow createDropShadowEffect() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.1));
        return dropShadow;
    }

    public void setFileHandler(FileHandler fileHandler) {
    }
}
