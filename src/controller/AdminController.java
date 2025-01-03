package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.AdminView;
import view.ModifyEmployeeView;

public class AdminController {
	private final AdminView adminView;
	private final Stage stage;
	
	public AdminController(Stage stage, AdminView adminView) {
		this.stage = stage;
		this.adminView = adminView;
		setButtonActions();
	}
	
	private void setButtonActions() {
		adminView.getRegisterButton().setOnAction(event -> {
			String name = adminView.getName().getText();
			String salary = adminView.getSalary().getText();
			String role = adminView.getRole().getText();
			String username = adminView.getUsername().getText();
			String password = adminView.getPassword().getText();
			String dob = adminView.getDob().getText();
			String phone = adminView.getPhone().getText();
			String email = adminView.getEmail().getText();
		});
		
		adminView.getModifyButton().setOnAction(event -> {
			ModifyEmployeeView modEmpView = new ModifyEmployeeView();
			stage.setScene(new Scene(modEmpView.getLayout(), 800, 600));
		});
	}
}
