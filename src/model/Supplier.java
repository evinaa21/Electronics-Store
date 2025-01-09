package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Supplier implements Serializable {
    private static final long serialVersionUID = -4499787452810759813L;

    private String supplierName;
    private String contactInfo;
    private List<Item> suppliedItems;

    // Constructor
    public Supplier(String supplierName, String contactInfo) {
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.suppliedItems = new ArrayList<>();
    }

    // Add an item to the supplier's list of supplied items
    public void addItem(Item item) {
        if (item != null) {
            suppliedItems.add(item);
        }
    }

    // Remove an item from the supplier's list of supplied items
    public void removeItem(Item item) {
        suppliedItems.remove(item);
    }

    // Getter for supplied items
    public List<Item> getSuppliedItems() {
        return new ArrayList<>(suppliedItems); // Return a copy to ensure encapsulation
    }

    // Getter and Setter for supplier name
    public String getSupplierName() {
        return supplierName;
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

    @Override
    public String toString() {
        return "Supplier: " + supplierName + ", Contact Info: " + contactInfo;
    }
}

