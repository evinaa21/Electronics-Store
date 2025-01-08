package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManagerView extends Application {

    private Stage previousStage;

    public ManagerView(Stage previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public void start(Stage primaryStage) {
        // Center welcome message
        Text welcomeMessage = new Text("Welcome, Manager!");
        welcomeMessage.setStyle(
                "-fx-font-size: 32px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: #2c3e50;"
        );
        welcomeMessage.setEffect(new DropShadow(5, Color.LIGHTGRAY));

        // Header below the welcome message
        Text header = new Text("Dashboard");
        header.setStyle(
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: #34495e;"
        );
        header.setEffect(new DropShadow(3, Color.GRAY));

        // Layout with chic and classy styling
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);" +
                "-fx-padding: 40px;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 10px;"
        );

        // Buttons with classy style
        Button addItemButton = createClassyButton("Add New Item");
        Button restockItemButton = createClassyButton("Restock Item");
        Button generateReportButton = createClassyButton("Generate Sales Report");
        Button viewCategoriesButton = createClassyButton("View Item Sectors");
        Button viewItemsButton = createClassyButton("View Items");
        Button backButton = createClassyButton("Back");

        // Back button functionality
        backButton.setOnAction(e -> goBackToPreviousWindow(primaryStage));

        // Add elements to layout
        layout.getChildren().addAll(welcomeMessage, header, addItemButton, restockItemButton, generateReportButton, viewCategoriesButton, viewItemsButton, backButton);

        // Scene setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); // Center window on the screen
        primaryStage.show();
    }

    // Create chic and classy buttons
    private Button createClassyButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #5d6d7e, #34495e);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15px;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-color: #95a5a6;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 10px 20px;"
        );

        // Hover effect
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #34495e, #5d6d7e);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15px;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-color: #7f8c8d;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 10px 20px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.5, 0, 2);"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #5d6d7e, #34495e);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15px;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-color: #95a5a6;" +
                "-fx-border-width: 2px;" +
                "-fx-padding: 10px 20px;"
        ));
        button.setPrefWidth(250);
        return button;
    }

    private void goBackToPreviousWindow(Stage primaryStage) {
        primaryStage.close();
        if (previousStage != null) {
            previousStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
