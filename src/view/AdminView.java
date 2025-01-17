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
	private ScrollPane adminLayout;
	private TextField modifyEmpName, deleteEmpName;
	private Button registerAdmin, registerManager, registerCashier, modifyAdmin, modifyManager, modifyCashier, deleteUser;
	
	public AdminView() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		registerAdmin = new Button("Register a new Admin");
		registerManager = new Button("Register a new Manager");
		registerCashier = new Button("Register a new Cashier");
		
		
		modifyAdmin = new Button("Modify a Admin");
		modifyManager = new Button("Modify a Manager");
		modifyCashier = new Button("Modify a Cashier");
		
		deleteUser = new Button("Delete an User");
		
		
		
		Text EmpTableText = new Text("Data about employees:");
		//Will add the table here, by reading all the data from
		//the employee file and show only the columns that are needed
		//Also, i was thinking of adding the ArrayList as an argument of the constructor
		
		Text totalIncome = new Text("Total Income");
		Text totalOutcome = new Text("Total Outcome");
		
		
		
		HBox modifyHBox = new HBox(10);
		modifyHBox.setAlignment(Pos.CENTER);
//		modifyHBox.getChildren().addAll(modifyText, modifyEmpName, modifyButton);
		
		HBox deleteHBox = new HBox(10);
		deleteHBox.setAlignment(Pos.CENTER);
	//	deleteHBox.getChildren().addAll(deleteText, deleteEmpName, deleteButton);
		
		HBox incomeOutcomeHBox = new HBox(10);
		incomeOutcomeHBox.setAlignment(Pos.CENTER);
		incomeOutcomeHBox.getChildren().addAll(totalIncome, totalOutcome);
		
		VBox vbox = new VBox(15);
		vbox.getChildren().addAll(grid, modifyHBox, deleteHBox, EmpTableText, incomeOutcomeHBox);
		vbox.setPadding(new Insets(10,10,10,10));
		adminLayout = new ScrollPane(vbox);
	    adminLayout.setFitToWidth(false);
	    adminLayout.setFitToHeight(true);
	}

	public ScrollPane getAdminLayout() {
		return adminLayout;
	}


	public TextField getModifyEmpName() {
		return modifyEmpName;
	}

	public TextField getDeleteEmpName() {
		return deleteEmpName;
	}
	
//	public Button getRegisterButton() {
//		return register;
//	}
//	
//	public Button getModifyButton() {
//		return modifyButton;
//	}
//
//	public Button getDeleteButton() {
//		return deleteButton;
//	}

}
