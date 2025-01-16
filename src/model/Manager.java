package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import util.Role;

public class Manager extends User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7638438919947446592L;
	private ArrayList<Sector> sectors;  // Sectors used instead of categories
    private ArrayList<Supplier> suppliers;
    private ArrayList<Item> items;
    private SalesMetrics salesMetrics; 
    private ArrayList<Cashier> cashiers;




    public Manager(String name, double salary, Role role, String username, String password, LocalDate dateOfBirth,
                   String phonenumber, String email) {
        super(name, salary, role, username, password, dateOfBirth, phonenumber, email);
        this.sectors = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.items = new ArrayList<>();
        this.salesMetrics = new SalesMetrics();
        this.cashiers = new ArrayList<>();
    }


 // Method to add a new sector
    public boolean addSector(Sector newSector) {
        // Check if the sector already exists by name (or other criteria)
        for (Sector sector : sectors) {
            if (sector.getName().equalsIgnoreCase(newSector.getName())) {
                showError("Sector already exists: " + newSector.getName());
                return false; // Return false if the sector already exists
            }
        }

        // Add the new sector to the list of sectors
        sectors.add(newSector);
        showSuccess("Sector added successfully: " + newSector.getName());
        return true; // Return true if the sector was added successfully
    }
    public Supplier getSupplierByName(String supplierName) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                return supplier;
            }
        }
        return null;  // Return null if no matching supplier found
    }


    public ArrayList<Sector> viewSector() {
        // Assuming 'sectors' is already an ArrayList<Sector>
        if (sectors != null && !sectors.isEmpty()) {
            return sectors;  // Return the list of Sector objects directly
        } else {
            System.out.println("No sectors available.");
            return new ArrayList<>();  // Return an empty list if no sectors exist
        }
    }




    // Add a new item
    public void addNewItem(Item item) {
        items.add(item); // Assuming items is an ArrayList or similar collection
        System.out.println("Item added: " + item.getItemName() + " with stock: " + item.getItemQuantity());
    }

    // Restock an item
    public void restockItem(Item item, int quantity) {
        if (item != null && quantity > 0) {
            item.restockItem(quantity);
            System.out.println("Item restocked: " + item.getItemName());
        }
    }
    

    // Generate sales report
    public String generateSalesReport(String timePeriod) {
        salesMetrics.setTimePeriod(timePeriod);
        salesMetrics.calculateMetrics(items);
        return "Sales Report for " + timePeriod + ": \n" +
               "Total Revenue: $" + salesMetrics.getTotalRevenue() + "\n" +
               "Total Items Sold: " + salesMetrics.getTotalItemsSold();
    }

    // Notify for restocks
    public void notifyRestockNeeded() {
        for (Item item : items) {
            if (item.getStockQuantity() < 5) {
                System.out.println("Restock needed for: " + item.getItemName());
            }
        }
    }

    // Sort items by price (ascending)
    public void sortItemsByPriceLowToHigh() {
        items.sort(Comparator.comparingDouble(Item::getPrice));
    }

    // Sort items by price (descending)
    public void sortItemsByPriceHighToLow() {
        items.sort(Comparator.comparingDouble(Item::getPrice).reversed());
    }

    // Utility methods to show error and success messages
    private void showError(String message) {
        System.err.println("Error: " + message);
    }

    private void showSuccess(String message) {
        System.out.println("Success: " + message);
    }
    public void addCategoryToSector(String sectorName, String categoryName) {
        for (Sector sector : sectors) {
            if (sector.getName().equals(sectorName)) {
                sector.addCategory(categoryName); // Assuming Sector has an addCategory method
                return;
            }
        }
        throw new IllegalArgumentException("Sector not found: " + sectorName);
    }
    public List<String> getSupplierNames() {
        List<String> supplierNames = new ArrayList<>();
        for (Supplier supplier : suppliers) {  // Assuming you have a list of Supplier objects
            supplierNames.add(supplier.getSupplierName());
        }
        return supplierNames;
    }
    // Getter and Setter methods
    public ArrayList<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(ArrayList<Sector> sectors) {
        this.sectors = sectors;
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }
    
    public void setSuppliers(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public SalesMetrics getSalesMetrics() {
        return salesMetrics;
    }

    public void setSalesMetrics(SalesMetrics salesMetrics) {
        this.salesMetrics = salesMetrics;
    }

    public ArrayList<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(ArrayList<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

}
