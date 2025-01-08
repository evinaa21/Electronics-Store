package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Item;
import model.Manager;
import javafx.scene.paint.Color;

public class ViewItemsView {
    private Manager manager; // Instance of Manager
    private Stage previousStage;  // Reference to the previous stage

    // Constructor accepts Manager instance and previous Stage
    public ViewItemsView(Manager manager, Stage previousStage) {
        this.manager = manager;
        this.previousStage = previousStage;
    }

    public void showViewItemsView() {
        Stage viewItemsStage = new Stage(); // New stage for viewing items

        // Create ComboBox for sectors with "All Items" option
        Label sectorLabel = new Label("Select Sector:");
        sectorLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        ComboBox<String> sectorComboBox = new ComboBox<>();

        // Populate ComboBox with sector names + "All Items"
        sectorComboBox.getItems().add("All Items"); // Option to view all items
        sectorComboBox.getItems().addAll(manager.viewSector()); // Get sectors from the manager

        // Create a FlowPane for arranging items horizontally from the top left corner
        FlowPane itemsFlowPane = new FlowPane();
        itemsFlowPane.setHgap(20);  // Horizontal gap between items
        itemsFlowPane.setVgap(20);  // Vertical gap between items
        itemsFlowPane.setAlignment(Pos.TOP_LEFT);  // Align items from the top left

        // Add an action listener to filter items based on the selected sector
        sectorComboBox.setOnAction(e -> {
            String selectedSector = sectorComboBox.getValue();
            displayItems(itemsFlowPane, selectedSector); // Filter items based on sector selection
        });

        // Set default prompt text
        sectorComboBox.setPromptText("Choose a Sector");

        // Layout for the ComboBox
        HBox sectorBox = new HBox(15, sectorLabel, sectorComboBox);
        sectorBox.setAlignment(Pos.CENTER_LEFT);

        // Display the items in the FlowPane
        displayItems(itemsFlowPane, "All Items"); // Display all items initially

        // Back button to go back to the previous stage
        Button backButton = new Button("Back");
        styleButton(backButton);  // Apply style to button
        backButton.setOnAction(e -> goBackToManagerDashboard(viewItemsStage));

        // Layout for buttons
        HBox buttonsLayout = new HBox(10, backButton);
        buttonsLayout.setAlignment(Pos.TOP_LEFT);  // Align buttons to the top left

        // Create main layout to hold everything
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 20px; -fx-border-radius: 10px;");
        root.getChildren().addAll(sectorBox, itemsFlowPane, buttonsLayout);

        // Set scene and stage
        Scene scene = new Scene(root, 500, 500);
        viewItemsStage.setTitle("View Items");
        viewItemsStage.setScene(scene);
        viewItemsStage.show();
    }

    // Method to display items in the FlowPane based on the selected sector
    private void displayItems(FlowPane flowPane, String selectedSector) {
        flowPane.getChildren().clear(); // Clear current items in the FlowPane

        // Loop through all items, filtering by sector if selected
        for (Item item : manager.getItems()) {
            // If "All Items" is selected or the sector matches
            if ("All Items".equals(selectedSector) || item.getItemSector().equalsIgnoreCase(selectedSector)) {
                VBox itemBox = new VBox(10);
                itemBox.setAlignment(Pos.CENTER);
                itemBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 8px; -fx-padding: 10px; -fx-background-color: white;");

                // Create ImageView for the item image
                ImageView itemImage = new ImageView(item.getImage());
                itemImage.setFitWidth(150);
                itemImage.setFitHeight(150);
                itemImage.setPreserveRatio(true);

                // Create Label for item name (Heading 1)
                Label itemName = new Label(item.getItemName());
                itemName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");

                // Create Label for item price
                Label itemPrice = new Label("$" + item.getPrice());
                itemPrice.setStyle("-fx-font-size: 14px; -fx-text-fill: #4CAF50;");

                // Create Label for item stock quantity
                Label itemStock = new Label("Stock: " + item.getItemQuantity());
                itemStock.setStyle("-fx-font-size: 14px; -fx-text-fill: #f44336;");

                // Add the image, name, price, and stock to the itemBox
                itemBox.getChildren().addAll(itemImage, itemName, itemPrice, itemStock);

                // Add itemBox to the FlowPane
                flowPane.getChildren().add(itemBox);
                
                // Apply shadow effect on mouse hover
                DropShadow shadow = new DropShadow(10, Color.BLACK);
                itemBox.setOnMouseEntered(e -> itemBox.setEffect(shadow));
                itemBox.setOnMouseExited(e -> itemBox.setEffect(null));
            }
        }
    }

    // Apply style to buttons
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #214e60; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: 10px;");
        button.setPrefWidth(150);
        button.setMaxHeight(40);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #5ca7c6; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #214e60; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: 10px;"));
    }

    // Method to go back to the previous stage (Manager Dashboard)
    private void goBackToManagerDashboard(Stage viewItemsStage) {
        // Close the current ViewItems stage
        viewItemsStage.close();
        // Show the previous (Manager Dashboard) stage
        if (previousStage != null) {
            previousStage.show();
        }
    }
}

