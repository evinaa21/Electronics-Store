package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.ManagerController;
import model.Manager;
import model.Sector;
import util.FileHandler;
import model.Item;

import java.util.ArrayList;

public class ManagerView {

    private ManagerController managerController;
    private Stage primaryStage;
    private Manager manager;
    private FileHandler fileHandler;
	private Text lowStockMessage;
    

    public ManagerView(ManagerController managerController, Stage primaryStage, Manager manager, FileHandler fileHandler) {
        this.managerController = managerController;
        this.primaryStage = primaryStage;
        this.manager = manager;
        this.fileHandler = fileHandler;
        this.lowStockMessage = new Text();
    }

    public void setupUI(BorderPane mainLayout, StackPane centerContent) {
        VBox homeContent = createHomeContent();
        centerContent.getChildren().add(homeContent);

        HBox navigationBar = createNavigationBar();
        mainLayout.setTop(navigationBar);
        mainLayout.setCenter(centerContent);
        mainLayout.setStyle("-fx-background-color: #2C3E50;");
    }

    private VBox createHomeContent() {
        Text welcomeMessage = new Text("Welcome, Manager!");
        welcomeMessage.setStyle("-fx-font-size: 24px; -fx-fill: white;");
        welcomeMessage.setEffect(new DropShadow(5, Color.LIGHTGRAY));

        Text managerInfo = new Text("Name: John Doe\nEmail: john.doe@example.com");
        managerInfo.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        managerInfo.setEffect(new DropShadow(3, Color.GRAY));

        Text header = new Text("Dashboard");
        header.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        header.setEffect(new DropShadow(3, Color.GRAY));

        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().addAll(welcomeMessage, managerInfo, header);

        Text lowStockInfo = getLowStockInfo();
        Text lowStockMessage = new Text();
        lowStockMessage.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        homeContent.getChildren().add(lowStockMessage);

        return homeContent;
    }

    private HBox createNavigationBar() {
        HBox navigationBar = new HBox(20);
        navigationBar.setAlignment(Pos.CENTER);
        navigationBar.setStyle("-fx-background-color: #34495E;");

        Button homeButton = createNavButton("Home");
        Button addItemButton = createNavButton("Add New Item");
        Button restockItemButton = createNavButton("Restock Item");
        Button generateReportButton = createNavButton("Generate Sales Report");
        Button manageSuppliersButton = createNavButton("Manage Suppliers");
        Button monitorCashierButton = createNavButton("Monitor Cashier Performance");
        Button viewSectorsButton = createNavButton("View Sectors");
        Button viewItemsButton = createNavButton("View Items");

        homeButton.setOnAction(e -> managerController.openHomePage());
        addItemButton.setOnAction(e -> managerController.openAddItemView());
        restockItemButton.setOnAction(e -> managerController.openRestockItemView());
        generateReportButton.setOnAction(e -> managerController.openGenerateReportView());
        manageSuppliersButton.setOnAction(e -> managerController.openSupplierView());
        monitorCashierButton.setOnAction(e -> managerController.openMonitorCashierPerformanceView());
        viewSectorsButton.setOnAction(e -> managerController.openViewSectorsView());
        viewItemsButton.setOnAction(e -> managerController.openViewItemsView());

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

    public void showHomePage() {
        VBox homeContent = createHomeContent();
        StackPane centerContent = managerController.getCenterContent();
        centerContent.getChildren().clear();
        centerContent.getChildren().add(homeContent);
    }

    private Text getLowStockInfo() {
        ArrayList<Sector> managerSectors = manager.getSectors();
        ArrayList<String> sectorNames = new ArrayList<>();
        for (Sector sector : managerSectors) {
            sectorNames.add(sector.getName());
        }

        ArrayList<Item> lowStockItems = fileHandler.notifyLowStock(5);
        ArrayList<Item> managerLowStockItems = new ArrayList<>();
        for (Item item : lowStockItems) {
            if (sectorNames.contains(item.getItemSector())) {
                managerLowStockItems.add(item);
            }
        }

        if (managerLowStockItems.isEmpty()) {
            lowStockMessage.setText("All items have sufficient stock.");
            lowStockMessage.setFill(Color.GREEN);
        } else {
            StringBuilder lowStockInfo = new StringBuilder("Low Stock Items:\n");
            for (Item item : managerLowStockItems) {
                lowStockInfo.append(item.getItemName()).append(" - Stock: ").append(item.getStockQuantity()).append("\n");
            }
            lowStockMessage.setText(lowStockInfo.toString());
            lowStockMessage.setFill(Color.RED);
        }
        lowStockMessage.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        return lowStockMessage;
    
    }
}