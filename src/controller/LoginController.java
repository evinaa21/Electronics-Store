package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import model.Admin;
import model.User;
import util.FileHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.LoginView;
import view.AdminView;

public class LoginController {
	private static final String FILE_PATH = "employees.dat";
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
		ArrayList<User> employees = new ArrayList<>();
		FileHandler file = new FileHandler();
		employees = file.loadEmployeeData();
		
		for(User user : employees) {
			String usernameInFile = user.getUsername();
			String passwordInFile = user.getPassword();
			if(user instanceof Admin) {
				if(username.equals(usernameInFile) && password.equals(passwordInFile)) {
					AdminView adminView = new AdminView();
					AdminController adminController = new AdminController(stage, adminView);
					break;
				}
			}
//			}else if(user instanceof Manager) {
//				if(username.equals(usernameInFile) && password.equals(passwordInFile)) {
//					ManagerView managerView = new ManagerView();
//					ManagerController managerController = new ManagerController(stage, managerView);
//					break;
//				}
//			}else if(user instanceof Cashier){
//				if(username.equals(usernameInFile) && password.equals(passwordInFile)) {
//					CashierView cashierView = new CashierView();
//					CashierController cashierController = new CashierController(stage, cashierView);
//					break;
//				}
//			}
				else {
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


	public Scene getLoginScene() {
		return loginScene;
	}
}
