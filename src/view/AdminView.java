package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AdminView {
	private ScrollPane adminViewLayout;
	private TextField name, salary, role, username, password, dob, phone, email, modifyEmpName, deleteEmpName;
	private Button registerButton, modifyButton, deleteButton;
	
	public AdminView() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Text registerText = new Text("Register a new employee:");
		registerText.setFont(new Font(20));
		Label nameL = new Label("Name");
		name = new TextField();
		name.setPrefWidth(300);
		Label salaryL = new Label("Salary");
		salary = new TextField();
		Label roleL = new Label("Role");
		role = new TextField();
		Label usernameL = new Label("Username");
		username = new TextField();
		Label passwordL = new Label("Password");
		password = new TextField();
		Label dobL = new Label("Date of Birth");
		dob = new TextField();
		Label phoneL = new Label("Phone number");
		phone = new TextField();
		Label emailL = new Label("Email");
		email = new TextField();
		registerButton = new Button("Register");
		
		Text modifyText = new Text("Modify an employee:");
		modifyText.setFont(new Font(15));
		modifyEmpName = new TextField();
		modifyButton = new Button("Modify");
		
		Text deleteText = new Text("Delete an employee:");
		deleteText.setFont(new Font(15));
		deleteEmpName = new TextField();
		deleteButton = new Button("Delete");
		
		Text EmpTableText = new Text("Data about employees:");
		
		Text totalIncome = new Text("Total Income");
		Text totalOutcome = new Text("Total Outcome");
		
		grid.add(registerText, 0, 0);
		grid.add(nameL, 0, 1);
		grid.add(name, 0, 2);
		grid.add(salaryL, 0, 3);
		grid.add(salary, 0, 4);
		grid.add(roleL, 0, 5);
		grid.add(role, 0, 6);
		grid.add(usernameL, 0, 7);
		grid.add(username, 0, 8);
		grid.add(passwordL, 0, 9);
		grid.add(password, 0, 10);
		grid.add(dobL, 0, 11);
		grid.add(dob, 0, 12);
		grid.add(phoneL, 0, 13);
		grid.add(phone, 0, 14);
		grid.add(emailL, 0, 15);
		grid.add(email, 0, 16);
		grid.add(registerButton, 0, 17);
		
		HBox modifyHBox = new HBox(10);
		modifyHBox.setAlignment(Pos.CENTER);
		modifyHBox.getChildren().addAll(modifyText, modifyEmpName, modifyButton);
		
		HBox deleteHBox = new HBox(10);
		deleteHBox.setAlignment(Pos.CENTER);
		deleteHBox.getChildren().addAll(deleteText, deleteEmpName, deleteButton);
		
		HBox incomeOutcomeHBox = new HBox(10);
		incomeOutcomeHBox.setAlignment(Pos.CENTER);
		incomeOutcomeHBox.getChildren().addAll(totalIncome, totalOutcome);
		
		VBox vbox = new VBox(15);
		vbox.getChildren().addAll(grid, modifyHBox, deleteHBox, EmpTableText, incomeOutcomeHBox);
		vbox.setPadding(new Insets(10,10,10,10));
		adminViewLayout = new ScrollPane(vbox);
	    adminViewLayout.setFitToWidth(false);
	    adminViewLayout.setFitToHeight(true);
	}

	public ScrollPane getAdminViewLayout() {
		return adminViewLayout;
	}

	public TextField getName() {
		return name;
	}

	public TextField getSalary() {
		return salary;
	}

	public TextField getRole() {
		return role;
	}

	public TextField getUsername() {
		return username;
	}

	public TextField getPassword() {
		return password;
	}

	public TextField getDob() {
		return dob;
	}

	public TextField getPhone() {
		return phone;
	}


	public TextField getEmail() {
		return email;
	}

	public TextField getModifyEmpName() {
		return modifyEmpName;
	}

	public TextField getDeleteEmpName() {
		return deleteEmpName;
	}
	
	public Button getRegisterButton() {
		return registerButton;
	}
	
	public Button getModifyButton() {
		return modifyButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

}
