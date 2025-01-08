package controller;



import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.AddItemView;
import view.RestockItemView;
import view.GenerateReportView;
import view.ViewSectorsView;
import view.ViewItemsView;
import model.Manager;

public class ManagerController {

    private Stage primaryStage;
    private Manager manager;  // Add manager object for handling data
    private Scene managerScene; // Variable to store the manager dashboard scene

    public ManagerController(Stage primaryStage, Manager manager) {
        this.primaryStage = primaryStage;
        this.manager = manager;  // Pass the manager instance
        setupUI();
    }

    private void setupUI() {
        // Main layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 30px;");

        // Welcome Text
        Label welcomeLabel = new Label("Welcome, Manager");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Buttons
        Button addItemButton = createStyledButton("Add New Item");
        Button restockItemButton = createStyledButton("Restock Item");
        Button generateReportButton = createStyledButton("Generate Sales Report");
        Button viewSectorsButton = createStyledButton("View Item Sector");
        Button viewItemsButton = createStyledButton("View Items");

        // Button Actions
        addItemButton.setOnAction(e -> openAddItemView());
        restockItemButton.setOnAction(e -> openRestockItemView());
        generateReportButton.setOnAction(e -> openGenerateReportView());
        viewSectorsButton.setOnAction(e -> openViewSectorsView());
        viewItemsButton.setOnAction(e -> openViewItemsView());

        // Add components to layout
        layout.getChildren().addAll(welcomeLabel, addItemButton, restockItemButton, generateReportButton, viewSectorsButton, viewItemsButton);

        // Scene setup
        managerScene = new Scene(layout, 600, 400);  // Increased window size
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(managerScene);
        primaryStage.centerOnScreen(); // Center window on screen
        primaryStage.show();
    }

    // Method to create styled buttons
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #6a89cc, #4a69bd);" +
                        "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        button.setPrefWidth(250);
        return button;
    }


    // Open AddItemView with manager instance
    private void openAddItemView() {
        primaryStage.close();  // Close current manager dashboard
        AddItemView addItemView = new AddItemView(manager, primaryStage);  // Pass manager instance
        addItemView.showAddItemView();
    }

    // Open RestockItemView with manager instance
    private void openRestockItemView() {
        primaryStage.close();  // Close current manager dashboard
        RestockItemView restockItemView = new RestockItemView(manager, primaryStage);  // Pass manager instance
        restockItemView.showRestockItemView();
    }

    // Open GenerateReportView
    private void openGenerateReportView() {
        primaryStage.close();
        GenerateReportView generateReportView = new GenerateReportView(primaryStage);  // Optionally pass manager
        generateReportView.showGenerateReportView();
    }

    // Open ViewCategoriesView
    private void openViewSectorsView() {
        primaryStage.close();
        ViewSectorsView viewSectorsView = new ViewSectorsView(manager, primaryStage);  // Optionally pass manager
        viewSectorsView.showViewSectorsView();
    }

    // Open ViewItemsView
    private void openViewItemsView() {
        primaryStage.close();
        ViewItemsView viewItemsView = new ViewItemsView(manager, primaryStage);  // Optionally pass manager
        viewItemsView.showViewItemsView();
    }

    // Get the Manager Scene
    public Scene getManagerScene() {
        return managerScene;  // Return the scene created for the manager dashboard
    }
}

