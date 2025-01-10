package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import view.AdminView;
import view.ModifyEmployeeView;

public class ModifyEmployeeController {
	private final Stage stage;
	private ModifyEmployeeView modEmpView;
	
	public ModifyEmployeeController(Stage stage, ModifyEmployeeView modEmpView) {
		this.stage = stage;
		this.modEmpView = modEmpView;
		createScenes();
		setButtonAction();
	}

	private void createScenes() {
		stage.setScene(new Scene(modEmpView.getLayout()));
		stage.show();
	}
	
	private void setButtonAction() {
		modEmpView.getModifyButton().setOnAction(event -> {
			
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
