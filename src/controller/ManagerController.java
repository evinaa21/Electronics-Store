package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        VBox layout = new VBox(10);

        Button addItemButton = new Button("Add New Item");
        Button restockItemButton = new Button("Restock Item");
        Button generateReportButton = new Button("Generate Sales Report");
        Button viewCategoriesButton = new Button("View Item Categories");
        Button viewItemsButton = new Button("View Items");

        // Pass manager instance to views so they can interact with it
        addItemButton.setOnAction(e -> openAddItemView());
        restockItemButton.setOnAction(e -> openRestockItemView());
        generateReportButton.setOnAction(e -> openGenerateReportView());
        viewCategoriesButton.setOnAction(e -> openViewCategoriesView());
        viewItemsButton.setOnAction(e -> openViewItemsView());

        layout.getChildren().addAll(addItemButton, restockItemButton, generateReportButton, viewCategoriesButton, viewItemsButton);

        managerScene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(managerScene);
        primaryStage.show();
    }

    // Open AddItemView with manager instance
    private void openAddItemView() {
        primaryStage.close();  // Close current manager dashboard
        AddItemView addItemView = new AddItemView(manager);  // Pass manager instance
        addItemView.showAddItemView();
    }

    // Open RestockItemView with manager instance
    private void openRestockItemView() {
        primaryStage.close();  // Close current manager dashboard
        RestockItemView restockItemView = new RestockItemView(manager);  // Pass manager instance
        restockItemView.showRestockItemView();
    }

    // Open GenerateReportView
    private void openGenerateReportView() {
        primaryStage.close();
        GenerateReportView generateReportView = new GenerateReportView();  // Optionally pass manager
        generateReportView.showGenerateReportView();
    }

    // Open ViewCategoriesView
    private void openViewCategoriesView() {
        primaryStage.close();
        ViewSectorsView viewSectorsView = new ViewSectorsView(manager);  // Optionally pass manager
        viewSectorsView.showViewSectorsView();
    }

    // Open ViewItemsView
    private void openViewItemsView() {
        primaryStage.close();
        ViewItemsView viewItemsView = new ViewItemsView(manager);  // Optionally pass manager
        viewItemsView.showViewItemsView();
    }

    // Get the Manager Scene
    public Scene getManagerScene() {
        return managerScene;  // Return the scene created for the manager dashboard
    }
}

