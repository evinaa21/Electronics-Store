package controller;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Admin;
import model.User;
import util.DeleteException;
import util.FileHandler;
import util.NavBar;
import view.AdminView;
import view.ModifyEmployeeView;

public class AdminController {
	private final AdminView adminView;
	private final Stage stage;
	
	public AdminController(Stage stage, AdminView adminView) {
		this.stage = stage;
		this.adminView = adminView;
		loadNavBar();
		createScenes();
		setButtonActions();
	}
	
	private void loadNavBar() {
		NavBar navBar = adminView.getNavBar();
		NavBarController navBarController = new NavBarController(stage);
		navBarController.configureNavBar(navBar);
	}

	private void createScenes() {
		stage.setScene(new Scene(adminView.getAdminLayout(), 670, 400));
		stage.show();
	}

	private void setButtonActions() {
		adminView.getDeleteButton().setOnAction(event -> {
			FileHandler file = new FileHandler();
			if(adminView.getDeleteEmployeeName().getText().equals("") || file.loadEmployee(adminView.getDeleteEmployeeName().getText()) == null) {
				alertMessage("Warning!", "You should put a valid employee name!");
			}else {
				deleteEmployee(adminView.getDeleteEmployeeName().getText(), adminView.getTable());
			}
		});
		
		adminView.getEditButton().setOnAction(event -> {
			FileHandler file = new FileHandler();
			if(adminView.getEditEmployeeName().getText().equals("") || file.loadEmployee(adminView.getEditEmployeeName().getText()) == null) {
				alertMessage("Warning!", "You should put a valid employee name!");
			}else {
				User user = file.loadEmployee(adminView.getEditEmployeeName().getText());
				if(user instanceof Admin) {
					alertMessage("Information!", "Admins cannot be modified!");
				}else {
					ModifyEmployeeView MEmpV = new ModifyEmployeeView();
					ModifyEmployeeController MEmpC = new ModifyEmployeeController(stage, MEmpV, user.getName());
				}
			}
		});
	}

	private void deleteEmployee(String name, TableView<User> table) {
		try {
			FileHandler file = new FileHandler();
			ArrayList<User> empData = file.loadEmployeeData();
			User admin = file.loadEmployee(name);
			if(admin instanceof Admin) {
				int i=0;
				for(User user : empData) {
					if(user instanceof Admin) {
						i++;
					}
				}
				if(i==1) {
					throw new DeleteException("You cannot delete the last admin!");
				}
			}
			for(User user : empData) {
				if(user.getName().equals(name)) {
					empData.removeIf(obj -> obj.equals(user));
					User.idCounter--;
					break;
				}
			}
			file.saveEmployeeData(empData);
			table.refresh();
			alertMessage("Success!", "User deleted succesfully!");
			AdminView adminView = new AdminView();
			AdminController adminController = new AdminController(stage, adminView);
		}catch(DeleteException e) {
			System.out.println("Error during deletion: " + e.getMessage());
			alertMessage("Error", "There should be at least one Admin");
		}catch(Exception e) {
			System.out.println("Error during registration: " + e.getMessage());
			alertMessage("Warning", "Error during registration!");
		}
	}

	private void alertMessage(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
