

package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import model.Manager;
import controller.ManagerController;

public class RestockItemView {

    private Manager manager; // Instance variable for Manager

    // Constructor to pass the Manager instance
    public RestockItemView(Manager manager) {
        this.manager = manager;
    }

    // Method to display the restock item view
    public void showRestockItemView() {
        Stage restockItemStage = new Stage();
        
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();
        
        Label quantityLabel = new Label("Restock Quantity:");
        TextField quantityField = new TextField();
        
        Button restockButton = new Button("Restock Item");
        Button backButton = new Button("Back");  // Add Back Button

        // Restock Button Action
        restockButton.setOnAction(event -> {
            String itemName = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            Item item = findItemByName(itemName);  // Find the item by name
            if (item != null) {
                item.restockItem(quantity);  // Restock the item if found
                System.out.println("Item restocked: " + itemName + " by " + quantity);
            } else {
                System.out.println("Item not found: " + itemName);
            }
        });

        // Back Button Action
        backButton.setOnAction(e -> goBackToManagerDashboard(restockItemStage));

        // Layout with buttons and fields
        VBox layout = new VBox(10, nameLabel, nameField, quantityLabel, quantityField, restockButton, backButton);
        
        Scene scene = new Scene(layout, 300, 250);
        restockItemStage.setTitle("Restock Item");
        restockItemStage.setScene(scene);
        restockItemStage.show();
    }

    // Method to find an item by name
    private Item findItemByName(String itemName) {
        for (Item item : manager.getItems()) { // Assuming manager has getItems() method
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;  // Return null if item not found
    }

    // Go back to the Manager Dashboard
    private void goBackToManagerDashboard(Stage restockItemStage) {
        // Close the current window
        restockItemStage.close();
        
        // Show the Manager Dashboard again
        ManagerController managerController = new ManagerController(new Stage(), manager);
        Scene managerScene = managerController.getManagerScene();
        
        Stage managerStage = new Stage();
        managerStage.setTitle("Manager Dashboard");
        managerStage.setScene(managerScene);
        managerStage.show();
    }
}
