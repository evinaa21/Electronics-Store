package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.LoginView;
import view.AdminView;

public class LoginController {
	private static final String FILE_PATH = "employees.txt";
	private final Stage stage;
	private final LoginView loginView;
	private Scene loginScene;
	
	public LoginController(Stage stage, LoginView loginView) {
		this.stage = stage;
		this.loginView = loginView;
		createScenes();
		setButtonAction();
	}
	
	private void createScenes() {
		loginScene = new Scene(loginView.getLoginPane(), 400, 300);
	}
	
	private void setButtonAction() {
		loginView.getLoginButton().setOnAction(event -> {
			String username = loginView.getUsernameField().getText();
			String password = loginView.getPasswordField().getText();
	        
			authenticate(username, password);
		});
	}

	private void authenticate(String username, String password){
		ArrayList<String[]> database = loadEmployeeData();
		
		for(String[] column: database) {
			String role = column[3].trim();
			String usernameInFile = column[4].trim();
			String passwordInFile = column[5].trim();
			
			if(username.equals(usernameInFile) && password.equals(passwordInFile)) {
				if(role.equals("Admin")) {
					AdminView adminView = new AdminView();
					AdminController adminController = new AdminController(stage, adminView);
					break;
				}
//				 else if(role.equals("Cashier")) {
//					CashierView CashierView = new CashierView();
//					CashierController cashierController = new CashierController(stage, CashierView);
//					break;
//				}else {
//					ManagerView ManagerView = new ManagerView();
//					ManagerController ManagerController = new ManagerController(stage, ManagerView);
//					break;
//				}
			}else {
				Label errorMessageLabel = new Label();
		        GridPane loginLayout = loginView.getLoginPane();
		        
				errorMessageLabel.setText("Wrong Credentials");
	            errorMessageLabel.setStyle("-fx-text-fill: red;");
	            loginLayout.add(errorMessageLabel, 0, 5);
	            
	            PauseTransition pause = new PauseTransition(Duration.seconds(10));
	            pause.setOnFinished(e1 -> loginLayout.getChildren().remove(errorMessageLabel));
	            pause.play();
			}
		}
	}
	
	private ArrayList<String[]> loadEmployeeData(){
	    ArrayList<String[]> employeeData = new ArrayList<>();
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
	        String line;
	        while ((line = reader.readLine()) != null){
	            String[] data = line.split(",");
	            employeeData.add(data);
	        }
	    } catch (IOException e) {
	        System.err.println("Error reading the employee file: " + e.getMessage());
	    }
	    return employeeData;
	}

	public Scene getLoginScene() {
		return loginScene;
	}
}
