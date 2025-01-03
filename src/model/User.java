// src/model/User.java
package model;

import util.Role;
import java.util.Date;

abstract class User {
	private static int idCounter = 1;
	private int id;
    private String name;
    private double salary;
    private Role role;
    private String username;
    private String password;
    private Date dateOfBirth;
    private long phonenumber;
    private String email;

    public User() {
    };

    public User(String name, double salary, Role role, String username, String password, Date dateOfBirth,
            long phonenumber, String email) {
        this.id = idCounter++;
        this.name = name;
        this.salary = salary;
        this.role = role;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
