package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Bill implements Serializable{
	private static final long serialVersionUID = -4660349044455634797L;
	
	private String billNumber;
	private ArrayList<Item> items;
	private double totalAmount;
	private Date saleDate;
	
	
	public Bill(String billNumber, ArrayList<Item> items, double totalAmount, Date saleDate) {
		this.billNumber = billNumber;
		this.items = items; 
		this.totalAmount = totalAmount;
		this.saleDate = saleDate;
	}
	
	public String getBillNumber() {
		return billNumber;
	}
	
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public Date getSaleDate() {
		return saleDate;
	}
	
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	public double calculateTotal() {
		double total = 0.0;
		for(int i = 0; i <items.size(); i++) {
			total += items.get(i).getSellingPrice() * items.get(i).getItemQuantity();
		}
		
		this.totalAmount = total; //Update totalAmount
		return total;
	}
	
	public void printBill() {
		System.out.println("Bill Number: " + billNumber);
		System.out.println("Sale Date: " + saleDate);
		System.out.println("Items:");
		
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			System.out.println("- " + item.getItemName() + " | Quantity: " + item.getItemQuantity() 
			+ " | Price: $ " + item.getSellingPrice() + "each");
		}
		
		System.out.println("Total Amount: $" + totalAmount);
	}
	
	
	
	@Override
	public String toString() {
		return "Bill{ " +
				"billNumber= " + billNumber + '\n' +
				", totalAmount= " + totalAmount + 
				", saleDate= " + saleDate + '}';
	}
}
