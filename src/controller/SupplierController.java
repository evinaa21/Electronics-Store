package controller;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import model.Manager;
import model.Supplier;
import util.FileHandler;
import model.Item;
import view.SupplierView;

public class SupplierController {
    private Manager manager;
    private SupplierView supplierView;
    private FileHandler fileHandler;

    public SupplierController(Manager manager, SupplierView supplierView, FileHandler fileHandler) {
        this.manager = manager;
        this.supplierView = supplierView;
        this.fileHandler = fileHandler;

        // Load suppliers and items from file and set them to the manager
        ArrayList<Supplier> suppliers = fileHandler.loadSuppliers();
        ArrayList<Item> items = fileHandler.loadInventory();
        manager.setSuppliers(suppliers);
        manager.setItems(items);

        // Debugging logs
        System.out.println("Suppliers loaded: " + suppliers);
        System.out.println("Items loaded: " + items);
    }


    public void addSupplier(String name, ListView<HBox> supplierListView) {
        Supplier supplier = new Supplier(name);  // Only pass the name

        manager.getSuppliers().add(supplier);
        saveSuppliersToFile();  // Ensure this is called after adding the supplier
        supplierView.refreshSupplierList(supplierListView);
        supplierView.showSuccessDialog("Supplier added successfully.");
    }
    

    // Edit an existing supplier
    public void editSupplier(Supplier supplier, String newName, String newContactInfo, ListView<HBox> supplierListView) {
        supplier.setSupplierName(newName);
        supplier.setContactInfo(newContactInfo);
        saveSuppliersToFile();
        supplierView.refreshSupplierList(supplierListView);
        supplierView.showSuccessDialog("Supplier updated successfully.");
    }

    // Delete a supplier
    public void deleteSupplier(Supplier supplier, ListView<HBox> supplierListView) {
        manager.getSuppliers().remove(supplier);
        saveSuppliersToFile();
        supplierView.refreshSupplierList(supplierListView);
        supplierView.showSuccessDialog("Supplier deleted successfully.");
    }

    // Add an item to a supplier
    public void addItemToSupplier(Supplier supplier, Item item, ListView<HBox> supplierListView) {
        if (item != null) {
            supplier.addItem(item);
            saveSuppliersToFile();
            fileHandler.saveInventory(manager.getItems());
            supplierView.refreshSupplierList(supplierListView);
            supplierView.showSuccessDialog("Item added successfully to supplier.");
        } else {
            supplierView.showErrorDialog("Invalid item selected.");
        }
    }

 // Delete an item globally and update suppliers
    public void deleteItemGlobally(Item item, ListView<HBox> itemListView) {
        // Ensure item removal is consistent
        fileHandler.deleteItemAndUpdateSuppliers(item, manager.getItems(), manager.getSuppliers());

        // Refresh the items and suppliers views
        itemListView.getItems().removeIf(hbox -> ((Label) hbox.getChildren().get(0)).getText().equals(item.getItemName())); // Example logic for updating UI
        supplierView.refreshSupplierList(itemListView); // Refresh the supplier view to reflect changes
    }


    // Save suppliers to the binary file
    private void saveSuppliersToFile() {
        fileHandler.saveSuppliers(manager.getSuppliers());
    }
}
