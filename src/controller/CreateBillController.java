package controller;

import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Bill;
import model.Item;
import util.FileHandler;

import java.util.ArrayList;
import java.util.Date;

public class CreateBillController {
    private final FileHandler fileHandler; // Handles file operations
    private final VBox itemsContainer; // Container for displaying items added to the bill
    private final TextField totalField; // Field to display the current total amount
    private final ComboBox<String> categoryDropdown; // Drop down for selecting categories
    private final ComboBox<String> itemDropdown; // Drop down for selecting items
    private final ArrayList<Item> inventory; // Full inventory for the cashier's assigned sector
    private final ArrayList<Item> billItems; //Items added to the current bill
    private final String assignedSector; // Sector assigned to the cashier

    // Constructor to initialize the controller with UI elements and assigned sector
    public CreateBillController(
            VBox itemsContainer,
            TextField totalField,
            ComboBox<String> categoryDropdown,
            ComboBox<String> itemDropdown,
            String assignedSector) {
        this.fileHandler = new FileHandler();
        this.itemsContainer = itemsContainer;
        this.totalField = totalField;
        this.categoryDropdown = categoryDropdown;
        this.itemDropdown = itemDropdown;
        this.assignedSector = assignedSector;
        
        this.inventory = new ArrayList<>();
        this.billItems = new ArrayList<>();

        loadInventory(); //Load inventory for the assigned sector
        populateCategories(); //Populate category drop down
        setupCategorySelection(); //Initialize category selection functionality
    }

    //Load inventory for the assigned sector
    private void loadInventory() {
        ArrayList<Item> allItems = fileHandler.loadInventory(); //Load all items from the inventory file

        //Filter items by assigned sector
        for (int i = 0; i < allItems.size(); i++) {
            Item item = allItems.get(i);
            if (item.getItemSector().equalsIgnoreCase(assignedSector)) {
                inventory.add(item);
            }
        }
    }

    //Populate the category dropdown with unique categories from the sector's inventory
    private void populateCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            String category = inventory.get(i).getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        categoryDropdown.getItems().addAll(categories); // Add categories to the drop down
    }

    // Set up the category selection to filter items in the item drop down
    private void setupCategorySelection() {
        categoryDropdown.setOnAction(event -> {
            String selectedCategory = categoryDropdown.getValue();
            
            // Null check for selectedCategory
            if (selectedCategory == null) {
                showError("Please select a category.");
                return;
            }
            
            itemDropdown.getItems().clear(); // Clear current items
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                if (item.getCategory().equalsIgnoreCase(selectedCategory)) {
                    itemDropdown.getItems().add(item.getItemName()); // Add items matching the selected category
                }
            }
        });
    }

    // Add an item to the bill
    public void addItemToBill(String itemName, int quantity) {
        if(itemName == null || itemName.isEmpty()) {
        	showError("Please select an item");
        	return;
        }
        
        if(quantity <= 0) {
        	showError("Quantity must be greater than zero.");
        	return;
        }
        
        Item selectedItem = null;

        // Find the selected item in the inventory
        for (int i = 0; i < inventory.size(); i++) {
        	Item item = inventory.get(i);
            if (item.getItemName().equalsIgnoreCase(itemName)) {
            	selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
        	showError("Item not found in inventory.");
            return;
        }

        if (!selectedItem.hasSufficientStock(quantity)) {
        	showError("Insufficient stock for item: " + selectedItem.getItemName());
            return;
        }

        //Deduct stock and calculate price
        selectedItem.sellItem(quantity);
        double totalPrice = selectedItem.getSellingPrice() * quantity;
            
        //Add the item to the current bill
        Item billItem = new Item(
        		selectedItem.getItemName(),
                selectedItem.getCategory(),
                selectedItem.getSellingPrice(),
                quantity,
                0,
                quantity );
            
        billItems.add(billItem); //Maintain a reference to the added item
            		

        //Update UI
        String itemInfo = String.format(
        		"Item Name: %s | Category: %s | Quantity: %d | Price: %.2f",
                selectedItem.getItemName(),
                selectedItem.getCategory(),
                quantity,
                totalPrice );
            
        itemsContainer.getChildren().add(new javafx.scene.control.Label(itemInfo));

        //Update total field
        double currentTotal = Double.parseDouble(totalField.getText());
        totalField.setText(String.format("%.2f", currentTotal + totalPrice));

    }

    // Finalize the bill and save it
    public void finalizeBill(String cashierName, String sector) {
        try {
            if(billItems.isEmpty()) {
            	showError("No items added to the bill.");
            	return;
            }
            
            double totalAmount = Double.parseDouble(totalField.getText());
            
            //Generate a unique bill number and create a new bill
            String billNumber = generateBillNumber();
            Bill newBill = new Bill(billNumber, billItems, totalAmount, new Date());
            
            //Save the bill
            fileHandler.saveBill(billNumber, billItems, totalAmount, cashierName, sector);

         	//Reset UI and bill items
            resetFields();

            showSuccess("Bill created successfully!\nBill Number: " + billNumber);

        } catch (Exception e) {
            showError("Failed to finalize bill: " + e.getMessage());
        }
    }
    
    private void resetFields() {
    	itemsContainer.getChildren().clear();
        totalField.setText("0.00");
        categoryDropdown.getSelectionModel().clearSelection();
        itemDropdown.getItems().clear();
        billItems.clear(); // Clear the list after finalization
		
	}

	// Generate a unique bill number
    private String generateBillNumber() {
        return "BILL-" + System.currentTimeMillis();
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(String message) {
    	showAlert("Error", message, Alert.AlertType.ERROR);
    }
    
    private void showSuccess(String message) {
        showAlert("Success", message, Alert.AlertType.INFORMATION);
    }
}
