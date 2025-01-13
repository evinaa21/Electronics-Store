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
import util.FileHandler;

import java.util.List;
import java.util.stream.Collectors;

public class RestockItemView {

    private Manager manager;
    private FileHandler fileHandler;


    // Constructor to pass Manager and FileHandler instances
    public RestockItemView(Manager manager, FileHandler fileHandler) {
        if (manager == null || manager.getItems() == null) {
            throw new IllegalArgumentException("Manager or item list cannot be null.");
        }
        this.manager = manager;
        this.fileHandler = fileHandler;
    }

    public VBox getViewContent() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2C3E50; -fx-padding: 30px; -fx-border-radius: 15px;");

        // Label and ComboBox for selecting an item to restock
        Label itemLabel = new Label("Select Item to Restock:");
        itemLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        ComboBox<String> itemComboBox = new ComboBox<>();
        styleComboBox(itemComboBox);

        // Filter items that need restocking (stock < 5)
        List<Item> itemsToRestock = manager.getItems().stream()
                .filter(item -> item.getItemQuantity() < 5)
                .collect(Collectors.toList());

        if (itemsToRestock.isEmpty()) {
            VBox errorLayout = new VBox(20);
            errorLayout.setAlignment(Pos.CENTER);
            errorLayout.setStyle("-fx-background-color: #2C3E50; -fx-padding: 30px; -fx-border-radius: 15px;");
            Label errorLabel = new Label("No items need restocking.");
            errorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FF6F61;");
            errorLayout.getChildren().add(errorLabel);
            return errorLayout;
        }

        itemsToRestock.forEach(item -> itemComboBox.getItems().add(item.getItemName()));

        // Quantity input field and restock button
        Label quantityLabel = new Label("Restock Quantity:");
        quantityLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        TextField quantityField = new TextField();
        styleTextField(quantityField);

        Button restockButton = new Button("Restock Item");
        styleButton(restockButton);

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

                // Find the selected item in the list of items to restock
                Item item = findItemByName(selectedItem, itemsToRestock);
                if (item != null) {
                    // Restock the item
                    item.restockItem(quantity);

                    // Save updated inventory list back to the file
                    fileHandler.saveInventory(manager.getItems());

                    showSuccess("Item restocked successfully!");

                    // Refresh the ComboBox after restocking
                    itemComboBox.getItems().clear();
                    itemsToRestock.forEach(i -> itemComboBox.getItems().add(i.getItemName()));

                } else {
                    showError("Item not found: " + selectedItem);
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid number for quantity.");
            }
        });

        layout.getChildren().addAll(itemLabel, itemComboBox, quantityLabel, quantityField, restockButton);
        return layout;
    }

    private Item findItemByName(String itemName, List<Item> itemsToRestock) {
        for (Item item : itemsToRestock) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px;");
 
    }

    private void styleComboBox(ComboBox<String> comboBox) {
        comboBox.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff;");
        comboBox.setPrefWidth(280);
    }

    private void styleTextField(TextField textField) {
        textField.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff;");
        textField.setPrefWidth(280);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }





    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
}

