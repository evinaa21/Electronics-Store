package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ModifyEmployeeView {
	private GridPane layout;
	private TextField username, password, salary, role, email;
	private Button Modify;
	
	public ModifyEmployeeView() {
		layout = new GridPane();
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		
		Text registerText = new Text("Modify credentials");
		
		Label usernameL = new Label("Username");
		username = new TextField();
		Label passwordL = new Label("Password");
		password = new TextField();
		Label emailL = new Label("Email");
		email = new TextField();
		Label salaryL = new Label("Salary");
		salary = new TextField();
		Label roleL = new Label("Role");
		role = new TextField();
		Modify = new Button("Modify");
		
		layout.add(registerText, 0, 0);
		layout.add(usernameL, 0, 1);
		layout.add(username, 0, 2);
		layout.add(passwordL, 0, 3);
		layout.add(password, 0, 4);
		layout.add(emailL, 0, 5);
		layout.add(email, 0, 6);
		layout.add(salaryL, 0, 7);
		layout.add(salary, 0, 8);
		layout.add(roleL, 0, 9);
		layout.add(role, 0, 10);
		layout.add(Modify, 0, 11);
	}
	
	public GridPane getLayout() {
		return layout;
	}
}
