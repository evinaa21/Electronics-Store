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
import util.FileHandler;

import java.util.ArrayList;

public class SupplierView {

    private SupplierController supplierController;
    private Manager manager;

    private FileHandler fileHandler;


    public SupplierView(Manager manager, FileHandler fileHandler) {
        this.manager = manager;
        this.fileHandler = fileHandler;

        // Initialize the SupplierController
        this.supplierController = new SupplierController(manager, this, fileHandler);

        // Load suppliers and items from file with null checks
        this.manager.setSuppliers(getNonNullList(fileHandler.loadSuppliers()));
        this.manager.setItems(getNonNullList(fileHandler.loadInventory()));

        // Initialize suppliedItems for each supplier if not already initialized
        for (Supplier supplier : manager.getSuppliers()) {
            if (supplier.getSuppliedItems() == null) {
                supplier.setSuppliedItems(new ArrayList<>()); // Initialize with an empty list if null
            }
        }

        // Debugging logs
        System.out.println("Suppliers loaded from file: " + manager.getSuppliers());
        System.out.println("Items loaded from file: " + manager.getItems());
    }


    public VBox getViewContent() {
        VBox supplierLayout = new VBox(30);
        ListView<HBox> supplierListView = new ListView<>();
        
        // Set VBox to fill the available space
        supplierLayout.setPrefWidth(Double.MAX_VALUE);
        supplierLayout.setPrefHeight(Double.MAX_VALUE);

        // Add each supplier to the ListView
        for (Supplier supplier : manager.getSuppliers()) {
            HBox supplierItem = createSupplierItem(supplier, supplierListView);
            supplierListView.getItems().add(supplierItem);
        }

        // Add button to add a new supplier
        Button addSupplierButton = new Button("Add Supplier");
        addSupplierButton.setOnAction(e -> showAddSupplierDialog(supplierListView));

        // Set the button to expand to fill the available width
        addSupplierButton.setMaxWidth(100);

        supplierLayout.getChildren().addAll(supplierListView, addSupplierButton);
        supplierLayout.setPadding(new Insets(20));

        // Allow the VBox to expand
        VBox.setVgrow(supplierListView, Priority.ALWAYS); // Let the ListView take all the available vertical space

        return supplierLayout;
    }


    private HBox createSupplierItem(Supplier supplier, ListView<HBox> supplierListView) {
        VBox itemListLayout = new VBox(5);
        itemListLayout.setPadding(new Insets(5));
        itemListLayout.setVisible(false);

        // Populate the item list for the supplier
        itemListLayout.getChildren().clear();
        for (Item item : supplier.getSuppliedItems()) {
            HBox itemRow = new HBox(10);
            itemRow.setAlignment(Pos.CENTER_LEFT);

            Label itemLabel = new Label(item.toString());
            itemRow.getChildren().add(itemLabel);
            itemListLayout.getChildren().add(itemRow);
        }

        HBox supplierItem = new HBox(10);
        supplierItem.setAlignment(Pos.CENTER_LEFT);
        supplierItem.setPadding(new Insets(5));

        Label supplierNameLabel = new Label(supplier.getSupplierName());
        Button toggleItemsButton = new Button("Show Items");

        toggleItemsButton.setOnAction(e -> {
            itemListLayout.setVisible(!itemListLayout.isVisible());
            toggleItemsButton.setText(itemListLayout.isVisible() ? "Hide Items" : "Show Items");
        });

        supplierItem.getChildren().addAll(supplierNameLabel, toggleItemsButton, itemListLayout);
        return supplierItem;
    }

   
    private void showAddSupplierDialog(ListView<HBox> supplierListView) {
        // Create a dialog to enter the supplier's name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Supplier");
        dialog.setHeaderText("Enter Supplier Name");
        dialog.setContentText("Supplier Name:");

        dialog.showAndWait().ifPresent(supplierName -> {
            if (!supplierName.trim().isEmpty()) {
                // Add the supplier with only the name and default contact info
                supplierController.addSupplier(supplierName, supplierListView);
                refreshSupplierList(supplierListView);  // Refresh the list to show the new supplier
            } else {
                showErrorDialog("Supplier name cannot be empty.");
            }
        });
    }

    public void refreshSupplierList(ListView<HBox> supplierListView) {
        supplierListView.getItems().clear(); // Clear the existing items first
        for (Supplier supplier : manager.getSuppliers()) {
            HBox supplierItem = createSupplierItem(supplier, supplierListView);
            supplierListView.getItems().add(supplierItem); // Add the new items
        }
    }

    public void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void showSuccessDialog(String successMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }


    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    // Utility method to handle null lists
    private <T> ArrayList<T> getNonNullList(ArrayList<T> list) {
        return list != null ? list : new ArrayList<>();
    }

}
