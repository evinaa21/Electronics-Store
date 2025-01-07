package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenerateReportView {
    
    public static void showGenerateReportView() {
        Stage reportStage = new Stage();
        
        Label timePeriodLabel = new Label("Select Time Period:");
        
        // Use ComboBox for predefined time periods
        ComboBox<String> timePeriodCombo = new ComboBox<>();
        timePeriodCombo.getItems().addAll("Last 7 Days", "Last Month", "Custom");
        timePeriodCombo.setValue("Last 7 Days");
        
        // TextArea to display the report
        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        
        Button generateButton = new Button("Generate Report");
        generateButton.setOnAction(event -> {
            String timePeriod = timePeriodCombo.getValue();
            String report = generateSalesReport(timePeriod); // Call method to generate report
            reportArea.setText(report); // Set the report in the TextArea
        });
        
        VBox layout = new VBox(10, timePeriodLabel, timePeriodCombo, generateButton, reportArea);
        
        Scene scene = new Scene(layout, 400, 300);
        reportStage.setTitle("Generate Sales Report");
        reportStage.setScene(scene);
        reportStage.show();
    }
    
    private static String generateSalesReport(String timePeriod) {
        // Sample logic for generating a sales report based on the time period
        if (timePeriod.equals("Last 7 Days")) {
            return "Sales Report for Last 7 Days:\nTotal Sales: $1000\nTotal Items Sold: 50";
        } else if (timePeriod.equals("Last Month")) {
            return "Sales Report for Last Month:\nTotal Sales: $4000\nTotal Items Sold: 200";
        } else {
            return "Sales Report for Custom Period:\nTotal Sales: $2000\nTotal Items Sold: 100";
        }
    }
}

