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

        // Create a title for the report window with refined style
        Label titleLabel = new Label("Generate Sales Report");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Select time period with improved styling
        Label timePeriodLabel = new Label("Select Time Period:");
        ComboBox<String> timePeriodCombo = new ComboBox<>();
        timePeriodCombo.getItems().addAll("Last 7 Days", "Last Month", "Custom");
        timePeriodCombo.setValue("Last 7 Days");
        timePeriodLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        // Report display area with refined style
        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #ecf0f1; -fx-border-radius: 5px;");
        reportArea.setPrefHeight(120); // Limit height to fit nicely in the window
        reportArea.setWrapText(true);

        // Generate Button with chic style
        Button generateButton = new Button("Generate Report");
        generateButton.setStyle("-fx-background-color: linear-gradient(to bottom, #3498db, #2980b9); "
                + "-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 30px;");
        generateButton.setPrefWidth(180);
        generateButton.setOnAction(event -> {
            String timePeriod = timePeriodCombo.getValue();
            String report = generateSalesReport(timePeriod); // Generate the report based on the time period
            reportArea.setText(report); // Display the report in the TextArea
        });

        // Back Button with classy style
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b); "
                + "-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 30px;");
        backButton.setPrefWidth(180);
        backButton.setOnAction(e -> goBackToPreviousWindow(reportStage));

        // Layout container with some spacing
        VBox layout = new VBox(20, titleLabel, timePeriodLabel, timePeriodCombo, generateButton, reportArea, backButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-shadow: 0 0 20px rgba(0, 0, 0, 0.1);");

        // Scene and stage setup with refined size
        Scene scene = new Scene(layout, 450, 400);
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
