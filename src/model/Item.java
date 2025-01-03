package model;

public class Item {
    private String itemName;
    private String itemCategory;
    private double price;
    private int stockQuantity;
    private int itemsSold;
    private int itemQuantity;

    //Constructor called by Manager
    public Item(String itemName, String itemCategory, double price, int stockQuantity) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemsSold = 0;
        this.itemQuantity = 0;
    }
    
    //Constructor called by Cashier
    public Item(String itemName, String itemCategory, double price, int stockQuantity, int itemsSold, int itemQuantity) {
    	this.itemName = itemName;
    	this.itemCategory = itemCategory;
    	this.price = price;
    	this.stockQuantity = stockQuantity;
    	this.itemsSold = itemsSold;
    	this.itemQuantity = itemQuantity;
    }
    
    //Get and set quantity for bill only
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
        stockQuantity -= quantity;
        itemsSold += quantity;
    }

    public void restockItem(int quantity) {
        stockQuantity += quantity;
    }
    
    public void updateStock(int quantity) {
		if(stockQuantity + quantity < 0) {
			throw new IllegalArgumentException("Insufficient stock to reduce by: " + quantity);
		}
		stockQuantity += quantity;
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

    public String getItemCategory() {
        return itemCategory;
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
    @Override
public String toString() {
    return "Item{" +
            "Name='" + itemName + '\'' +
            ", Category='" + itemCategory + '\'' +
            ", Price=" + price +
            ", Stock=" + stockQuantity +
            ", Items Sold=" + itemsSold +
            '}';
}

}

