package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import controller.ManagerController;
import model.Item;
import model.Manager;
import util.FileHandler;

public class ManagerView extends Application {

    private ManagerController managerController;
    private Stage primaryStage;
    private BorderPane mainLayout;
    private StackPane centerContent;
    private FileHandler fileHandler;
    private Label lowStockLabel;

    public ManagerView(Stage primaryStage, Manager manager, FileHandler fileHandler) {
        this.primaryStage = primaryStage;
        this.managerController = new ManagerController(primaryStage, manager);  // Pass the controller
        this.mainLayout = new BorderPane();
        this.centerContent = new StackPane();
        this.fileHandler = fileHandler;  // Properly initialize fileHandler
        this.lowStockLabel = new Label();
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

        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().addAll(welcomeMessage, managerInfo, header);

        // Adding the low stock information
        String lowStockInfo = getLowStockInfo();
        Text lowStockMessage = new Text(lowStockInfo);
        lowStockMessage.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        homeContent.getChildren().add(lowStockMessage);

        centerContent.getChildren().add(homeContent);


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
        Button manageSuppliersButton = createNavButton("Manage Suppliers", primaryStage);
        Button monitorCashierButton = createNavButton("Monitor Cashier Performance", primaryStage);
        Button viewSectorsButton = createNavButton("View Sectors", primaryStage);
        Button viewItemsButton = createNavButton("View Items", primaryStage);

        // Button actions tied to the controller
        homeButton.setOnAction(e -> managerController.openHomePage());
        addItemButton.setOnAction(e -> managerController.openAddItemView());
        restockItemButton.setOnAction(e -> managerController.openRestockItemView());
        generateReportButton.setOnAction(e -> managerController.openGenerateReportView());
        manageSuppliersButton.setOnAction(e -> managerController.openSupplierView());
        monitorCashierButton.setOnAction(e -> managerController.openMonitorCashierPerformanceView());
        viewSectorsButton.setOnAction(e -> managerController.openViewSectorsView());
        viewItemsButton.setOnAction(e -> managerController.openViewItemsView());

        // Add the buttons to the navigation bar
        navigationBar.getChildren().addAll(
                homeButton, addItemButton, restockItemButton,
                generateReportButton, manageSuppliersButton, monitorCashierButton, viewSectorsButton, viewItemsButton

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
        welcomeMessage.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white;");


        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().addAll(welcomeMessage);

        // Refresh and display the low stock notification dynamically
        String lowStockInfo = getLowStockInfo();
        Text lowStockMessage = new Text(lowStockInfo);
        lowStockMessage.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        homeContent.getChildren().add(lowStockMessage);

        updateCenterContent(homeContent);
    }

    private String getLowStockInfo() {
        // Specify the threshold for low stock (e.g., 5)
        ArrayList<Item> lowStockItems = fileHandler.notifyLowStock(5);  // Use fileHandler to get low stock items
        
        if (lowStockItems != null && !lowStockItems.isEmpty()) {
            StringBuilder lowStockInfo = new StringBuilder("Low Stock Items:\n");
            for (Item item : lowStockItems) {
                lowStockInfo.append(item.getItemName())
                            .append(" - Stock: ")
                            .append(item.getStockQuantity())
                            .append("\n");
            }
            return lowStockInfo.toString();
        } else {
            return "No low stock items.";
        }
    }


    private void updateCenterContent(VBox viewContent) {
        centerContent.getChildren().clear();
        centerContent.getChildren().add(viewContent);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

