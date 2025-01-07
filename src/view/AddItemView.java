package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;  // Make sure to import your Item class
import model.Manager;
import controller.ManagerController;

public class AddItemView {
    
    // Add a manager instance to interact with the manager class
    private Manager manager;

    // Constructor to pass the Manager instance
    public AddItemView(Manager manager) {
        this.manager = manager;
    }

    // Method to show the Add Item view
    public void showAddItemView() {
        Stage addItemStage = new Stage();
        
        // UI Components
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();
        
        Label categoryLabel = new Label("Item Category:");
        TextField categoryField = new TextField();
        
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        
        Label stockLabel = new Label("Stock Quantity:");
        TextField stockField = new TextField();
        
        Button saveButton = new Button("Save Item");
        Button backButton = new Button("Back");

        // Save Button Action
        saveButton.setOnAction(event -> {
            // Retrieve input data
            String name = nameField.getText();
            String category = categoryField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            
            // Validate inputs
            if (name.isEmpty() || category.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                showError("All fields must be filled in.");
                return;
            }
            
            double price;
            int stock;
            
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                showError("Price must be a valid number.");
                return;
            }
            
            try {
                stock = Integer.parseInt(stockText);
            } catch (NumberFormatException e) {
                showError("Stock quantity must be a valid integer.");
                return;
            }
            
            // Create a new Item object
            Item newItem = new Item(name, category, price, stock);
            
            // Call the manager's addNewItem method
            manager.addNewItem(newItem);
            
            // For now, print to console as placeholder for item addition
            System.out.println("Item Added: " + name + ", " + category + ", " + price + ", " + stock);
            
            // Close the Add Item window after saving
            addItemStage.close();
        });

        // Back Button Action
        backButton.setOnAction(e -> goBackToManagerDashboard(addItemStage));

        // Layout with buttons and fields
        VBox layout = new VBox(10, nameLabel, nameField, categoryLabel, categoryField, priceLabel, priceField, stockLabel, stockField, saveButton, backButton);
        
        Scene scene = new Scene(layout, 300, 350);
        addItemStage.setTitle("Add New Item");
        addItemStage.setScene(scene);
        addItemStage.show();
    }

    // Go back to the Manager Dashboard
    private void goBackToManagerDashboard(Stage addItemStage) {
        // Close the current window
        addItemStage.close();
        
        // Show the Manager Dashboard again
        ManagerController managerController = new ManagerController(new Stage(), manager);
        Scene managerScene = managerController.getManagerScene();
        
        Stage managerStage = new Stage();
        managerStage.setTitle("Manager Dashboard");
        managerStage.setScene(managerScene);
        managerStage.show();
    }

    // Show error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

