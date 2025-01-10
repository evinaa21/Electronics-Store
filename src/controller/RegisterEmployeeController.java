package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import view.AdminView;
import view.RegisterEmployeeView;

public class RegisterEmployeeController {
	private final Stage stage;
	private final RegisterEmployeeView registerEmpView;
	
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
			
			showRegistrationSuccess();
			AdminView adminView = new AdminView();
			AdminController adminController = new AdminController(stage, adminView);
		});
	}

	private void showRegistrationSuccess() {
		Alert successMsg = new Alert(AlertType.INFORMATION);
		successMsg.setTitle("User registered Successfully");
		successMsg.setHeaderText(null);
		successMsg.setContentText("The user was registered successfully!");
	    successMsg.showAndWait(); 
	}
}
