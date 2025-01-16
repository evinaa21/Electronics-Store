package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Admin;
import model.Cashier;
import model.Manager;
import model.Sector;
import model.User;
import util.CredentialsException;
import util.FileHandler;
import util.Role;
import view.AdminView;
import view.RegisterEmployeeView;

public class RegisterEmployeeController {
	private final Stage stage;
	private final RegisterEmployeeView registerEmpView;
	private String name, username, password, phone, email, role;
	private double salary;
	private LocalDate dob;
	private String sectorName;
	private FileHandler file = new FileHandler();
	private ArrayList<User> data = file.loadEmployeeData();
	
	public RegisterEmployeeController(Stage stage, RegisterEmployeeView registerEmpView) {
		this.stage = stage;
		this.registerEmpView = registerEmpView;
		createScene();
		setButtonActions();
	}
	
	private void createScene() {
		stage.setScene(new Scene(registerEmpView.getLayout()));
		stage.show();
	}

	private void setButtonActions() {
		registerEmpView.getRegisterButton().setOnAction(event -> {
			try {
				name = registerEmpView.getName().getText();
				salary = parseSalary(registerEmpView.getSalary().getText());
				username = registerEmpView.getUsername().getText();			
				password = registerEmpView.getPassword().getText();			
				dob = registerEmpView.getDob().getValue();			
				phone = registerEmpView.getPhone().getText();			
				email = registerEmpView.getEmail().getText();			
				role = registerEmpView.getRole().getText();
				sectorName = registerEmpView.getSector();
				
				if(validateCredentials(name, username, password, dob, phone, email, role, sectorName)) {
					switch (role) {
						case "Admin":
							Admin user = new Admin(name, salary, Role.Admin, username, password, dob, phone, email);
							data.add(user);
							file.saveEmployeeData(data);
							break;
						case "Manager":
							Manager manager = new Manager(name, salary, Role.Manager, username, password, dob, phone, email);
							data.add(manager);
							file.saveEmployeeData(data);
							break;
						default:
							ArrayList<Sector> s = file.loadSectors();
							Sector sector = null;
							for(Sector sec : s) {
								if(sec.getName().equals(sectorName)) {
									sector = sec;
								}
							}
							Cashier cashier = new Cashier(name, salary, Role.Cashier, username, password, dob, phone, email, sector);
							data.add(cashier);
							file.saveEmployeeData(data);
							break;
					}
					showRegistrationSuccess();
					AdminView adminView = new AdminView();
					AdminController adminController = new AdminController(stage, adminView);	
				}
				
			}catch(Exception e) {
				System.out.println("Error during registration: " + e.getMessage());
				showAlert("Error during registration!");
				RegisterEmployeeView registerEmpView = new RegisterEmployeeView();
				RegisterEmployeeController registerEmpCtrl = new RegisterEmployeeController(stage,registerEmpView);
				return;
			}
		});
	}
	
	private boolean validateCredentials(String name2, String username2, String password2, LocalDate dob2, String phone2,  String email2, String role2, String sector2) throws CredentialsException {
		
		if(!email2.contains(String.valueOf("@"))) {
			showAlert("Please enter a valid email!");
			throw new CredentialsException("Email input is wrong!");
		}
		
		for(User user : data) {
			if(user.getName().equals(name2)) {
				showAlert("Please enter a valid name!");
				throw new CredentialsException("Name already exists!");
			}
			
			if(user.getUsername().equals(username2)) {
				showAlert("Please enter a valid username!");
				throw new CredentialsException("Username already exists!");
			}
			
			if(user.getEmail().equals(email2)) {
				showAlert("Please enter a valid email!");
				throw new CredentialsException("Email already exists!");
			}
			
		}
		
		
		return true;
	}

	private double parseSalary(String salaryString) {
		try {
			return Double.parseDouble(salaryString);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid salary formal.");
		}
	}

	private void showRegistrationSuccess() {
		Alert successMsg = new Alert(AlertType.INFORMATION);
		successMsg.setTitle("User registered Successfully");
		successMsg.setHeaderText(null);
		successMsg.setContentText("The user was registered successfully!");
	    successMsg.showAndWait(); 
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Validation Error");
		alert.setHeaderText("Invalid Input");
		alert.setContentText(message);
	    alert.showAndWait(); 
	}
}
