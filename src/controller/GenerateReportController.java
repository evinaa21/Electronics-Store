package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;

public class GenerateReportController {

    private Manager manager;

    public GenerateReportController(Manager manager) {
        this.manager = manager;
        showGenerateReportView();
    }

    public void showGenerateReportView() {
        Stage reportStage = new Stage();

        Label timePeriodLabel = new Label("Select Time Period:");
        TextField timePeriodField = new TextField();

        Button generateButton = new Button("Generate Report");
        generateButton.setOnAction(event -> {
            String timePeriod = timePeriodField.getText();
            String report = manager.generateSalesReport(timePeriod);
            showReport(report);
        });

        VBox layout = new VBox(10, timePeriodLabel, timePeriodField, generateButton);

        Scene scene = new Scene(layout, 300, 200);
        reportStage.setTitle("Generate Sales Report");
        reportStage.setScene(scene);
        reportStage.show();
    }

    private void showReport(String report) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sales Report");
        alert.setHeaderText(null);
        alert.setContentText(report);
        alert.showAndWait();
    }
}

