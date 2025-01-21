package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import util.FileHandlerMANAGER;
import view.GenerateReportView;

public class GenerateReportController {

    private Manager manager;
    private FileHandlerMANAGER fileHandler;

    public GenerateReportController(Manager manager) {
        this.manager = manager;
        this.fileHandler=fileHandler;
        showGenerateReportView();
    }

    public void showGenerateReportView() {
        Stage reportStage = new Stage();

        // Instantiate the view (GenerateReportView)
        GenerateReportView generateReportView = new GenerateReportView(manager, fileHandler);

        // Get the VBox from the GenerateReportView
        VBox layout = generateReportView.getViewContent();

        // Set up the scene with the layout
        Scene scene = new Scene(layout, 500, 400);
        reportStage.setTitle("Generate Sales Report");
        reportStage.setScene(scene);
        reportStage.show();
    }
}

