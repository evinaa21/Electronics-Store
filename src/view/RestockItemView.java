package view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import model.Item;
import model.Manager;

import java.util.List;
import java.util.stream.Collectors;

public class RestockItemView {

    private Manager manager; // Instance variable for Manager
    private Stage previousStage; // To store the reference to the previous stage

    // Constructor to pass the Manager instance and previous Stage reference
    public RestockItemView(Manager manager, Stage previousStage) {
        this.manager = manager;
        this.previousStage = previousStage;
    }

    // Method to display the restock item view
    public void showRestockItemView() {
        Stage restockItemStage = new Stage();

        // Create label and combo box for selecting an item to restock
        Label itemLabel = new Label("Select Item to Restock:");
        itemLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        ComboBox<String> itemComboBox = new ComboBox<>();
        styleComboBox(itemComboBox);

        // Filter items that need restocking (stock < 5)
        List<Item> itemsToRestock = manager.getItems().stream()
            .filter(item -> item.getItemQuantity() < 5) // Only items with stock < 5
            .collect(Collectors.toList());

        // If no items need restocking, go back to the manager dashboard
        if (itemsToRestock.isEmpty()) {
            goBackToPreviousWindow(restockItemStage);
            showError("No items need restocking.");
            return;
        }

        // Add the names of items that need restocking to the ComboBox
        for (Item item : itemsToRestock) {
            itemComboBox.getItems().add(item.getItemName());
        }

        // Create a quantity input field and button
        Label quantityLabel = new Label("Restock Quantity:");
        quantityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        TextField quantityField = new TextField();
        styleTextField(quantityField);

        Button restockButton = new Button("Restock Item");
        Button backButton = new Button("Back");

        // Restock Button Action
        restockButton.setOnAction(event -> {
            String selectedItem = itemComboBox.getValue();
            int quantity = 0;

            if (selectedItem == null || selectedItem.isEmpty()) {
                showError("Please select an item to restock.");
                return;
            }

            try {
                quantity = Integer.parseInt(quantityField.getText());
                Item item = findItemByName(selectedItem, itemsToRestock);  // Find item in restock list
                if (item != null) {
                    item.restockItem(quantity);  // Restock the item if found
                    System.out.println("Item restocked: " + selectedItem + " by " + quantity);
                } else {
                    showError("Item not found: " + selectedItem);
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid number for quantity.");
            }
        });

        // Back Button Action
        backButton.setOnAction(e -> goBackToPreviousWindow(restockItemStage));

        // Layout with buttons and fields
        VBox layout = new VBox(20); // Increased spacing between elements for better alignment
        layout.setAlignment(Pos.CENTER); // Center all components in the window
        layout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 30px; -fx-border-radius: 15px;");

        layout.getChildren().addAll(itemLabel, itemComboBox, quantityLabel, quantityField, restockButton, backButton);
        
        // Styling the buttons
        styleButton(restockButton);
        styleButton(backButton);

        // Scene setup with layout and dimensions
        Scene scene = new Scene(layout, 450, 400);
        restockItemStage.setTitle("Restock Item");
        restockItemStage.setScene(scene);
        restockItemStage.show();
    }

    // Method to find an item by name from a list of items to restock
    private Item findItemByName(String itemName, List<Item> itemsToRestock) {
        for (Item item : itemsToRestock) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;  // Return null if item not found
    }

    // Go back to the previous window (Manager Dashboard or previous view)
    private void goBackToPreviousWindow(Stage restockItemStage) {
        // Close the current window
        restockItemStage.close();
        
        // Show the previous window again
        if (previousStage != null) {
            previousStage.show();
        }
    }

    // Method to style buttons uniformly with hover effect
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 15px;");
        button.setPrefWidth(220);
        button.setMaxHeight(45);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 15px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 15px;"));
    }

    // Method to style combo box
    private void styleComboBox(ComboBox<String> comboBox) {
        comboBox.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-border-color: #cccccc;");
        comboBox.setPrefWidth(280);
        comboBox.setMaxHeight(35);
    }

    // Method to style text fields with a uniform look
    private void styleTextField(TextField textField) {
        textField.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-padding: 15px; -fx-border-color: #cccccc;");
        textField.setPrefWidth(280);
        textField.setMaxHeight(35);
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

