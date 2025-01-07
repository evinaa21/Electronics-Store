package model;

import java.util.ArrayList;
import java.util.Date;
import util.Role;

public class Manager extends User {
    private ArrayList<Sector> sectors;  // Sectors used instead of categories
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

    // Add a new sector to the list of sectors
    public void addSector(String sectorName) {
        if (sectorName != null && !sectorName.trim().isEmpty()) {
            // Ensure the sector is unique before adding
            boolean exists = false;
            for (Sector sector : sectors) {
                if (sector.getName().equalsIgnoreCase(sectorName)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                sectors.add(new Sector(sectorName)); // Assuming Sector class accepts a sector name
                System.out.println("Sector added: " + sectorName);
            } else {
                System.out.println("Sector already exists: " + sectorName);
            }
        }
    }
    // View the sectors in the manager's list
    public ArrayList<String> viewSector() {
        ArrayList<String> sectorNames = new ArrayList<>();
        for (Sector sector : sectors) {
            sectorNames.add(sector.getName());  // Assuming Sector class has a getName() method
        }
        return sectorNames;
    }

    // Add a new item
    public void addNewItem(Item item) {
        if (item != null) {
            items.add(item);
            System.out.println("Item added: " + item.getItemName());
        }
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
}

