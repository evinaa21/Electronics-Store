// src/model/User.java
package model;

import util.Role;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static int idCounter = 0;
	private int id;
    private String name;
    private double salary;
    private Role role;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private String phonenumber;
    private String email;

    public User() {
    };

    public User(String name, double salary, Role role, String username, String password, LocalDate dateOfBirth,
            String phonenumber, String email) {
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

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
