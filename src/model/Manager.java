package model;

import java.util.ArrayList;
import java.util.Date;
import util.Role;

public class Manager extends User {
    private ArrayList<Sector> sectors;
    private ArrayList<Supplier> suppliers;
    private ArrayList<Item> items;
    private SalesMetrics salesMetrics;

    public Manager(int id, String name, double salary, Role role, String username, String password, Date dateOfBirth,
                   long phonenumber, String email) {
        super(name, salary, role, username, password, dateOfBirth, phonenumber, email);
        this.sectors = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.items = new ArrayList<>();
        this.salesMetrics = new SalesMetrics();
    }

    // Default constructor
    public Manager() {
        super("Default", 0.0, Role.Manager, "default", "default", new Date(), 0L, "default@domain.com");
        this.sectors = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.items = new ArrayList<>();
        this.salesMetrics = new SalesMetrics();
    }

    public void addNewItem(Item item) {
        if (item != null) {
            items.add(item);
            System.out.println("Item added: " + item.getItemName());
        }
    }

    public void restockItem(Item item, int quantity) {
        if (item != null && quantity > 0) {
            item.restockItem(quantity);
            System.out.println("Item restocked: " + item.getItemName());
        }
    }

    public String generateSalesReport(String timePeriod) {
        salesMetrics.setTimePeriod(timePeriod);
        salesMetrics.calculateMetrics(items);
        return "Sales Report for " + timePeriod + ": \n" +
               "Total Revenue: $" + salesMetrics.getTotalRevenue() + "\n" +
               "Total Items Sold: " + salesMetrics.getTotalItemsSold();
    }

    public void notifyRestockNeeded() {
        for (Item item : items) {
            if (item.getStockQuantity() < 5) {
                System.out.println("Restock needed for: " + item.getItemName());
            }
        }
    }

    public ArrayList<String> viewItemCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Item item : items) {
            if (!categories.contains(item.getItemCategory())) {
                categories.add(item.getItemCategory());
            }
        }
        return categories;
    }

    // Getters and Setters
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
}
