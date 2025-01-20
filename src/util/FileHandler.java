package util;
import model.Admin;
import model.Bill;
import model.Cashier;
import model.Item;
import model.Manager;
import model.Sector;
import model.Supplier;
import model.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {
	private ArrayList<Item> items = new ArrayList<>();
	
	//Constants for file paths CHANGE IF THESE DONT WORK FOR YOU
	private static final String EMPLOYEE_FILE = "src/BinaryFiles/employees.dat"; //Binary files for employees
	private static final String INVENTORY_FILE = "C:\\Users\\Evina\\git\\Electronics-Store\\src\\BinaryFiles\\items.dat"; //Binary files for inventory
	private static final String BILL_DIRECTORY = "C:\\Users\\Evina\\git\\Electronics-Store\\src\\BinaryFiles\\bill.txt"; //Text files for bills
	private static final String SECTOR_FILE = "src/BinaryFiles/sectors.dat"; // Path to sector file
	private static final String SUPPLIER_FILE = "C:\\Users\\Evina\\git\\Electronics-Store\\src\\BinaryFiles\\suppliers.dat"; // Binary files for suppliers

	

	
	//Date format for parsing and saving dates
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
	
	public FileHandler() {
		
	}
	


	public boolean deleteItemAndUpdateSuppliers(Item item, ArrayList<Item> inventory, ArrayList<Supplier> suppliers) {
	    boolean itemRemoved = inventory.remove(item);

	    if (itemRemoved) {
	        // Remove the item from all suppliers
	        for (Supplier supplier : suppliers) {
	            supplier.getSuppliedItems().removeIf(i -> i.getItemName().equals(item.getItemName()));
	        }

	        // Save updated data to files
	        saveInventory(inventory);
	        saveSuppliers(suppliers);
	        return true;
	    }
	    return false;
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

 // Add a new item to the inventory and save it to the file
    public void addNewItem(Item newItem) {
        ArrayList<Item> inventory = loadInventory();  // Load existing inventory
        inventory.add(newItem);  // Add the new item
        saveInventory(inventory);  // Save the updated inventory
        System.out.println("New item added to inventory: " + newItem.getItemName());
    }
 
    public boolean deleteItem(Item itemToDelete) {
        try {
            ArrayList<Item> items = loadInventory(); // Load existing items
            boolean removed = items.removeIf(item -> item.getItemName().equals(itemToDelete.getItemName())); // Remove the matching item

            if (removed) {
                saveInventory(items); // Save updated list back to the file
            }
            return removed; // Return whether the item was deleted
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        }
    }
 // Save the entire inventory to the binary file
    public void saveInventory(ArrayList<Item> inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(inventory);  // Write the entire list of items to the file
            System.out.println("Inventory saved successfully to binary file: " + INVENTORY_FILE);
        } catch (IOException e) {
            System.err.println("Error saving inventory to binary file: " + e.getMessage());
        }
    }

	public void saveSuppliers(List<Supplier> suppliers) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUPPLIER_FILE))) {
	        oos.writeObject(suppliers);
	        System.out.println("Suppliers saved successfully to binary file: " + SUPPLIER_FILE);
	    } catch (IOException e) {
	        System.err.println("Error saving suppliers: " + e.getMessage());
	    }
	}
	public ArrayList<Supplier> loadSuppliers() {
	    ArrayList<Supplier> suppliers = new ArrayList<>();
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SUPPLIER_FILE))) {
	        suppliers = (ArrayList<Supplier>) ois.readObject();
	        System.out.println("Suppliers loaded successfully from binary file: " + SUPPLIER_FILE);
	    } catch (FileNotFoundException e) {
	        System.err.println("Suppliers binary file not found: " + SUPPLIER_FILE);
	    } catch (IOException | ClassNotFoundException e) {
	        System.err.println("Error loading suppliers: " + e.getMessage());
	    }
	    return suppliers;
	}
	public void addSupplier(Supplier newSupplier) {
	    ArrayList<Supplier> suppliers = loadSuppliers();
	    suppliers.add(newSupplier);
	    saveSuppliers(suppliers);
	    System.out.println("New supplier added: " + newSupplier.getSupplierName());
	}
	

	 
    public void saveSectors(ArrayList<Sector> sectors) {
        if (sectors == null || sectors.isEmpty()) {
            System.out.println("No sectors to save.");
            return;  // If the list is empty, do not proceed with saving.
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SECTOR_FILE))) {
            out.writeObject(sectors); // Serialize the sectors list (which includes categories).
            System.out.println("Sectors saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving sectors to file.");
        }
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

	    // Check if the directory exists and is a valid directory
	    if (!billDirectory.exists() || !billDirectory.isDirectory()) {
	        System.out.println("The specified directory does not exist or is not a valid directory: " + BILL_DIRECTORY);
	        return bills;  // Return empty list if directory is invalid
	    }

	    // List all .txt files in the directory
	    File[] billFiles = billDirectory.listFiles((dir, name) -> name.endsWith(".txt"));

	    // Check if there are any files to process
	    if (billFiles == null || billFiles.length == 0) {
	        System.out.println("No bill files found in directory: " + BILL_DIRECTORY);
	        return bills;  // Return empty list if no .txt files found
	    }

	    // Process each .txt file found
	    for (File billFile : billFiles) {
	        Bill bill = loadBillFromFile(billFile);
	        if (bill != null) {
	            bills.add(bill);  // Only add valid bills
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

	        while ((line = br.readLine()) != null) {
	            // Process each line and extract bill data
	            if (line.startsWith("Bill Number:")) {
	                billNumber = line.split(":")[1].trim();  // Extract bill number
	            } else if (line.startsWith("Date:")) {
	                String dateString = line.split(":")[1].trim();
	                saleDate = dateFormat.parse(dateString);  // Parse date string into Date
	            } else if (line.startsWith("Item Name")) {
	                // Skipping header for item section
	                continue;
	            } else if (line.startsWith("-")) {
	                // Process item details from each line
	                String[] itemDetails = line.split("\\|");
	                String itemName = itemDetails[0].trim();
	                int quantity = Integer.parseInt(itemDetails[1].trim().split(":")[1].trim());
	                double price = Double.parseDouble(itemDetails[2].trim().split(":")[1].trim());
	               //error items.add(new Item(itemName, quantity, price));  // Add the item to the list
	            } else if (line.startsWith("Total Amount:")) {
	                totalAmount = Double.parseDouble(line.split(":")[1].trim());  // Extract total amount
	            }
	        }

	        // Create the Bill object using the parsed data
	        if (billNumber != null && !items.isEmpty() && totalAmount > 0 && saleDate != null) {
	            bill = new Bill(billNumber, items, totalAmount, saleDate);
	        }

	    } catch (IOException | NumberFormatException | java.text.ParseException e) {
	        e.printStackTrace();
	    }
	    return bill;
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

    // Sort items by price (low to high)
    public ArrayList<Item> sortItemsByPriceLowToHigh(ArrayList<Item> items) {
        items.sort(Comparator.comparingDouble(Item::getSellingPrice)); // Sort by selling price (low to high)
        return items;
    }

    // Sort items by price (high to low)
    public ArrayList<Item> sortItemsByPriceHighToLow(ArrayList<Item> items) {
        items.sort((item1, item2) -> Double.compare(item2.getSellingPrice(), item1.getSellingPrice())); // Sort by selling price (high to low)
        return items;
    }

    // Combine filter and sort (first by category, then by price)
    public ArrayList<Item> filterAndSortItems(String category, String sortOrder) {
        ArrayList<Item> inventory = loadInventory(); // Load all items from inventory

        // Step 1: Filter items by category
        List<Item> filteredItems = inventory.stream()
                .filter(item -> category == null || category.isEmpty() || item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        // Step 2: Sort items by category (grouped by category)
        filteredItems.sort(Comparator.comparing(Item::getCategory)); // Sort by category

        // Step 3: Sort each category by price
        ArrayList<Item> sortedItems = new ArrayList<>();
        String currentCategory = "";
        ArrayList<Item> categoryItems = new ArrayList<>();

        for (Item item : filteredItems) {
            // If a new category starts, sort the previous category's items and add them to the sorted list
            if (!item.getCategory().equalsIgnoreCase(currentCategory)) {
                if (!categoryItems.isEmpty()) {
                    if ("lowToHigh".equalsIgnoreCase(sortOrder)) {
                        sortItemsByPriceLowToHigh(categoryItems);
                    } else if ("highToLow".equalsIgnoreCase(sortOrder)) {
                        sortItemsByPriceHighToLow(categoryItems);
                    }
                    sortedItems.addAll(categoryItems);
                }
                categoryItems.clear(); // Clear previous category items
                currentCategory = item.getCategory(); // Update to new category
            }
            categoryItems.add(item); // Add item to current category's list
        }

        // Sort and add the last category
        if (!categoryItems.isEmpty()) {
            if ("lowToHigh".equalsIgnoreCase(sortOrder)) {
                sortItemsByPriceLowToHigh(categoryItems);
            } else if ("highToLow".equalsIgnoreCase(sortOrder)) {
                sortItemsByPriceHighToLow(categoryItems);
            }
            sortedItems.addAll(categoryItems);
        }

        return sortedItems;
    }
    public ArrayList<String> loadCategoriesBySectors() {
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Item> inventory = loadInventory(); // Assuming this method loads items

        for (Item item : inventory) {
            String category = item.getCategory();
            if (!categories.contains(category)) {
                categories.add(category); // Only add unique categories
            }
        }

        return categories; // Return the list of categories
    }
   
    public boolean deleteItemFromSupplier(String itemId) {
        List<Supplier> suppliers = loadSuppliers();
        boolean updated = false;

        for (Supplier supplier : suppliers) {
            if (supplier.getItemIds().contains(itemId)) {
                supplier.removeItem(itemId);
                updated = true;
            }
        }

        if (updated) {
            saveSuppliers(suppliers);
        }
        return updated;
    }
    public void loadItemsAndAssociateWithSuppliers(ArrayList<Supplier> suppliers, ArrayList<Item> items) {
        for (Item item : items) {
            // Loop through all suppliers to find the supplier that matches the item's supplier name
            for (Supplier supplier : suppliers) {
                if (supplier.getSupplierName().equals(item.getSupplierName())) {
                    // Add the item to the supplier's suppliedItems list
                    supplier.getSuppliedItems().add(item);
                }
            }
        }
    }
    // Method to load existing cashiers from the binary file
    public static ArrayList<Cashier> loadCashiers() {
        ArrayList<Cashier> cashiers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE))) {
            while (true) {
                Cashier cashier = (Cashier) ois.readObject();
                cashiers.add(cashier);
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cashiers;
    }

    public ArrayList<Cashier> loadCashiersByRole() {
        ArrayList<Cashier> cashiers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    if (user instanceof Cashier && user.getRole() == Role.Cashier) {  // Filter based on Role
                        cashiers.add((Cashier) user);
                    }
                } catch (EOFException e) {
                    break;  // End of file reached
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;  // Break the loop on error
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file.");
        }
        return cashiers;
    }
    public ArrayList<String> readBills() {
        ArrayList<String> billsData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BILL_DIRECTORY))) {
            String line;
            while ((line = reader.readLine()) != null) {
                billsData.add(line);  // Add each bill entry to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return billsData;
    }
  //Ensure directories and files exist
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
  	
  	public void saveEmployee(User employee){
  			
  		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))) {
  				oos.writeObject(employee);
  				System.out.println("Employee data saved successfully to binary file: " + EMPLOYEE_FILE);
  		} catch (IOException e) {
  			System.out.println("Error saving employee data to binary file: " + e.getMessage());
  		}
  	}
  	
  	//Load employee data from a binary file
  	public ArrayList<User> loadEmployeeData() {
  		ArrayList<User> employees = new ArrayList<>();
  		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE))) {
  			while(true) {
  				try {
  					 employees = (ArrayList<User>) ois.readObject();
  				}catch(EOFException e) {
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
		for(User employee : user) {
			if(employee.getName().equals(Name)) {
			return employee;
			}
		}
  		System.out.println("No user with the name of " +Name+ " was found!");
  		return null;
  	}
  	
  	public void updateEmployeeData(User updatedUser) {
  		ArrayList<User> employees = loadEmployeeData();
  		
  		employees.removeIf(user -> user.getUsername().equals(updatedUser.getUsername()));
  		
  		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))){
  			oos.writeObject(updatedUser);
  		}catch(IOException e) {
  			System.err.println("Error updating employee data: " + e.getMessage());
  		}
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
  	            writer.write(String.format("%-20s %-10s %-10d %-10.2f\n",
  	                    item.getItemName(),
  	                    item.getCategory() != null ? item.getCategory() : "Uncategorized", // Check for null category
  	                    item.getItemQuantity(),
  	                    item.getSellingPrice()));
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
          return date1.getYear() == date2.getYear() &&
                  date1.getMonth() == date2.getMonth() &&
                  date1.getDate() == date2.getDate();
      }
  //Load all bills for a specific cashier
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
  	
  	//Load a bill from a text file and convert it into a Bill object
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
                      //KTU KA ERROR items.add(new Item(itemName, category , price, quantity));
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
  

}
