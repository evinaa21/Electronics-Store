package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GenerateReportView {

    private Stage previousStage; // To store the reference to the previous stage

    // Constructor to pass the previous Stage reference
    public GenerateReportView(Stage previousStage) {
        this.previousStage = previousStage;
    }

    public void showGenerateReportView() {
        Stage reportStage = new Stage();

        // Create a title for the report window
        Label titleLabel = new Label("Generate Sales Report");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Select time period
        Label timePeriodLabel = new Label("Select Time Period:");
        ComboBox<String> timePeriodCombo = new ComboBox<>();
        timePeriodCombo.getItems().addAll("Last 7 Days", "Last Month", "Custom");
        timePeriodCombo.setValue("Last 7 Days");
        timePeriodLabel.setStyle("-fx-font-size: 14px;");

        // Report display area
        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        reportArea.setPrefHeight(120); // Limit height to fit nicely in the window

        // Generate Button
        Button generateButton = new Button("Generate Report");
        generateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        generateButton.setPrefWidth(150);
        generateButton.setOnAction(event -> {
            String timePeriod = timePeriodCombo.getValue();
            String report = generateSalesReport(timePeriod); // Generate the report based on the time period
            reportArea.setText(report); // Display the report in the TextArea
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setPrefWidth(150);
        backButton.setOnAction(e -> goBackToPreviousWindow(reportStage));

        // Layout container
        VBox layout = new VBox(20, titleLabel, timePeriodLabel, timePeriodCombo, generateButton, reportArea, backButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #c0c0c0; -fx-border-width: 2px;");

        // Scene and stage setup
        Scene scene = new Scene(layout, 400, 350);
        reportStage.setTitle("Generate Sales Report");
        reportStage.setScene(scene);
        reportStage.show();
    }

    private String generateSalesReport(String timePeriod) {
        // Sample logic to generate a sales report based on the selected time period
        if (timePeriod.equals("Last 7 Days")) {
            return "Sales Report for Last 7 Days:\nTotal Sales: $1000\nTotal Items Sold: 50";
        } else if (timePeriod.equals("Last Month")) {
            return "Sales Report for Last Month:\nTotal Sales: $4000\nTotal Items Sold: 200";
        } else {
            return "Sales Report for Custom Period:\nTotal Sales: $2000\nTotal Items Sold: 100";
        }
    }

    // Go back to the previous window (Manager Dashboard or other previous stage)
    private void goBackToPreviousWindow(Stage reportStage) {
        reportStage.close();

        if (previousStage != null) {
            previousStage.show();
        }
    }
}
