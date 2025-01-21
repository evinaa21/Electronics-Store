package util;
 
import model.Bill;
import model.Cashier;
import model.Item;
import model.Sector;
import model.Supplier;
import model.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {
	// Constants for file paths CHANGE IF THESE DONT WORK FOR YOU
	private static final String EMPLOYEE_FILE = "src/BinaryFiles/employees.dat"; // Binary files for employees
	private static final String INVENTORY_FILE = "src/BinaryFiles/items.dat"; // Binary files for inventory
	private static final String BILL_DIRECTORY = "src/BinaryFiles/Bills/"; // Text files for bills
	private static final String SECTOR_FILE = "src/BinaryFiles/sectors.dat"; // Path to sector file
	private static final String SUPPLIER_FILE = "src/BinaryFiles/suppliers.dat"; // Binary files for suppliers

	// Date format for parsing and saving dates
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");

	public FileHandler() {

	}
	
	public ArrayList<Sector> loadSectors() {
		ArrayList<Sector> sectors = new ArrayList<>();

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SECTOR_FILE))) {
			Object obj = ois.readObject();

			// Check if the object is an instance of ArrayList
			if (obj instanceof ArrayList<?>) {
				ArrayList<?> tempList = (ArrayList<?>) obj;

				// Check if the list contains Sector objects
				if (!tempList.isEmpty() && tempList.get(0) instanceof Sector) {
					sectors = (ArrayList<Sector>) tempList;
					System.out.println("Sectors loaded successfully.");
				} else {
					System.err.println("Error: File data is not of type ArrayList<Sector>");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading sectors: File not found or unable to read.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error loading sectors: Class not found.");
		} catch (ClassCastException e) {
			e.printStackTrace();
			System.out.println("Error loading sectors: Incorrect data format in file.");
		}

		return sectors;
	}

	
	
	public static ArrayList<Item> readLowStockItemsFromBinaryFile(String fileName, int threshold) {
		ArrayList<Item> lowStockItems = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			ArrayList<Item> inventory = (ArrayList<Item>) ois.readObject();
			for (Item item : inventory) {
				if (item.getStockQuantity() <= threshold) {
					lowStockItems.add(item);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lowStockItems;
	}

	// Update the notifyLowStock method
		public ArrayList<Item> notifyLowStock(int threshold) {
			// Use INVENTORY_FILE constant instead of hardcoded filename
			return readLowStockItemsFromBinaryFile(INVENTORY_FILE, threshold);
		}
		
	

	public ArrayList<Bill> loadBills() {
	    ArrayList<Bill> bills = new ArrayList<>();
	    File billDirectory = new File(BILL_DIRECTORY);

	    if (!billDirectory.exists() || !billDirectory.isDirectory()) {
	        System.out.println("The specified directory does not exist or is not a valid directory: " + BILL_DIRECTORY);
	        return bills;
	    }

	    File[] billFiles = billDirectory.listFiles((dir, name) -> name.endsWith(".txt"));

	    if (billFiles == null || billFiles.length == 0) {
	        System.out.println("No bill files found in directory: " + BILL_DIRECTORY);
	        return bills;
	    }

	    Date today = Calendar.getInstance().getTime();

	    for (File billFile : billFiles) {
	        Bill bill = loadBillFromFile(billFile);
	        if (bill != null) {
	        	// Filter for today's bills
	            if (isSameDay(bill.getSaleDate(), today)) {
	                bills.add(bill);
	            } 
	        }
	    }

	    return bills;
	}

	public Bill loadBillFromFile(File billFile) {
	    Bill bill = null;
	    try (BufferedReader br = new BufferedReader(new FileReader(billFile))) {
	        String line;
	        String billNumber = null;
	        ArrayList<Item> items = new ArrayList<>();
	        double totalAmount = 0.0;
	        Date saleDate = null;
	        boolean isItemSection = false;

	        while ((line = br.readLine()) != null) {
	            line = line.trim();

	            if (line.startsWith("Bill Number:")) {
	                billNumber = line.split(":")[1].trim();
	            } else if (line.startsWith("Date:")) {
	                String dateString = line.split(":")[1].trim();
	                saleDate = dateFormat.parse(dateString); // Ensure dateFormat matches file's date format
	            } else if (line.startsWith("Total Amount:")) {
	                totalAmount = Double.parseDouble(line.split(":")[1].trim());
	            } else if (line.startsWith("Items:")) {
	                isItemSection = true;
	            } else if (isItemSection && line.startsWith("-----------------------------------------")) {
	                // Skip the header divider line
	                continue;
	            } else if (isItemSection && !line.isEmpty()) {
	                // Skip the header row or invalid lines
	                if (line.startsWith("Item Name")) {
	                    continue; // Skip the header row
	                }

	                // Parse item details (assumes columns are aligned and separated by spaces)
	                String[] itemDetails = line.split("\\s{2,}"); // Splits on 2 or more spaces
	                if (itemDetails.length >= 4) {
	                    try {
	                        String itemName = itemDetails[0].trim();
	                        String category = itemDetails[1].trim();
	                        int quantity = Integer.parseInt(itemDetails[2].trim());
	                        double price = Double.parseDouble(itemDetails[3].trim());

	                        // Create and add the item to the list
	                        items.add(new Item(itemName, category, price, 0, 0, quantity));
	                    } catch (NumberFormatException e) {
	                        System.err.println("Skipping invalid item line: " + line);
	                    }
	                }
	            }
	        }

	        if (billNumber != null && !items.isEmpty() && totalAmount > 0 && saleDate != null) {
	            bill = new Bill(billNumber, items, totalAmount, saleDate);
	        }
	    } catch (IOException | NumberFormatException | java.text.ParseException e) {
	        e.printStackTrace();
	    }
	    return bill;
	}

	


	public ArrayList<String> readBills() {
		ArrayList<String> billsData = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(BILL_DIRECTORY))) {
			String line;
			while ((line = reader.readLine()) != null) {
				billsData.add(line); // Add each bill entry to the list
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return billsData;
	}

	// Ensure directories and files exist
	private void ensureDirectoriesExist() {
		try {
			File employeeFile = new File(EMPLOYEE_FILE);
			File inventoryFile = new File(INVENTORY_FILE);
			File billDir = new File(BILL_DIRECTORY);

			if (!employeeFile.getParentFile().exists()) {
				employeeFile.getParentFile().mkdirs();
			}
			if (!inventoryFile.getParentFile().exists()) {
				inventoryFile.getParentFile().mkdirs();
			}
			if (!billDir.exists()) {
				billDir.mkdirs();
			}
		} catch (Exception e) {
			System.err.println("Error ensuring directories exist: " + e.getMessage());
		}
	}
	// Load inventory data from the item.dat file
		// This method does not modify or update the inventory file
		public ArrayList<Item> loadInventory() {
			ArrayList<Item> inventory = new ArrayList<>();
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INVENTORY_FILE))) {
				inventory = (ArrayList<Item>) ois.readObject();
				System.out.println("Inventory loaded successfully from binary file: " + INVENTORY_FILE);
			} catch (FileNotFoundException e) {
				System.err.println("Inventory binary file not found: " + INVENTORY_FILE);
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Error loading inventory from binary file: " + e.getMessage());
			}
			return inventory;
		}


	// Update inventory after a sale (used by the cashier)
	// This method validates stock availability and saves the updated inventory
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
		saveInventory(inventory);
	}
	// Save the entire inventory to the binary file
		public void saveInventory(ArrayList<Item> inventory) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
				oos.writeObject(inventory); // Write the entire list of items to the file
				System.out.println("Inventory saved successfully to binary file: " + INVENTORY_FILE);
			} catch (IOException e) {
				System.err.println("Error saving inventory to binary file: " + e.getMessage());
			}
		}




	// Save employee data to the employees.dat file
	public void saveEmployeeData(ArrayList<User> employees) {

		// Validation ???
		if (employees.isEmpty()) {
			System.err.println("No employee data to save.");
			return;
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))) {
			oos.writeObject(employees);
			System.out.println("Employee data saved successfully to binary file: " + EMPLOYEE_FILE);
		} catch (IOException e) {
			System.out.println("Error saving employee data to binary file: " + e.getMessage());
		}
	}

	public void saveEmployee(User employee) {

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))) {
			oos.writeObject(employee);
			System.out.println("Employee data saved successfully to binary file: " + EMPLOYEE_FILE);
		} catch (IOException e) {
			System.out.println("Error saving employee data to binary file: " + e.getMessage());
		}
	}

	// Load employee data from a binary file
	public ArrayList<User> loadEmployeeData() {
		ArrayList<User> employees = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE))) {
			while (true) {
				try {
					employees = (ArrayList<User>) ois.readObject();
				} catch (EOFException e) {
					break;
				}
			}
			System.out.println("Employee data loaded successfully from binary file: " + EMPLOYEE_FILE);
		} catch (FileNotFoundException e) {
			System.err.println("Employee binary file not found: " + EMPLOYEE_FILE);
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error loading employee data from binary file: " + e.getMessage());
		}
		return employees;
	}

	public User loadEmployee(String Name) {
		ArrayList<User> user = new ArrayList<>();
		user = loadEmployeeData();
		for (User employee : user) {
			if (employee.getName().equals(Name)) {
				return employee;
			}
		}
		System.out.println("No user with the name of " + Name + " was found!");
		return null;
	}

	public void updateEmployeeData(User updatedUser) {
		ArrayList<User> employees = loadEmployeeData();

		employees.removeIf(user -> user.getUsername().equals(updatedUser.getUsername()));

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))) {
			oos.writeObject(updatedUser);
		} catch (IOException e) {
			System.err.println("Error updating employee data: " + e.getMessage());
		}
	}

	// Add employee salary or update if employee exists
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

	// Save a bill to a text file
	public void saveBill(String billNumber, ArrayList<Item> items, double total, String cashierName, String sector) {
		String date = dateFormat.format(new Date());
		String fileName = BILL_DIRECTORY + billNumber + "_" + date + ".txt";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("=========================================\n");
			writer.write("                ELECTRONIC STORE          \n");
			writer.write("=========================================\n");
			writer.write("Bill Number: " + billNumber + "\n");
			writer.write("Cashier: " + cashierName + "\n");
			writer.write("Sector: " + sector + "\n");
			writer.write("Date: " + date + "\n");
			writer.write("-----------------------------------------\n");
			writer.write("Items:\n");
			writer.write(String.format("%-20s %-10s %-10s %-10s\n", "Item Name", "Category", "Quantity", "Price"));
			writer.write("-----------------------------------------\n");

			// Loop through items and print details including category
			for (Item item : items) {
				writer.write(String.format("%-20s %-10s %-10d %-10.2f\n", item.getItemName(),
						item.getCategory() != null ? item.getCategory() : "Uncategorized", // Check for null category
						item.getItemQuantity(), item.getSellingPrice()));
			}

			writer.write("-----------------------------------------\n");
			writer.write(String.format("Total Amount: %.2f\n", total));
			writer.write("=========================================\n");
			writer.write("          THANK YOU FOR SHOPPING         \n");
			writer.write("=========================================\n");

			System.out.println("Bill saved successfully to " + fileName);
		} catch (IOException e) {
			System.err.println("Error saving bill to file: " + fileName + ". Cause: " + e.getMessage());
		}
	}

	private boolean isSameDay(Date date1, Date date2) {
		return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate();
	}

	// Load all bills for a specific cashier
	public ArrayList<Bill> loadBills(String cashierName, Date date) {
		ArrayList<Bill> bills = new ArrayList<>();
		File billDirectory = new File(BILL_DIRECTORY);

		if (billDirectory.exists() && billDirectory.isDirectory()) {
			File[] billFiles = billDirectory.listFiles((dir, name) -> name.contains(cashierName));

			if (billFiles != null) {
				for (File billFile : billFiles) {
					Bill bill = loadBillFromFile(billFile, date);
					if (bill != null) {
						bills.add(bill);
					}
				}
			}
		} else {
			System.err.println("Bill directory not found or is not a directory.");
		}
		return bills;
	}

	// Load a bill from a text file and convert it into a Bill object
	private Bill loadBillFromFile(File billFile, Date date) {
		Bill bill = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(billFile))) {
			String line;
			String billNumber = null;
			Date saleDate = null;
			ArrayList<Item> items = new ArrayList<>();
			double totalAmount = 0;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Bill Number:")) {
					billNumber = line.split(":")[1].trim();
				} else if (line.startsWith("Date:")) {
					String dateStr = line.split(":")[1].trim();
					saleDate = dateFormat.parse(dateStr);
				} else if (line.startsWith("Total Amount:")) {
					totalAmount = Double.parseDouble(line.split(":")[1].trim());
				} else if (!line.startsWith("=") && !line.startsWith("-") && !line.isBlank()) {
					String[] itemDetails = line.split("\\s+");
					String itemName = itemDetails[0];
					String category = itemDetails[1];
					int quantity = Integer.parseInt(itemDetails[1]);
					double price = Double.parseDouble(itemDetails[2]);
					// KTU KA ERROR items.add(new Item(itemName, category , price, quantity));
				}
			}

			if (billNumber != null && saleDate != null) {
				if (date == null || isSameDay(saleDate, date)) {
					bill = new Bill(billNumber, items, totalAmount, saleDate);
				}
			}
		} catch (Exception e) {
			System.err.println("Error reading bill file: " + billFile.getName());
		}
		return bill;
	}
	// Load inventory data for a specific sector
		public ArrayList<Item> loadInventoryBySector(String sector) {
			ArrayList<Item> inventory = loadInventory();
			ArrayList<Item> sectorInventory = new ArrayList<>();

			for (Item item : inventory) {
				if (item.getItemSector().equalsIgnoreCase(sector)) {
					sectorInventory.add(item);
				}
			}

			return sectorInventory;
		}
		// Filter items by category
		public ArrayList<Item> filterItemsByCategory(String category) {
			ArrayList<Item> filteredItems = new ArrayList<>();
			ArrayList<Item> inventory = loadInventory(); // Load all items from inventory

			for (Item item : inventory) {
				if (item.getCategory().equalsIgnoreCase(category)) {
					filteredItems.add(item); // Add item to list if it matches the category
				}
			}

			return filteredItems;
		}


	// Notify low stock items for the specified sector where cashier is assigned
	public boolean isItemOutOfStock(String itemName, String sector) {
		ArrayList<Item> sectorItems = loadInventoryBySector(sector); // Load items for the sector

		for (Item item : sectorItems) {
			if (item.getItemName().equalsIgnoreCase(itemName)) { // Match the item name
				return item.getStockQuantity() == 0; // Return true if stock is 0
			}
		}

		// If the item is not found in the sector inventory, treat it as unavailable
		return true;
	}

}
