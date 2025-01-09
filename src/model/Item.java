package model;

import java.io.Serializable;

import javafx.scene.image.Image;

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
    private Image image;
    private String category;
    private String description;

    // Constructor for Manager
    public Item(String itemName, String itemCategory, double price, int stockQuantity) {
        this.itemName = itemName;
        this.itemSector = itemCategory;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemsSold = 0;
        this.itemQuantity = stockQuantity;
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

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
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

    @Override
    public String toString() {
        return "Item->" +
                "Name:'" + itemName + '\'' +
                ", Category:'" + itemSector + '\'' +
                ", Price:" + price +
                ", Stock:" + stockQuantity +
                ", Items Sold:" + itemsSold;
    }
}
