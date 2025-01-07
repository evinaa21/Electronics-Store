package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerView extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(10);
        
        Button addItemButton = new Button("Add New Item");
        Button restockItemButton = new Button("Restock Item");
        Button generateReportButton = new Button("Generate Sales Report");
        Button viewCategoriesButton = new Button("View Item Sectors");
        Button viewItemsButton = new Button("View Items");
        
        // Add functionality to buttons later
        layout.getChildren().addAll(addItemButton, restockItemButton, generateReportButton, viewCategoriesButton, viewItemsButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

