package controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.AddItemView;
import view.RestockItemView;
import view.SupplierView;
import view.GenerateReportView;
import view.ViewSectorsView;
import view.ViewItemsView;
import model.Manager;

public class ManagerController {

    private Stage primaryStage;
    private Manager manager;
    private BorderPane mainLayout; // Main BorderPane layout
    private StackPane centerContent; // Container for dynamic center content
    private Scene managerScene; // Variable to store the manager dashboard scene

    public ManagerController(Stage primaryStage, Manager manager) {
        this.primaryStage = primaryStage;
        this.manager = manager;  // Pass the manager instance
        this.mainLayout = new BorderPane();
        this.centerContent = new StackPane(); // Initialize StackPane for the center content
        setupUI();
    }

    private void setupUI() {
        // Header content (Welcome message and dashboard title)
        Text welcomeMessage = new Text("Welcome, Manager!");
        welcomeMessage.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white;");
        
        // Manager information (sample info)
        Text managerInfo = new Text("Name: John Doe\nEmail: john.doe@example.com");
        managerInfo.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        
        // Header below the welcome message
        Text header = new Text("Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        // Top navigation bar (HBox)
        HBox navigationBar = createNavigationBar(primaryStage);
        navigationBar.setStyle(
                "-fx-background-color: #34495E;" + // Dark blue background
                "-fx-padding: 10px; -fx-alignment: center;"
        );

        // Set the header and center content in the main layout
        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().addAll(welcomeMessage, managerInfo, header);
        centerContent.getChildren().add(homeContent); // Add content to center

        // Set layout using BorderPane
        mainLayout.setTop(navigationBar);
        mainLayout.setCenter(centerContent); // Set the center part
        mainLayout.setStyle("-fx-background-color: #2C3E50;"); // Dark background color

        // Scene setup
        managerScene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(managerScene);
        primaryStage.centerOnScreen(); // Center window on screen
        primaryStage.show();
    }

    // Create top navigation bar (HBox)
    private HBox createNavigationBar(Stage primaryStage) {
        HBox navigationBar = new HBox(20);
        navigationBar.setAlignment(Pos.CENTER);

        Button homeButton = createNavButton("Home", primaryStage);
        Button addItemButton = createNavButton("Add New Item", primaryStage);
        Button restockItemButton = createNavButton("Restock Item", primaryStage);
        Button generateReportButton = createNavButton("Generate Sales Report", primaryStage);
        Button viewSectorsButton = createNavButton("View Item Sector", primaryStage);
        Button viewItemsButton = createNavButton("View Items", primaryStage);
        Button manageSuppliersButton = createNavButton("Manage Suppliers", primaryStage); // New Button for Suppliers

        // Button actions
        homeButton.setOnAction(e -> openHomePage());
        addItemButton.setOnAction(e -> openView(new AddItemView(manager)));
        restockItemButton.setOnAction(e -> openView(new RestockItemView(manager)));
        generateReportButton.setOnAction(e -> openView(new GenerateReportView(manager)));
        viewSectorsButton.setOnAction(e -> openView(new ViewSectorsView(manager)));
        viewItemsButton.setOnAction(e -> openView(new ViewItemsView(manager)));
        manageSuppliersButton.setOnAction(e -> openView(new SupplierView(manager))); // Action for Suppliers Button

        // Add buttons to the navigation bar
        navigationBar.getChildren().addAll(
                homeButton,
                addItemButton,
                restockItemButton,
                generateReportButton,
                viewSectorsButton,
                viewItemsButton,
                manageSuppliersButton // Add the new Suppliers button to the layout
        );

        return navigationBar;
    }


    // Create navigation bar buttons with style
    private Button createNavButton(String text, Stage primaryStage) {
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

        // Hover effect
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #2980B9;" + // Hover background
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10px 15px;" +
                "-fx-border-color: #95a5a6;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +
                "-fx-background-radius: 5px;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10px 15px;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +
                "-fx-background-radius: 5px;"
        ));

        return button;
    }

    // Update center content dynamically
    private void updateCenterContent(VBox viewContent) {
        centerContent.getChildren().clear(); // Clear the previous content
        centerContent.getChildren().add(viewContent); // Add the new content
    }

    // General method to open any view and update the center content
    private void openView(Object view) {
        VBox viewContent = null;
        if (view instanceof AddItemView) {
            viewContent = new AddItemView(manager).getViewContent();
        } else if (view instanceof RestockItemView) {
            viewContent = new RestockItemView(manager).getViewContent();
        } else if (view instanceof GenerateReportView) {
            viewContent = new GenerateReportView(manager).getViewContent();
        } else if (view instanceof ViewSectorsView) {
            viewContent = new ViewSectorsView(manager).getViewContent();
        } else if (view instanceof ViewItemsView) {
            viewContent = new ViewItemsView(manager).getViewContent();
        } else if (view instanceof SupplierView) {  // Handle SupplierView
            viewContent = new SupplierView(manager).getViewContent();
        } else {
            throw new IllegalArgumentException("Unsupported view type: " + view.getClass());
        }
        updateCenterContent(viewContent);
    }

    // Open the Home page and display manager info
    private void openHomePage() {
        // Create welcome message and manager info
        Text welcomeMessage = new Text("Welcome, Manager!");
        welcomeMessage.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white;");
        
        Text managerInfo = new Text("Name: John Doe\nEmail: john.doe@example.com");
        managerInfo.setStyle("-fx-font-size: 18px; -fx-fill: white;");
        
        Text header = new Text("Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        // Set the content in the center
        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.getChildren().addAll(welcomeMessage, managerInfo, header);
        updateCenterContent(homeContent); // Display home content
    }

    // Get the Manager Scene
    public Scene getManagerScene() {
        return managerScene;  // Return the scene created for the manager dashboard
    }
}

