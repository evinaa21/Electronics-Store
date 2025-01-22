package controller;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import util.FileHandler;
import view.GenerateReportView;

public class GenerateReportController {

    private Manager manager;
    private FileHandler fileHandler;

    public GenerateReportController(Manager manager) {
        this.manager = manager;
        showGenerateReportView();
    }

    public void showGenerateReportView() {
        Stage reportStage = new Stage();

        GenerateReportView generateReportView = new GenerateReportView(manager, fileHandler);

        VBox layout = generateReportView.getViewContent();

        Scene scene = new Scene(layout, 500, 400);
        reportStage.setTitle("Generate Sales Report");
        reportStage.setScene(scene);
        reportStage.show();
    }
}

