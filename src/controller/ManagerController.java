package controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Manager;
import model.Sector;
import model.Supplier;
import model.Item;
import util.FileHandler;
import view.AddItemView;
import view.RestockItemView;
import view.GenerateReportView;
import view.MonitorCashierPerformanceView;
import view.SupplierView;
import view.ViewItemsView;
import view.ViewSectorsView;

import java.util.ArrayList;

public class ManagerController {

    private Stage primaryStage;
    private Manager manager;
    private FileHandler fileHandler;
    private BorderPane mainLayout;
    private StackPane centerContent;
    private Scene managerScene;
    private Text lowStockMessage;

    public ManagerController(Stage primaryStage, Manager manager) {
        this.primaryStage = primaryStage;
        this.manager = manager;
        this.fileHandler = new FileHandler();
        this.mainLayout = new BorderPane();
        this.centerContent = new StackPane();
        this.lowStockMessage = new Text();
        loadDataFromFiles();
        setupUI();
    }

    // Load data from binary and other files
    private void loadDataFromFiles() {
        // Load inventory items, suppliers, sectors from binary or text files
        ArrayList<Item> items = fileHandler.loadInventory();
        manager.setItems(items);

        ArrayList<Supplier> suppliers = fileHandler.loadSuppliers();
        manager.setSuppliers(suppliers);

        ArrayList<Sector> sectors = fileHandler.loadSectors();
        manager.setSectors(sectors);
    }

    private void setupUI() {
        // Header content
        VBox headerContent = createHeaderContent();

        // Navigation bar
        HBox navigationBar = createNavigationBar();

        // Main content area
        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().add(headerContent);
        homeContent.getChildren().add(createLowStockNotification());

        centerContent.getChildren().add(homeContent);

        // Setting up the main layout
        mainLayout.setTop(navigationBar);
        mainLayout.setCenter(centerContent);
        mainLayout.setStyle("-fx-background-color: #2C3E50;");

        // Scene setup
        managerScene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(managerScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private VBox createHeaderContent() {
        Text welcomeMessage = new Text("Welcome, Manager!");
        welcomeMessage.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white;");

        Text managerInfo = new Text("Name: John Doe\nEmail: john.doe@example.com");
        managerInfo.setStyle("-fx-font-size: 18px; -fx-fill: white;");

        Text header = new Text("Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        VBox headerContent = new VBox(20);
        headerContent.setAlignment(Pos.CENTER);
        headerContent.getChildren().addAll(welcomeMessage, managerInfo, header);
        return headerContent;
    }

    // Method to check and display low stock items
    private Text createLowStockNotification() {
        ArrayList<Item> lowStockItems = fileHandler.notifyLowStock(5);
        if (lowStockItems.isEmpty()) {
            lowStockMessage.setText("All items have sufficient stock.");
            lowStockMessage.setFill(Color.GREEN);
        } else {
            StringBuilder lowStockInfo = new StringBuilder("Low Stock Items:\n");
            for (Item item : lowStockItems) {
                lowStockInfo.append(item.getItemName()).append(" - Stock: ").append(item.getStockQuantity()).append("\n");
            }
            lowStockMessage.setText(lowStockInfo.toString());
            lowStockMessage.setFill(Color.RED);
        }
        lowStockMessage.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        return lowStockMessage;
    }

    private HBox createNavigationBar() {
        HBox navigationBar = new HBox(20);
        navigationBar.setAlignment(Pos.CENTER);

        Button homeButton = createNavButton("Home");
        Button addItemButton = createNavButton("Add New Item");
        Button restockItemButton = createNavButton("Restock Item");
        Button generateReportButton = createNavButton("Generate Sales Report");
        Button manageSuppliersButton = createNavButton("Manage Suppliers");
        Button monitorCashierButton = createNavButton("Monitor Cashier Performance");
        Button viewSectorsButton = createNavButton("View Sectors");
        Button viewItemsButton = createNavButton("View Items");

        homeButton.setOnAction(e -> openHomePage());
        addItemButton.setOnAction(e -> openAddItemView());
        restockItemButton.setOnAction(e -> openRestockItemView());
        generateReportButton.setOnAction(e -> openGenerateReportView());
        manageSuppliersButton.setOnAction(e -> openSupplierView());
        monitorCashierButton.setOnAction(e -> openMonitorCashierPerformanceView());
        viewSectorsButton.setOnAction(e -> openViewSectorsView());
        viewItemsButton.setOnAction(e -> openViewItemsView());

        navigationBar.getChildren().addAll(
                homeButton, addItemButton, restockItemButton,
                generateReportButton, manageSuppliersButton, monitorCashierButton, viewSectorsButton, viewItemsButton
        );
        return navigationBar;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 15px;" +
                        "-fx-border-color: #bdc3c7;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #2980B9;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 15px;" +
                        "-fx-border-color: #95a5a6;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px 15px;" +
                        "-fx-border-color: #bdc3c7;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"));
        return button;
    }

    // Open the home page and refresh the low stock notification
    public void openHomePage() {
        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().add(createHeaderContent());
        homeContent.getChildren().add(createLowStockNotification());
        updateCenterContent(homeContent);
    }

    public void openAddItemView() {
        AddItemView addItemView = new AddItemView(manager, null);
        addItemView.setFileHandler(fileHandler);
        updateCenterContent(addItemView.getViewContent());
    }

    public void openRestockItemView() {
        RestockItemView restockItemView = new RestockItemView(manager, fileHandler);
        restockItemView.setFileHandler(fileHandler);
        updateCenterContent(restockItemView.getViewContent());
    }

    public void openGenerateReportView() {
        GenerateReportView generateReportView = new GenerateReportView(manager, fileHandler);
        generateReportView.setFileHandler(fileHandler);
        updateCenterContent(generateReportView.getViewContent());
    }

    public void openSupplierView() {
        SupplierView supplierView = new SupplierView(manager, fileHandler);
        supplierView.setFileHandler(fileHandler);
        updateCenterContent(supplierView.getViewContent());
    }

    public void openMonitorCashierPerformanceView() {
        MonitorCashierPerformanceView monitorCashierPerformanceView = new MonitorCashierPerformanceView(manager, fileHandler);
        updateCenterContent(monitorCashierPerformanceView.getViewContent());
    }

    public void openViewSectorsView() {
        ViewSectorsView sectorsView = new ViewSectorsView(manager, fileHandler);
        sectorsView.setFileHandler(fileHandler);
        updateCenterContent(sectorsView.getViewContent());
    }

    public void openViewItemsView() {
        // Create the ViewItemsController and pass the manager and fileHandler
        ViewItemsController viewItemsController = new ViewItemsController(manager, fileHandler);

        // Get the current center content container
        VBox containerLayout = new VBox();  // or use the existing layout reference (centerContent)

        // Call the controller to display the view content inside the containerLayout
        viewItemsController.showViewItemsView(containerLayout);

        // Update the center content of the main layout with the ViewItems view content
        updateCenterContent(containerLayout);
    }



 // Update the center content dynamically
    private void updateCenterContent(Node content) {
        centerContent.getChildren().clear();
        centerContent.getChildren().add(content);
    }


    // Get the count of low stock items
    public int getLowStockItemsCount() {
        ArrayList<Item> lowStockItems = fileHandler.notifyLowStock(5);
        return lowStockItems.size(); // Return the count of low stock items
    }

    public Scene getManagerScene() {
        return managerScene;
    }
}