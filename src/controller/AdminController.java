package controller;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import util.FileHandler;
import view.AdminView;
import view.RegisterEmployeeView;
import view.ModifyEmployeeView;

public class AdminController {
	private final AdminView adminView;
	private final Stage stage;
	
	public AdminController(Stage stage, AdminView adminView) {
		this.stage = stage;
		this.adminView = adminView;
		createScenes();
		setButtonActions();
	}
	
	private void createScenes() {
		stage.setScene(new Scene(adminView.getAdminLayout(), 700, 400));
		stage.show();
	}

	private void setButtonActions() {
		adminView.getRegisterButton().setOnAction(event -> {
			RegisterEmployeeView registerEmpView = new RegisterEmployeeView();
			RegisterEmployeeController registerEmployeeController = new RegisterEmployeeController(stage, registerEmpView);
		});
		
		adminView.getModifyButton().setOnAction(event -> {
			FileHandler file = new FileHandler();
			if(adminView.getModifyEmpName().getText().equals("") || file.getEmployee(adminView.getModifyEmpName().getText()) == null){
				alertMessage();
			}else {
				ModifyEmployeeView modEmpView = new ModifyEmployeeView();
				ModifyEmployeeController modEmpCtrl = new ModifyEmployeeController(stage, modEmpView, file.getEmployee(adminView.getModifyEmpName().getText()));
			}
		});
		
		adminView.getDeleteButton().setOnAction(event -> {
			FileHandler file = new FileHandler();
			if(adminView.getDeleteEmpName().getText().equals("") || file.getEmployee(adminView.getDeleteEmpName().getText()) == null) {
				alertMessage();
			}else {
				deleteEmployee(adminView.getDeleteEmpName().getText());
			}
		});
	}

	private void deleteEmployee(String name) {
		FileHandler file = new FileHandler();
		ArrayList<User> empData = file.loadEmployeeData();
		for(User user : empData) {
			if(user.getName().equals(name)) {
				empData.removeIf(obj -> obj.equals(user));
				User.idCounter--;
				break;
			}
		}
		file.saveEmployeeData(empData);
	}

	private void alertMessage() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setContentText("You should put a valid employee name");
		alert.showAndWait();
	}
}
