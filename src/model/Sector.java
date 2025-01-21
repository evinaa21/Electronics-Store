package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sector implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sectorName;
	private Manager manager;
	private ArrayList<Cashier> cashiers;
	private ArrayList<Item> items;
	private ArrayList<String> categories; // Add a list to store categories

	public Sector(String sectorName) {
		this.sectorName = sectorName;
		this.cashiers = new ArrayList<>();
		this.items = new ArrayList<>();
		this.categories = new ArrayList<>(); // Initialize the categories list
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

	public void addCategory(String category) {
		categories.add(category); // Add category to the list
	}

	public ArrayList<Item> viewItems() {
		return items;
	}

	public List<Cashier> getCashiers() {
		return cashiers;
	}

	public ArrayList<String> getCategories() {
		return categories; // Get the list of categories
	}

	@Override
	public String toString() {
		return "Sector{" + "Name='" + sectorName + '\'' + ", Manager=" + (manager != null ? manager.getName() : "None")
				+ ", Number of Cashiers=" + cashiers.size() + ", Number of Items=" + items.size() + ", Categories="
				+ categories.size() + // Display category count
				'}';
	}

	public boolean removeItem(Item item) {
		return items.remove(item);
	}

	public boolean removeCashier(Cashier cashier) {
		return cashiers.remove(cashier);
	}

	public String getName() {
		return sectorName; // Return the correct sector name
	}
}
