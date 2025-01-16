package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import util.FileHandler;
import view.AdminView;
import view.ModifyEmployeeView;

public class ModifyEmployeeController {
	private final Stage stage;
	private ModifyEmployeeView modEmpView;
	private User user;
	
	public ModifyEmployeeController(Stage stage, ModifyEmployeeView modEmpView, User user) {
		this.stage = stage;
		this.modEmpView = modEmpView;
		this.user = user;
		createScenes();
		setButtonAction();
	}

	private void createScenes() {
		stage.setScene(new Scene(modEmpView.getLayout()));
		stage.show();
	}
	
	private void setButtonAction() {
		modEmpView.getModifyButton().setOnAction(event -> {
			
			if(modEmpView.getUsername().getText() != null) {
				user.setUsername(modEmpView.getUsername().getText());
			}
			
			if(modEmpView.getPassword().getText() != null) {
				user.setPassword(modEmpView.getPassword().getText());
			}
			
			if(modEmpView.getEmail().getText() != null) {
				user.setEmail(modEmpView.getEmail().getText());
			}
			
			if(modEmpView.getSalary().getText() != null) {
				user.setEmail(modEmpView.getSalary().getText());
			}
			
			if(modEmpView.getRole().getText() != null) {
				user.setEmail(modEmpView.getRole().getText());
			}
			
			FileHandler fileHandler = new FileHandler();
	        fileHandler.updateEmployeeData(user);
			
			showModificationSuccess();
			AdminView adminView = new AdminView();
			AdminController adminController = new AdminController(stage, adminView);
		});
	}

	private void showModificationSuccess() {
		Alert successMsg = new Alert(AlertType.INFORMATION);
		successMsg.setTitle("User modified Successfully");
		successMsg.setHeaderText(null);
		successMsg.setContentText("The user was modified successfully!");
	    successMsg.showAndWait();
		
	}
}
