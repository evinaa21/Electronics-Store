package model;

import java.util.ArrayList;
import java.util.Date;

import util.FileHandler;
import util.Role;
import exceptions.BillGenerationException;
import exceptions.LowStockException;

public class Cashier extends User {
	private Sector sector; 
	private ArrayList<Bill> bills;  
	private double totalSales; 
	
	public Cashier(String name, double salary, Role role, String username, String password, 
			Date dateOfBirth, long phonenumber, String email, Sector sector) {
		super(name, salary, role, username, password, dateOfBirth, phonenumber, email);
		this.sector = sector;
		this.bills = new ArrayList<>();
		this.totalSales = 0.0;
	}
	
	public Sector getSector() {
		return sector;
	}
	
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	
	public ArrayList<Bill> getBills() {
		return bills;
	}
	
	public void setBills(ArrayList<Bill> bills) {
		this.bills = bills;
	}
	
	public double getTotalSales() {
		return totalSales;
	}
	
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	
	//Create bill
	public Bill createBill(ArrayList<Item> items) throws LowStockException { 
		double totalAmount = 0.0;
		
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			int quantity = item.getItemQuantity();
			
			//Validation step to check the quantity
			if(quantity <= 0) {
				throw new IllegalArgumentException("Invalid quantity for item: " + item.getItemName());
			}
			
			//Validation to ensure the item is in the Cashier's sector
			if(!sector.viewItems().contains(item)) {
				throw new IllegalArgumentException("item not found in the assigned sector: " + item.getItemName());
			}
			
			checkStock(item, quantity);  
			
			item.updateStock(-quantity);
			
			totalAmount += item.getSellingPrice() * quantity;		
		} 
		//Generating an unique bill based on the current time and bill
		String billNumber = "BILL-" + (bills.size() + 1) + "-" + System.currentTimeMillis();
		Bill bill = new Bill(billNumber, items, totalAmount, new Date());
		
		//Add this bill to the cashier's list & update totalSales
		bills.add(bill);
		totalSales += totalAmount;
		
		return bill;
	} 
		
		//Generate and save a bill
		public void generateBill(Bill bill) throws BillGenerationException {
	        try {
	            FileHandler fileHandler = new FileHandler();
	            fileHandler.saveBill(bill.getBillNumber(), bill.getItems(), bill.getTotalAmount());
	        } catch (Exception e) {
	            throw new BillGenerationException("Error generating bill: " + e.getMessage());
	        }
	    } 
		
		//Check if stock is available for the requested quantity
		public void checkStock(Item item, int requestedQuantity) throws LowStockException {
			//Check if available stock is less than requested
			if(item.getStockQuantity() < requestedQuantity) {
				throw new LowStockException("Insufficient stock for item: " + item.getItemName()); 
			}
		}
		
		//View all daily bills
		public ArrayList<Bill> viewDailyBills() {
			//List to hold today's bills
			ArrayList<Bill> dailyBills = new ArrayList<>();
			Date today = new Date();
			
			//Iterate the bills to find bills of current day
			for(int i = 0; i < bills.size(); i++) {
				Bill bill = bills.get(i);
;				if(isSameDay(bill.getSaleDate(), today)) {
					dailyBills.add(bill);
				}
			}
			
			return dailyBills;
		}
		
		//Compare dates to check if they are on the same day
		private boolean isSameDay(Date date1, Date date2) {
			return date1.getYear() == date2.getYear() &&
					date1.getMonth() == date2.getMonth() &&
					date1.getDate() == date2.getDate();
		}
		
		//Calculate daily total sales
		public double calculateDailyTotal() {
			double dailyTotal = 0.0;
			Date today = new Date();
			
			//Iterate the bills and sum up today's total
			for(int i = 0; i < bills.size(); i++) {
				Bill bill = bills.get(i);
				if(isSameDay(bill.getSaleDate(), today)) {
					dailyTotal += bill.getTotalAmount();
				}
			}
			
			return dailyTotal;
		}
		
	
}
	
	


