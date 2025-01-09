package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        // Welcome message
        Text welcomeMessage = new Text("Welcome, Manager!");
        welcomeMessage.setStyle("-fx-font-size: 24px; -fx-fill: white;");
        welcomeMessage.setEffect(new DropShadow(5, Color.LIGHTGRAY));

        // Manager information (sample info)
        Text managerInfo = new Text("Name: John Doe\nEmail: john.doe@example.com");
        managerInfo.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        managerInfo.setEffect(new DropShadow(3, Color.GRAY));

        // Header below the welcome message
        Text header = new Text("Dashboard");
        header.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        header.setEffect(new DropShadow(3, Color.GRAY));

        // Top navigation bar
        HBox navigationBar = createNavigationBar(primaryStage);

        // Center content (Home view by default)
        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.getChildren().addAll(welcomeMessage, managerInfo, header);

        // Layout using BorderPane
        BorderPane layout = new BorderPane();
        layout.setTop(navigationBar);
        layout.setCenter(centerContent);
        layout.setStyle("-fx-background-color: #2C3E50;");

        // Scene setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); // Center window on the screen
        primaryStage.show();
    }

    // Create a horizontal navigation bar with a new "Home" button
    private HBox createNavigationBar(Stage primaryStage) {
        HBox navigationBar = new HBox(20);
        navigationBar.setAlignment(Pos.CENTER);
        navigationBar.setStyle("-fx-background-color: #34495E;");

        Button homeButton = createNavButton("Home", primaryStage);
        Button addItemButton = createNavButton("Add New Item", primaryStage);
        Button restockItemButton = createNavButton("Restock Item", primaryStage);
        Button generateReportButton = createNavButton("Generate Sales Report", primaryStage);
        Button viewCategoriesButton = createNavButton("View Item Sectors", primaryStage);
        Button viewItemsButton = createNavButton("View Items", primaryStage);
        Button viewSupplier=createNavButton("Suppliers", primaryStage);

        navigationBar.getChildren().addAll(
                homeButton,
                addItemButton,
                restockItemButton,
                generateReportButton,
                viewCategoriesButton,
                viewItemsButton,
                viewSupplier
        );

        return navigationBar;
    }

    // Create navigation bar buttons with event handlers
    private Button createNavButton(String text, Stage primaryStage) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5;");
        
        // Set hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white;"));

        button.setOnAction(e -> {
            if (text.equals("Home")) {
                showHomePage(primaryStage);
            } else {
                // Handle other buttons if needed
            }
        });

        return button;
    }

    // Show the Home page with manager info and welcome message
    private void showHomePage(Stage primaryStage) {
        // Create welcome message and manager info
        Text welcomeMessage = new Text("Welcome, Manager!");
        welcomeMessage.setStyle("-fx-font-size: 24px; -fx-fill: white;");
        welcomeMessage.setEffect(new DropShadow(5, Color.LIGHTGRAY));

        Text managerInfo = new Text("Name: John Doe\nEmail: john.doe@example.com");
        managerInfo.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        managerInfo.setEffect(new DropShadow(3, Color.GRAY));

        // Header below the welcome message
        Text header = new Text("Dashboard");
        header.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        header.setEffect(new DropShadow(3, Color.GRAY));

        // Center content for home page
        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().addAll(welcomeMessage, managerInfo, header);

        // Layout using BorderPane
        BorderPane layout = new BorderPane();
        layout.setTop(createNavigationBar(primaryStage));
        layout.setCenter(homeContent);
        layout.setStyle("-fx-background-color: #2C3E50;");

        // Scene setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Manager Dashboard - Home");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen(); // Center window on the screen
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
