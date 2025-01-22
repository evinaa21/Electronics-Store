package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import model.Item;

import model.Supplier;
import util.FileHandlerMANAGER;

import java.util.ArrayList;


public class AddItemController {
	private Manager manager;
    private FileHandlerMANAGER fileHandler;
    public AddItemController(Manager manager) {
        this.manager = manager;
    }
    public AddItemController(Manager manager, FileHandlerMANAGER fileHandler) {
        this.manager = manager;
        this.fileHandler = fileHandler;

        showAddItemView();
    }

    public void showAddItemView() {
        Stage addItemStage = new Stage();

        // Fields for item details
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();

        Label categoryLabel = new Label("Item Category:");
        TextField categoryField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label stockLabel = new Label("Stock Quantity:");
        TextField stockField = new TextField();

        // Combobox i need for suppliers
        Label supplierLabel = new Label("Select Supplier:");
        ComboBox<String> supplierComboBox = new ComboBox<>();
        
        //  This Loads suppliers into the ComboBox
        ArrayList<String> supplierNames = manager.getSupplierNames(); 
        if (supplierNames != null && !supplierNames.isEmpty()) {
            supplierComboBox.getItems().addAll(supplierNames);
        } else {
            supplierComboBox.setPromptText("No suppliers available");
        }

        //THIS IS BUTTON FOR SAVING ITEM
        Button saveButton = new Button("Save Item");
        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String category = categoryField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            String supplierName = supplierComboBox.getValue();

            // Validation if fields are empty
            if (name.isEmpty() || category.isEmpty() || priceText.isEmpty() || stockText.isEmpty() || supplierName == null) {
                showError("All fields are required, including supplier selection!");
                return;
            }

            addItemStage.close(); 

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);

                Item newItem = new Item(name, category, price, stock, 0);

                // Adds item to inventory via FileHandler and Manager
                ArrayList<Item> currentItems = fileHandler.loadInventory(); 
                currentItems.add(newItem);
                fileHandler.saveInventory(currentItems); 
                ArrayList<Supplier> suppliers = fileHandler.loadSuppliers(); 
                for (Supplier supplier : suppliers) {
                    if (supplier.getSupplierName().equals(supplierName)) {
                        supplier.getSuppliedItems().add(newItem); // it associates item to supplier
                        break;
                    }
                }

                fileHandler.saveSuppliers(suppliers);

                manager.setItems(currentItems);
                showSuccess("Item added successfully!");
                addItemStage.close();

            } catch (NumberFormatException e) {
                showError("Price and stock must be numeric!");
            }

        });

        VBox layout = new VBox(10, nameLabel, nameField, categoryLabel, categoryField, priceLabel, priceField, stockLabel, supplierLabel, supplierComboBox, saveButton);
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 300, 400);
        addItemStage.setScene(scene);
        addItemStage.setTitle("Add New Item");
        addItemStage.show();
    }
    //Alerts
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
