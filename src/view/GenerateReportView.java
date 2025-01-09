package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Manager;

import java.time.LocalDate;

public class GenerateReportView {

    private Manager manager;

    // Constructor to pass Manager instance
    public GenerateReportView(Manager manager) {
        this.manager = manager;
    }

    // Method to return the VBox view content instead of displaying it directly
    public VBox getViewContent() {
        // Create a title for the report window
        Label titleLabel = new Label("Generate Sales Report");
        titleLabel.setFont(new javafx.scene.text.Font("Arial", 28));
        titleLabel.setTextFill(Color.WHITE);

        // Parent container with dark blue background
        VBox parentLayout = new VBox(20);
        parentLayout.setStyle("-fx-background-color: #2C3E50;");
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setPadding(new Insets(20));

        // Select time period
        Label timePeriodLabel = new Label("Select Time Period:");
        timePeriodLabel.setTextFill(Color.WHITE);

        ComboBox<String> timePeriodCombo = new ComboBox<>();
        timePeriodCombo.getItems().addAll("Last 7 Days", "Last Month", "Custom");
        timePeriodCombo.setValue("Last 7 Days");

        // Create DatePicker for Start and End Date (hidden initially)
        Label startDateLabel = new Label("Start Date:");
        startDateLabel.setTextFill(Color.WHITE);
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setVisible(false);  // Hidden initially

        Label endDateLabel = new Label("End Date:");
        endDateLabel.setTextFill(Color.WHITE);
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setVisible(false);  // Hidden initially

        // Create HBox to arrange start and end date horizontally
        HBox dateBox = new HBox(10, startDateLabel, startDatePicker, endDateLabel, endDatePicker);
        dateBox.setAlignment(Pos.CENTER);
        dateBox.setVisible(false);  // Hidden initially

        // Report display area
        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setPrefHeight(150);
        reportArea.setWrapText(true);

        // Generate Button
        Button generateButton = new Button("Generate Report");
        generateButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-padding: 12px 20px; -fx-border-radius: 5;");
        generateButton.setFont(new javafx.scene.text.Font("Arial", 14));
        generateButton.setOnAction(event -> {
            String timePeriod = timePeriodCombo.getValue();
            if (timePeriod == null || timePeriod.isEmpty()) {
                reportArea.setText("Please select a valid time period.");
            } else {
                if (timePeriod.equals("Custom")) {
                    // Check if both start and end dates are selected
                    if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                        reportArea.setText("Please select both start and end dates.");
                    } else {
                        LocalDate startDate = startDatePicker.getValue();
                        LocalDate endDate = endDatePicker.getValue();
                        reportArea.setText(generateSalesReport(timePeriod, startDate, endDate)); // Pass dates for custom range
                    }
                } else {
                    reportArea.setText(generateSalesReport(timePeriod, null, null)); // No dates for other time periods
                }
            }
        });

        // Show date pickers if "Custom" is selected
        timePeriodCombo.setOnAction(event -> {
            if (timePeriodCombo.getValue().equals("Custom")) {
                startDatePicker.setVisible(true);
                endDatePicker.setVisible(true);
                dateBox.setVisible(true);  // Show the HBox with date pickers
            } else {
                startDatePicker.setVisible(false);
                endDatePicker.setVisible(false);
                dateBox.setVisible(false);  // Hide the HBox with date pickers
            }
        });

        // Add DropShadow effect to the layout
        parentLayout.setEffect(createDropShadowEffect());

        // Add components to the layout
        parentLayout.getChildren().addAll(titleLabel, timePeriodLabel, timePeriodCombo, dateBox, generateButton, reportArea);

        return parentLayout; // Return the VBox layout containing the view content
    }

    // Method to generate a sales report based on the selected time period and optional date range
    private String generateSalesReport(String timePeriod, LocalDate startDate, LocalDate endDate) {
        switch (timePeriod) {
            case "Last 7 Days":
                return "Sales Report for Last 7 Days:\nTotal Sales: $1000\nTotal Items Sold: 50";
            case "Last Month":
                return "Sales Report for Last Month:\nTotal Sales: $4000\nTotal Items Sold: 200";
            case "Custom":
                if (startDate != null && endDate != null) {
                    return "Sales Report for Custom Period (" + startDate + " to " + endDate + "):\nTotal Sales: $2000\nTotal Items Sold: 100";
                } else {
                    return "Invalid custom date range selected.";
                }
            default:
                return "Invalid time period selected.";
        }
    }

    // Method to create a DropShadow effect
    private DropShadow createDropShadowEffect() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.1));
        return dropShadow;
    }
}

