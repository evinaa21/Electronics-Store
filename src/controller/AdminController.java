package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
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
			RegisterEmployeeView registerAdmin = new RegisterEmployeeView();
			RegisterEmployeeController registerEmployeeController = new RegisterEmployeeController(stage, registerAdmin);
		});
		
		adminView.getModifyButton().setOnAction(event -> {
			if(adminView.getModifyEmpName().getText().equals("")) {
				//Here should be added also the case where the name is entered wrong
				alertMessage();
			}else {
				ModifyEmployeeView modEmpView = new ModifyEmployeeView();
				ModifyEmployeeController modEmpCtrl = new ModifyEmployeeController(stage, modEmpView);
			}
		});
		
		adminView.getDeleteButton().setOnAction(event -> {
			if(adminView.getDeleteEmpName().getText().equals("")) {
				//Here should be added also the case where the name is entered wrong
				alertMessage();
			}else {
				deleteEmployee(adminView.getDeleteEmpName().getText());
			}
		});
	}

	private void deleteEmployee(String name) {
		
	}

	private void alertMessage() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setContentText("You should put a valid employee name");
		alert.showAndWait();
	}
}
