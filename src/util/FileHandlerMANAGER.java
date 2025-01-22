package util;

import model.Cashier;
import model.Item;
import model.Manager;
import model.Sector;
import model.Supplier;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandlerMANAGER{
	// Constants for file paths CHANGE IF THESE DONT WORK FOR YOU
	private static final String EMPLOYEE_FILE = "src/BinaryFiles/employees.dat"; // Binary files for employees
	private static final String INVENTORY_FILE = "C:\\Users\\Evina\\git\\Electronics-Store\\src\\BinaryFiles\\items .dat"; // Binary files for inventory
	private static final String BILL_DIRECTORY = "src/BinaryFiles/Bills/"; // Text files for bills
	private static final String SECTOR_FILE = "src/BinaryFiles/sectors.dat"; // Path to sector file
	private static final String SUPPLIER_FILE = "src/BinaryFiles/suppliers.dat"; // Binary files for suppliers

	public FileHandlerMANAGER() {

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

	// Add a new item to the inventory and save it to the file
	public void addNewItem(Item newItem) {
		ArrayList<Item> inventory = loadInventory(); // Load existing inventory
		inventory.add(newItem); // Add the new item
		saveInventory(inventory); // Save the updated inventory
		System.out.println("New item added to inventory: " + newItem.getItemName());
	}

	public boolean deleteItem(Item itemToDelete) {
		try {
			ArrayList<Item> items = loadInventory(); // Load existing items
			boolean removed = items.removeIf(item -> item.getItemName().equals(itemToDelete.getItemName())); // Remove
																												// the
																												// matching
																												// item

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
			oos.writeObject(inventory); // Write the entire list of items to the file
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

	@SuppressWarnings("unchecked")
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
			return; // If the list is empty, do not proceed with saving.
		}

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SECTOR_FILE))) {
			out.writeObject(sectors); // Serialize the sectors list (which includes categories).
			System.out.println("Sectors saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving sectors to file.");
		}
	}

	


	// Load inventory data from the item.dat file
	// This method does not modify or update the inventory file
	@SuppressWarnings("unchecked")
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

		// Updated method for reading low stock items based on sector categories
	public static ArrayList<Item> readLowStockItemsFromBinaryFileMANAGER(String fileName, int threshold, ArrayList<Sector> managerSectors) {
	    ArrayList<Item> lowStockItems = new ArrayList<>();
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
	        @SuppressWarnings("unchecked")
			ArrayList<Item> inventory = (ArrayList<Item>) ois.readObject();
	        
	        // Iterate through the inventory and check stock levels and sector-category match
	        for (Item item : inventory) {
	            for (Sector sector : managerSectors) {
	                // Compare category of item to sector's categories
	                if (sector.getCategories().contains(item.getCategory()) && item.getStockQuantity() <= threshold) {
	                    lowStockItems.add(item);
	                    break;  // Exit once a match is found
	                }
	            }
	        }
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return lowStockItems;
	}
	// Updated method to notify about low stock items
		public ArrayList<Item> notifyLowStockforManager(int threshold, ArrayList<Sector> managerSectors) {
		    ArrayList<Item> lowStockItems = new ArrayList<>();

		    // Get all low stock items from binary file
		    ArrayList<Item> allItems = readLowStockItemsFromBinaryFileMANAGER(INVENTORY_FILE, threshold, managerSectors);
		    System.out.println("All low stock items: " + allItems);  // Debugging output

		    // Iterate through all items and check sector-category match and stock level
		    for (Item item : allItems) {
		        System.out.println("Checking item: " + item.getItemName() + ", Stock: " + item.getStockQuantity() + ", Category: " + item.getCategory());
		        
		        for (Sector sector : managerSectors) {
		            // Check if item's category matches any of the manager's sector categories and stock is low
		            if (sector.getCategories().contains(item.getCategory()) && item.getStockQuantity() <= threshold) {
		                lowStockItems.add(item);
		                break;  // Exit once a match is found
		            }
		        }
		    }
		    return lowStockItems;
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
			// Loop through all suppliers to find the supplier that matches the item's
			// supplier name
			for (Supplier supplier : suppliers) {
				if (supplier.getSupplierName().equals(item.getSupplierName())) {
					// Add the item to the supplier's suppliedItems list
					supplier.getSuppliedItems().add(item);
				}
			}
		}
	}

	
	public ArrayList<Cashier> loadCashiersByRole(ArrayList<Sector> managerSectors) {
        ArrayList<Cashier> cashiers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE))) {
            // First, read the entire ArrayList of Users
            @SuppressWarnings("unchecked")
			ArrayList<User> users = (ArrayList<User>) ois.readObject(); // Read the whole list

            // Debug: Print Manager Sectors
            System.out.println("Manager Sectors: " + managerSectors);
            for (User user : users) {
                if (user instanceof Cashier) {
                    Cashier cashier = (Cashier) user;
                    System.out.println("Checking Cashier: " + cashier.getName() + " with Sector: " + cashier.getSector());

                    // Check if cashier's sector matches any of the manager's sectors
                    for (Sector managerSector : managerSectors) {
                        System.out.println("Comparing with Manager Sector: " + managerSector);
                        if (cashier.getSector().equals(managerSector)) {
                            cashiers.add(cashier);
                            break; // No need to check further once a match is found
                        }
                    }
                }
            }
        } catch (EOFException e) {
            // Handle end of file gracefully (if needed)
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            e.printStackTrace();
        }
        return cashiers;
    }


    public ArrayList<Sector> loadManagerSectors() {
        ArrayList<Sector> managerSectors = new ArrayList<>();
        
        // Load all employee data
        ArrayList<User> allUsers = loadEmployeeData();  // Assuming this method loads all employees
        
        // Loop through the users to find Managers and extract their sectors
        for (User user : allUsers) {
            if (user instanceof Manager) {
                Manager manager = (Manager) user;
                managerSectors.addAll(manager.getSectors());  // Add Manager's sectors to the list
            }
        }
        
        return managerSectors;
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


	// Save inventory data to the item.dat file
	public void saveIventory(ArrayList<Item> inventory) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
			oos.writeObject(inventory);
			System.out.println("Inventory saved successfully to binary file: " + INVENTORY_FILE);
		} catch (IOException e) {
			System.err.println("Error saving inventory to binary file: " + e.getMessage());
		}
	}

	

	// Load employee data from a binary file
	@SuppressWarnings("unchecked")
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

}
