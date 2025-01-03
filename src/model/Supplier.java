package model;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String supplierName;
    private String contactInfo;
    private List<Item> suppliedItems;

    public Supplier(String supplierName, String contactInfo) {
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.suppliedItems = new ArrayList<>();
    }

    public void addItem(Item item) {
        suppliedItems.add(item);
    }

    public void removeItem(Item item) {
        suppliedItems.remove(item);
    }

    public List<Item> getSuppliedItems() {
        return suppliedItems;
    }
}

