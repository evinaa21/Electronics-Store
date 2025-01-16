package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Admin;
import util.Role;
import view.LoginView;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		LoginView loginView = new LoginView();
		LoginController loginController = new LoginController(stage, loginView);
		
		stage.setTitle("Electronic Store");
		stage.setScene(loginController.getLoginScene());
		stage.show();
	}
	
	public static void main(String[] args) {
		initializeAdminFile();
		launch(args);
	}

	private static void initializeAdminFile() {
		String filepath = "src/BinaryFiles/employees.dat";
		File file = new File(filepath);
		
		if(!file.exists()) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(2005, Calendar.APRIL, 11); // Year, Month (0-based), Day
			Date date = calendar.getTime();
			Admin admin = new Admin("Florjon Allkaj", 50000, Role.Admin, "Flori05", "password", date, "069 642 8069", "Florionallkaj@gmail.com");
			try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
				
				oos.writeObject(admin);
				oos.close();
				System.out.println("Admin file created with default admin.");
			} catch (IOException e) {
				System.err.println("Error creating admin file: " + e.getMessage());
			}
		}
	}
}
