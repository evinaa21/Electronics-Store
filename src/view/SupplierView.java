package view;

import controller.SupplierController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import model.Manager;
import model.Supplier;
import model.Item;

public class SupplierView {

    private SupplierController supplierController;  // Declare the controller
    private Manager manager;

    public SupplierView(Manager manager) {
        this.manager = manager;
        this.supplierController = new SupplierController(manager, this);  // Pass the view to the controller
    }

    // Method to show the Supplier list view with suppliers and their items
    public VBox getViewContent() {
        VBox supplierLayout = new VBox(10);
        ListView<HBox> supplierListView = new ListView<>();
        
        // Add each supplier and their items to the ListView
        for (Supplier supplier : manager.getSuppliers()) {
            HBox supplierItem = createSupplierItem(supplier, supplierListView); // Pass the ListView to handle adding items
            supplierListView.getItems().add(supplierItem);
        }

        // Add button to add a new supplier
        Button addSupplierButton = new Button("Add Supplier");
        addSupplierButton.setOnAction(e -> showAddSupplierDialog(supplierListView));

        supplierLayout.getChildren().addAll(supplierListView, addSupplierButton);
        supplierLayout.setPadding(new Insets(20));

        return supplierLayout;
    }

    // Method to create a detailed view of a supplier and their items
    private HBox createSupplierItem(Supplier supplier, ListView<HBox> supplierListView) {
        VBox itemListLayout = new VBox(5);
        itemListLayout.setPadding(new Insets(5));

        // Display each item for the supplier
        for (Item item : supplier.getSuppliedItems()) {
            itemListLayout.getChildren().add(new Label(item.toString()));  // Show item name and price
        }

        // Create the layout for the supplier
        HBox supplierItem = new HBox(10);
        supplierItem.setAlignment(Pos.CENTER_LEFT);
        supplierItem.setPadding(new Insets(5));

        // Add supplier name and the item list to the HBox
        Label supplierNameLabel = new Label(supplier.getSupplierName());
        Button addItemButton = new Button("Add Item");

        // Set the action for the "Add Item" button
        addItemButton.setOnAction(e -> showAddItemDialog(supplier, supplierListView));

        // Add supplier name, item list, and button to the HBox
        supplierItem.getChildren().addAll(supplierNameLabel, itemListLayout, addItemButton);
        
        return supplierItem;
    }

    // Method to show the Add Supplier dialog
    private void showAddSupplierDialog(ListView<HBox> supplierListView) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Supplier");
        dialog.setHeaderText("Enter Supplier Details");
        dialog.setContentText("Supplier Name:");

        dialog.showAndWait().ifPresent(supplierName -> {
            if (!supplierName.trim().isEmpty()) {
                supplierController.addSupplier(supplierName, "Default Contact Info", supplierListView); // Pass appropriate values
                refreshSupplierList(supplierListView);  // Pass the ListView to refresh it
            }
        });
    }

    // Method to show the Add Item dialog where the user selects an existing item
    private void showAddItemDialog(Supplier supplier, ListView<HBox> supplierListView) {
        // Create a list of predefined items to choose from
        ListView<Item> predefinedItemsListView = new ListView<>();
        
        // Populate the list with available items from the system (or predefined list)
        predefinedItemsListView.getItems().addAll(manager.getItems());  // Assuming manager has a list of items

        // Create a dialog to allow the user to select an item from the predefined list
        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(10));
        
        Label instructionLabel = new Label("Select an item to add to the supplier:");
        dialogLayout.getChildren().addAll(instructionLabel, predefinedItemsListView);
        
        Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> {
            Item selectedItem = predefinedItemsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                supplierController.addItemToSupplier(supplier, selectedItem, supplierListView);
                refreshSupplierList(supplierListView);  // Refresh the list to show the newly added item
            }
        });
        
        dialogLayout.getChildren().add(addButton);

        // Create a dialog window with the layout
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Item to Supplier");
        dialogStage.setScene(new Scene(dialogLayout, 300, 200));
        dialogStage.show();
    }

    // Method to refresh the Supplier list view after adding a supplier or an item
    public void refreshSupplierList(ListView<HBox> supplierListView) {
        supplierListView.getItems().clear();  // Clear the current list
        for (Supplier supplier : manager.getSuppliers()) {
            HBox supplierItem = createSupplierItem(supplier, supplierListView);  // Create the supplier and their items
            supplierListView.getItems().add(supplierItem);  // Add the updated list
        }
    }

    // Utility method for showing error dialog
    public void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    // Utility method for showing success dialog
    public void showSuccessDialog(String successMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }
}
