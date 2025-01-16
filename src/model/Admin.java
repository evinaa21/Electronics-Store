package model;


import java.util.Date;

import util.Role;
//import java.util.ArrayList;

public class Admin extends User {
//	private ArrayList<User> employees;
	
    public Admin(String name, double salary, Role role, String username, String password,
            Date dateOfBirth, String phonenumber, String email) {
        super(name, salary, role, username, password, dateOfBirth, phonenumber, email);
        //setEmployees(new ArrayList<>());
    }
    
//    public void addEmployee(Object obj) {
//    	if(obj instanceof User) {
//    		employees.add((User)obj);
//    	}else {
//    		throw new IllegalArgumentException("Invalid object Type. Expected an instance of User, Manager or Cashier.");
//    	}
//    }
  

//	public ArrayList<User> getEmployees() {
//		return employees;
//	}
//
//	public void setEmployees(ArrayList<User> employees) {
//		this.employees = employees;
//	}
}
