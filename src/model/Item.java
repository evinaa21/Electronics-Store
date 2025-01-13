package model;

import java.io.Serializable;

import java.util.Objects;



public class Item implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3874406897246168956L;
	private String itemName;
    private String itemSector;
    private double price;
    private int stockQuantity;
    private int itemsSold;
    private int itemQuantity;
    private String imagePath;
    private String category;
    private String description;
    private String supplierName;

    // Constructor for Manager
    public Item(String itemName, String itemSector, double price, int stockQuantity, String category, String description, String supplierName, String imagePath) {
        this.itemName = itemName;
        this.itemSector = itemSector;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemsSold = 0;
        this.itemQuantity = stockQuantity;
        this.category = category;
        this.description = description;
        this.supplierName = supplierName;
        this.imagePath = imagePath;
    }

    // Constructor for Cashier
    public Item(String itemName, String itemCategory, double price, int stockQuantity, int itemsSold, int itemQuantity) {
        this.itemName = itemName;
        this.itemSector = itemCategory;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemsSold = itemsSold;
        this.itemQuantity = itemQuantity;
    }

    // Getters and Setters
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void sellItem(int quantity) {
        if (quantity > stockQuantity) {
            System.out.println("Not enough stock available for: " + itemName);
            return;
        }
        stockQuantity -= quantity;  // Decrease the stock by the sold quantity
        itemsSold += quantity;      // Increase the sold count
    }


    public void restockItem(int quantity) {

this.itemQuantity += quantity;

        this.stockQuantity += quantity;  // Update the stock quantity
        this.itemQuantity += quantity;  // Update the total item quantity

    }
  

    public boolean hasSufficientStock(int requestedQuantity) {
        return stockQuantity >= requestedQuantity;
    }

    public double getSellingPrice() {
        return this.price;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemSector() {
        return itemSector;
    }

    public void setItemSector(String itemSector) {
        this.itemSector = itemSector;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getItemsSold() {
        return itemsSold;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getCategory() {
        return category;
    }

    // Setter for category
    public void setCategory(String category) {
        this.category = category;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getSupplierName() {
        return supplierName;
    }

    @Override
    public String toString() {
        return "Item->" +
                "Name:'" + itemName + '\'' +
                ", Category:'" + itemSector + '\'' +
                ", Price:" + price +
                ", Stock:" + stockQuantity +
                ", Items Sold:" + itemsSold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.price, price) == 0 &&
                stockQuantity == item.stockQuantity &&
                itemsSold == item.itemsSold &&
                itemQuantity == item.itemQuantity &&
                Objects.equals(itemName, item.itemName) &&
                Objects.equals(itemSector, item.itemSector) &&
                Objects.equals(category, item.category) &&
                Objects.equals(description, item.description) &&
                Objects.equals(supplierName, item.supplierName) &&
                Objects.equals(imagePath, item.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, itemSector, price, stockQuantity, itemsSold, itemQuantity, imagePath, category, description, supplierName);
    }
}
