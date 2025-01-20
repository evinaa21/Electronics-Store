package controller;
import javafx.stage.Stage;
import util.NavBar;
import view.AdminView;
import view.RegisterEmployeeView;


public class NavBarController {
	private Stage stage;

	public NavBarController(Stage stage) {
		this.stage = stage;
	}
	
	public void configureNavBar(NavBar navBar) {
		navBar.getHomeButton().setOnAction(event -> {
			AdminView admin = new AdminView();
			AdminController adminC = new AdminController(stage, admin);
		});
		
		navBar.getRegister().setOnAction(event -> {
			RegisterEmployeeView registerEmployeeView = new RegisterEmployeeView();
			RegisterEmployeeController registerEmployeeController = new RegisterEmployeeController(stage, registerEmployeeView);
		});
	}
}
