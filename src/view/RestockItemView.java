package view;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Item;
import model.Manager;

import java.util.List;
import java.util.stream.Collectors;

public class RestockItemView {

    private Manager manager;

    // Constructor to pass Manager instance
    public RestockItemView(Manager manager) {
        if (manager == null || manager.getItems() == null) {
            throw new IllegalArgumentException("Manager or item list cannot be null.");
        }
        this.manager = manager;
    }

    // Method to return the VBox view content instead of displaying it directly
    public VBox getViewContent() {
        // Parent layout with a dark blue background
        VBox layout = new VBox(20); // Increased spacing between elements for better alignment
        layout.setAlignment(Pos.CENTER); // Center all components in the window
        layout.setStyle("-fx-background-color: #2C3E50; -fx-padding: 30px; -fx-border-radius: 15px;");

        // Create label and combo box for selecting an item to restock
        Label itemLabel = new Label("Select Item to Restock:");
        itemLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        ComboBox<String> itemComboBox = new ComboBox<>();
        styleComboBox(itemComboBox);

        // Filter items that need restocking (stock < 5)
        List<Item> itemsToRestock = manager.getItems().stream()
                .filter(item -> item.getItemQuantity() < 5) // Only items with stock < 5
                .collect(Collectors.toList());

        // If no items need restocking, show error and return a layout with a message
        if (itemsToRestock.isEmpty()) {
            VBox errorLayout = new VBox(20);
            errorLayout.setAlignment(Pos.CENTER);
            errorLayout.setStyle("-fx-background-color: #2C3E50; -fx-padding: 30px; -fx-border-radius: 15px;");
            Label errorLabel = new Label("No items need restocking.");
            errorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FF6F61;");
            errorLayout.getChildren().add(errorLabel);
            return errorLayout;  // Return a layout with an error message
        }

        // Populate the ComboBox with item names
        itemsToRestock.forEach(item -> itemComboBox.getItems().add(item.getItemName()));

        // Create a quantity input field and button
        Label quantityLabel = new Label("Restock Quantity:");
        quantityLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        TextField quantityField = new TextField();
        styleTextField(quantityField);

        Button restockButton = new Button("Restock Item");

        // Styling the button
        styleButton(restockButton);

        // Restock Button Action
        restockButton.setOnAction(event -> {
            String selectedItem = itemComboBox.getValue();
            int quantity;

            if (selectedItem == null || selectedItem.isEmpty()) {
                showError("Please select an item to restock.");
                return;
            }

            if (quantityField.getText().isEmpty()) {
                showError("Quantity cannot be empty.");
                return;
            }

            try {
                quantity = Integer.parseInt(quantityField.getText());

                if (quantity <= 0) {
                    showError("Please enter a positive number for quantity.");
                    return;
                }

                Item item = findItemByName(selectedItem, itemsToRestock);  // Find item in restock list
                if (item != null) {
                    item.restockItem(quantity);  // Restock the item if found
                    System.out.println("Item restocked: " + selectedItem + " by " + quantity);
                    showSuccess("Item restocked successfully!");
                } else {
                    showError("Item not found: " + selectedItem);
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid number for quantity.");
            }
        });

        // Add components to the layout
        layout.getChildren().addAll(itemLabel, itemComboBox, quantityLabel, quantityField, restockButton);

        return layout;  // Return the VBox layout containing the view content
    }

    // Method to find an item by name from a list of items to restock
    private Item findItemByName(String itemName, List<Item> itemsToRestock) {
        if (itemName == null || itemName.isEmpty() || itemsToRestock == null) {
            return null;
        }

        for (Item item : itemsToRestock) {
            if (item.getItemName().trim().equalsIgnoreCase(itemName.trim())) {
                return item;
            }
        }
        return null;  // Return null if item not found
    }

    // Method to style buttons uniformly with hover effect
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 15px;");
        button.setPrefWidth(220);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 15px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 15px;"));
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

    // Show success message
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

