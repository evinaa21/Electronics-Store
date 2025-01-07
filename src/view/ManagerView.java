package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerView extends Application {

    private Stage previousStage;  // Reference to the previous stage

    public ManagerView(Stage previousStage) {
        this.previousStage = previousStage;  // Pass the previous stage to this view
    }

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER); // Center the buttons in the layout

        // Buttons with added styling
        Button addItemButton = new Button("Add New Item");
        styleButton(addItemButton);
        
        Button restockItemButton = new Button("Restock Item");
        styleButton(restockItemButton);
        
        Button generateReportButton = new Button("Generate Sales Report");
        styleButton(generateReportButton);
        
        Button viewCategoriesButton = new Button("View Item Sectors");
        styleButton(viewCategoriesButton);
        
        Button viewItemsButton = new Button("View Items");
        styleButton(viewItemsButton);

        // Back Button
        Button backButton = new Button("Back");
        styleButton(backButton);
        backButton.setOnAction(e -> goBackToPreviousWindow(primaryStage)); // Action for the back button
        
        // Add buttons to layout
        layout.getChildren().addAll(addItemButton, restockItemButton, generateReportButton, viewCategoriesButton, viewItemsButton, backButton);

        // Scene setup with layout and dimensions
        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to style buttons uniformly
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: 10px;");
        button.setPrefWidth(200);
        button.setMaxHeight(40);
    }

    // Go back to the previous window
    private void goBackToPreviousWindow(Stage primaryStage) {
        primaryStage.close(); // Close the current window

        if (previousStage != null) {
            previousStage.show(); // Show the previous window
        }
    }

    public static void main(String[] args) {
        // Initialize with a null previous stage for simplicity in this example
        launch(args);
    }
}
