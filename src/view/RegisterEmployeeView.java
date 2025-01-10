package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterEmployeeView {
	private GridPane grid;
	private TextField name, salary, role, username, password, dob, phone, email;
	private Button registerButton;
	
	public RegisterEmployeeView() {
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
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
		grid.add(registerButton, 0, 18);
	}
	
	public GridPane getLayout() {
		return grid;
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
	
	public Button getRegisterButton() {
		return registerButton;
	}
}
