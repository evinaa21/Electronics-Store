package controller;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import model.Manager;
import model.Supplier;
import model.Item;
import view.SupplierView; // Import SupplierView

public class SupplierController {
    private Manager manager;
    private SupplierView supplierView;  // Declare the SupplierView

    public SupplierController(Manager manager, SupplierView supplierView) {
        this.manager = manager;
        this.supplierView = supplierView;  // Initialize the SupplierView
    }

    // Add a new supplier
    public void addSupplier(String name, String contactInfo, ListView<HBox> supplierListView) {
        Supplier supplier = new Supplier(name, contactInfo);
        manager.getSuppliers().add(supplier);
        supplierView.refreshSupplierList(supplierListView);  // Pass the ListView to refresh it
        supplierView.showSuccessDialog("Supplier added successfully.");
    }

    // Handle editing an existing supplier
    public void editSupplier(Supplier supplier, String newName, String newContactInfo, ListView<HBox> supplierListView) {
        supplier.setSupplierName(newName);
        supplier.setContactInfo(newContactInfo);
        supplierView.refreshSupplierList(supplierListView);  // Update the view with the ListView
        supplierView.showSuccessDialog("Supplier updated successfully.");
    }

    // Handle deleting a supplier
    public void deleteSupplier(Supplier supplier, ListView<HBox> supplierListView) {
        manager.getSuppliers().remove(supplier);
        supplierView.refreshSupplierList(supplierListView);  // Update the view with the ListView
        supplierView.showSuccessDialog("Supplier deleted successfully.");
    }

    public void addItemToSupplier(Supplier supplier, Item item, ListView<HBox> supplierListView) {
        if (item != null) {
            supplier.addItem(item);  // Add the item to the supplier's list
            supplierView.refreshSupplierList(supplierListView);  // Refresh the supplier list view
            supplierView.showSuccessDialog("Item added successfully to supplier.");
        } else {
            supplierView.showErrorDialog("Invalid item selected.");
        }
    }


    // Remove an item from a supplier
    public void removeItemFromSupplier(Supplier supplier, Item item, ListView<HBox> supplierListView) {
        supplier.removeItem(item);
        supplierView.refreshSupplierList(supplierListView);  // Refresh the view to remove the item
        supplierView.showSuccessDialog("Item removed from supplier successfully.");
    }

    // Handle displaying error dialogs from the SupplierView
    public void showError(String errorMessage) {
        supplierView.showErrorDialog(errorMessage);
    }

    // Handle displaying success dialogs from the SupplierView
    public void showSuccess(String successMessage) {
        supplierView.showSuccessDialog(successMessage);
    }
}
