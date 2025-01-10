package util;

import model.Admin;
import model.Cashier;
import model.Item;
import model.Manager;
import model.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileHandler {
	
	//Constants for file paths
	private static final String EMPLOYEE_FILE = "employees.dat"; //Binary files for employees
	private static final String INVENTORY_FILE = "items.dat"; //Binary files for inventory
	private static final String BILL_DIRECTORY = "bill/"; //Text files for bills
	
	//Date format for parsing and saving dates
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
	
	public FileHandler() {
		
	}
	
	//Update inventory after a sale (used by the cashier)
	//This method validates stock availability and saves the updated inventory
	public void updateInventoryForSale(ArrayList<Item> items) throws IllegalArgumentException, IOException {
	    // Load current inventory
	    ArrayList<Item> inventory = loadInventory();

	    // Validate and update inventory
	    for (int i = 0; i < items.size(); i++) {
	    	Item soldItem = items.get(i);
	        boolean itemFound = false;

	        for (int j = 0; j < inventory.size(); j++) {
	        	Item inventoryItem = inventory.get(j);
	            
	        	if (inventoryItem.getItemName().equalsIgnoreCase(soldItem.getItemName())) {
	                itemFound = true;

	                // Check stock availability
	                if (!inventoryItem.hasSufficientStock(soldItem.getItemQuantity())) {
	                    throw new IllegalArgumentException("Insufficient stock for item: " + soldItem.getItemName());
	                }

	                // Deduct sold quantity from stock
	                inventoryItem.sellItem(soldItem.getItemQuantity());
	                break;
	            }
	        }

	        if (!itemFound) {
	            throw new IllegalArgumentException("Item not found in inventory: " + soldItem.getItemName());
	        }
	    }

	    // Save updated inventory back to items.dat
	    saveIventory(inventory);
	}
	
	//Save inventory data to the item.dat file
	public void saveIventory(ArrayList<Item> inventory) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
			oos.writeObject(inventory);
			System.out.println("Inventory saved successfully to binary file: " + INVENTORY_FILE);
		} catch (IOException e) {
			System.err.println("Error saving inventory to binary file: " + e.getMessage());
		}
	}
	
	//Load inventory data from the item.dat file
	//This method does not modify or update the inventory file
	public ArrayList<Item> loadInventory() {
		ArrayList<Item> inventory = new ArrayList<>();
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INVENTORY_FILE))) {
			inventory = (ArrayList<Item>)ois.readObject();
			System.out.println("Inventory loaded successfully from binary file: " + INVENTORY_FILE);
		} catch (FileNotFoundException e) {
			System.err.println("Inventory binary file not found: " + INVENTORY_FILE);
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error loading inventory from binary file: " + e.getMessage());
		}
		return inventory;
	}
	
	//Save employee data to the employees.dat file
	public void saveEmployeeData(ArrayList<User> employees){
		
		//Validation ???
		if(employees.isEmpty()) {
			System.err.println("No employee data to save.");
			return;
		}
			
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))) {
				oos.writeObject(employees);
				System.out.println("Employee data saved successfully to binary file: " + EMPLOYEE_FILE);
		} catch (IOException e) {
			System.out.println("Error saving employee data to binary file: " + e.getMessage());
		}
	}
	
	//Load employee data from a binary file
	public ArrayList<User> loadEmployeeData() {
		ArrayList<User> employees = new ArrayList<>();
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE))) {
			employees = (ArrayList<User>) ois.readObject();
			System.out.println("Employee data loaded successfully from binary file: " + EMPLOYEE_FILE);
		} catch (FileNotFoundException e) {
			System.err.println("Employee binary file not found: " + EMPLOYEE_FILE);
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error loading employee data from binary file: " + e.getMessage());
		}
		return employees;	
	}
	
	//Add employee salary or update if employee exists
	public void addEmployeeSalary(String employeeName, double salary) {
		ArrayList<User> employees = loadEmployeeData(); // Load current employees
		boolean found = false;
		
		    for (int i = 0; i < employees.size(); i++) {
		    	User user = employees.get(i);
		        if (user.getName().equalsIgnoreCase(employeeName)) {
		            user.setSalary(salary); // Update salary if the employee exists
		            found = true;
		            break;
		        }
		    }

		    if (!found) {
		        System.err.println("Employee not found: " + employeeName);
		    } else {
		        saveEmployeeData(employees); // Save updated employees back to the file
		        System.out.println("Salary updated for employee: " + employeeName);
		    }
	}
	
	//Save a bill to a text file
	public void saveBill(String billNumber, ArrayList<Item> items, double total) {
		String date = dateFormat.format(new Date());
		String fileName = BILL_DIRECTORY + billNumber + "_" + date + ".txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			// Header section
		    writer.write("=========================================\n");
		    writer.write("                ELECTRONIC STORE          \n");
		    writer.write("=========================================\n");
		    writer.write("Bill Number: " + billNumber + "\n");
		    writer.write("Date: " + date + "\n");
		    writer.write("-----------------------------------------\n");

		    // Items section
		    writer.write("Items:\n");
		    writer.write(String.format("%-20s %-10s %-10s\n", "Item Name", "Quantity", "Price"));
		    writer.write("-----------------------------------------\n");
		    for (int i = 0; i < items.size(); i++) {
		    	Item item = items.get(i);
		        writer.write(String.format("%-20s %-10d %-10.2f\n",
		        		item.getItemName(), item.getItemQuantity(), item.getSellingPrice()));
		    }
		    writer.write("-----------------------------------------\n");

		    // Total section
		    writer.write(String.format("Total Amount: %.2f\n", total));
		    writer.write("=========================================\n");
		    writer.write("          THANK YOU FOR SHOPPING         \n");
		    writer.write("=========================================\n");
		        
		    System.out.println("Bill saved successfully to " + fileName);

		} catch (IOException e) { 
			System.err.println("Error saving bill to file: " + fileName + ". Cause: " + e.getMessage());
		}
	}
	
	
	//Notify manager of low stock items
	public ArrayList<Item> notifyLowStock(ArrayList<Item> inventory, int threshold) {
		ArrayList<Item> lowStockItems = new ArrayList<>();
	    for (int i = 0; i < inventory.size(); i++) {
	    	Item item = inventory.get(i);
	        if (item.getStockQuantity() < threshold) { 
	        	lowStockItems.add(item);
	        }
	    }
	    
	    return lowStockItems;
	}
	
	
} 

