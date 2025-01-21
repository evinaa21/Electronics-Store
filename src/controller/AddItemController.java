package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Manager;
import model.Item;

import model.Supplier;
import util.FileHandlerMANAGER;

import java.util.ArrayList;
import java.util.List;


public class AddItemController {

    private Manager manager;
    private FileHandlerMANAGER fileHandler;

    public AddItemController(Manager manager) {
        this.manager = manager;
    }
  

    // Constructor accepts Manager and FileHandler to manage file operations
    public AddItemController(Manager manager, FileHandlerMANAGER fileHandler) {
        this.manager = manager;
        this.fileHandler = fileHandler;

        showAddItemView();
    }

    public void showAddItemView() {
        Stage addItemStage = new Stage();

        // Create form fields for item details
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();

        Label categoryLabel = new Label("Item Category:");
        TextField categoryField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label stockLabel = new Label("Stock Quantity:");
        TextField stockField = new TextField();

        // Supplier ComboBox
        Label supplierLabel = new Label("Select Supplier:");
        ComboBox<String> supplierComboBox = new ComboBox<>();
        
        // Load suppliers into the ComboBox
        List<String> supplierNames = manager.getSupplierNames(); // Assuming this method exists in Manager
        if (supplierNames != null && !supplierNames.isEmpty()) {
            supplierComboBox.getItems().addAll(supplierNames);
        } else {
            supplierComboBox.setPromptText("No suppliers available");
        }

        // Create a save button
        Button saveButton = new Button("Save Item");
        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String category = categoryField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            String supplierName = supplierComboBox.getValue();

            // Validate input fields
            if (name.isEmpty() || category.isEmpty() || priceText.isEmpty() || stockText.isEmpty() || supplierName == null) {
                showError("All fields are required, including supplier selection!");
                return;
            }


            addItemStage.close(); // Close the add item window

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);

                // Create new item
                Item newItem = new Item(name, category, price, stock, 0, stock);

                // Add item to inventory via FileHandler and Manager
                ArrayList<Item> currentItems = fileHandler.loadInventory(); // Load existing items
                currentItems.add(newItem); // Add new item
                fileHandler.saveInventory(currentItems); // Save updated item list

                // Find the supplier and associate the item
                ArrayList<Supplier> suppliers = fileHandler.loadSuppliers(); // Load existing suppliers
                for (Supplier supplier : suppliers) {
                    if (supplier.getSupplierName().equals(supplierName)) {
                        supplier.getSuppliedItems().add(newItem); // Associate the item to the supplier
                        break;
                    }
                }

                // Save updated supplier list back to the file
                fileHandler.saveSuppliers(suppliers);

                // Update manager's item list
                manager.setItems(currentItems);

                // Show success message and close the Add Item window
                showSuccess("Item added successfully!");
                addItemStage.close();

            } catch (NumberFormatException e) {
                showError("Price and stock must be numeric!");
            }

        });

        VBox layout = new VBox(10, nameLabel, nameField, categoryLabel, categoryField, priceLabel, priceField, stockLabel, stockField, supplierLabel, supplierComboBox, saveButton);
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 300, 400);
        addItemStage.setScene(scene);
        addItemStage.setTitle("Add New Item");
        addItemStage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
