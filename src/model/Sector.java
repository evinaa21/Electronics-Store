package model;

import java.util.ArrayList;
import java.util.List;

public class Sector {
    private String sectorName;
    private Manager manager;
    private List<Cashier> cashiers;
    private List<Item> items;

    public Sector(String sectorName) {
        this.sectorName = sectorName;
        this.cashiers = new ArrayList<>();
        this.items = new ArrayList<>();
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }
    public void assignManager(Manager manager) {
        this.manager = manager;
    }
    public List<Item> viewItems() {
        return items;
    }
    public List<Cashier> getCashiers() {
        return cashiers;
    }
    @Override
    public String toString() {
        return "Sector{" +
                "Name='" + sectorName + '\'' +
                ", Manager=" + (manager != null ? manager.getName() : "None") +  
                ", Number of Cashiers=" + cashiers.size() +
                ", Number of Items=" + items.size() +
                '}';
    }
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public boolean removeCashier(Cashier cashier) {
        return cashiers.remove(cashier);
    }

    
}