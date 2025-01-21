package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Manager;
import util.FileHandlerMANAGER;

import java.time.LocalDate;
import java.util.ArrayList;

public class GenerateReportView {

    private Manager manager;
	private FileHandlerMANAGER fileHandler;
	


    public GenerateReportView(Manager manager, FileHandlerMANAGER fileHandler) {

        this.manager = manager;
        this.fileHandler=fileHandler;
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
        timePeriodCombo.getItems().addAll("Last 7 Days", "Last Month", "Custom");
        timePeriodCombo.setValue("Last 7 Days");

        Label startDateLabel = new Label("Start Date:");
        startDateLabel.setTextFill(Color.WHITE);
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setVisible(false);

        Label endDateLabel = new Label("End Date:");
        endDateLabel.setTextFill(Color.WHITE);
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setVisible(false);

        HBox dateBox = new HBox(10, startDateLabel, startDatePicker, endDateLabel, endDatePicker);
        dateBox.setAlignment(Pos.CENTER);
        dateBox.setVisible(false);

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
                if (timePeriod.equals("Custom")) {
                    if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                        reportArea.setText("Please select both start and end dates.");
                    } else {
                        LocalDate startDate = startDatePicker.getValue();
                        LocalDate endDate = endDatePicker.getValue();
                        reportArea.setText(generateSalesReport(timePeriod, startDate, endDate));
                    }
                } else {
                    reportArea.setText(generateSalesReport(timePeriod, null, null));
                }
            }
        });

        timePeriodCombo.setOnAction(event -> {
            if (timePeriodCombo.getValue().equals("Custom")) {
                startDatePicker.setVisible(true);
                endDatePicker.setVisible(true);
                dateBox.setVisible(true);
            } else {
                startDatePicker.setVisible(false);
                endDatePicker.setVisible(false);
                dateBox.setVisible(false);
            }
        });

        parentLayout.setEffect(createDropShadowEffect());
        parentLayout.getChildren().addAll(titleLabel, timePeriodLabel, timePeriodCombo, dateBox, generateButton, reportArea);

        return parentLayout;
    }




    // Method to create a DropShadow effect

    public String generateSalesReport(String timePeriod, LocalDate startDate, LocalDate endDate) {
        // Use the fileHandler to read bills from the file
        ArrayList<String> billsData = fileHandler.readBills();

        // Filter the bills data based on the time period or custom date range
        ArrayList<String> filteredBills = filterBills(billsData, timePeriod, startDate, endDate);

        // Calculate the total sales from the filtered bills
        double totalSales = calculateTotalSales(filteredBills);

        return "Total Sales: $" + totalSales;
    }

    private ArrayList<String> filterBills(ArrayList<String> billsData, String timePeriod, LocalDate startDate, LocalDate endDate) {
        ArrayList<String> filteredBills = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for (String bill : billsData) {
            // Parse each bill date and amount
            String[] billData = bill.split(",");
            LocalDate billDate = LocalDate.parse(billData[0]);
            double billAmount = Double.parseDouble(billData[1]);

            // Filter based on time period or custom date range
            if (timePeriod.equals("Last 7 Days") && billDate.isAfter(now.minusDays(7))) {
                filteredBills.add(bill);
            } else if (timePeriod.equals("Last Month") && billDate.isAfter(now.minusMonths(1))) {
                filteredBills.add(bill);
            } else if (timePeriod.equals("Custom") && billDate.isAfter(startDate.minusDays(1)) && billDate.isBefore(endDate.plusDays(1))) {
                filteredBills.add(bill);
            }
        }
        return filteredBills;
    }

    private double calculateTotalSales(ArrayList<String> billsData) {
        double totalSales = 0.0;

        for (String bill : billsData) {
            // Parse the bill data (assuming the format is "date,amount")
            String[] billData = bill.split(",");
            double amount = Double.parseDouble(billData[1]);
            totalSales += amount;
        }

        return totalSales;
    }


    private DropShadow createDropShadowEffect() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.1));
        return dropShadow;
    }



    public void setFileHandler(FileHandlerMANAGER fileHandler) {
        this.fileHandler = fileHandler;
    }

}


