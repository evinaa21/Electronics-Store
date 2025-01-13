package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Supplier implements Serializable {
    private static final long serialVersionUID = -4499787452810759813L;

    private String supplierName;
    private String contactInfo;
    private ArrayList<Item> suppliedItems;
    private List<String> itemIds; // Store IDs of items supplied by this supplier

	private String supplierId;

    public Supplier( String supplierName) {
        this.supplierName = supplierName;
        this.itemIds = new ArrayList<>();
        this.suppliedItems = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (suppliedItems == null) {
            suppliedItems = new ArrayList<>();  // Initialize if not already initialized
        }
        suppliedItems.add(item);  // Add the item
    }


    // Remove an item from the supplier's list of supplied items
    public void removeItem(Item item) {
        suppliedItems.remove(item);
    }


    // Getter and Setter for supplier name
    public String getSupplierName() {
        return supplierName;
    }

    public ArrayList<Item> getSuppliedItems() {
    	if(suppliedItems == null) {
    		suppliedItems = new ArrayList<Item>();
    	}
        return suppliedItems;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    // Getter and Setter for contact information
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setSuppliedItems(ArrayList<Item> suppliedItems) {
        this.suppliedItems = suppliedItems;
    }
    public List<String> getItemIds() {
        return itemIds;
    }
    public void removeItem(String itemId) {
        itemIds.remove(itemId);
    }


    @Override
    public String toString() {
        return "Supplier: " + supplierName + ", Contact Info: " + contactInfo;
    }


}

