package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

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
			
			Admin admin = new Admin("Florjon Allkaj", 50000, Role.Admin, "Flori05", "password", LocalDate.of(2005, 4, 11), "069 642 8069", "Florionallkaj@gmail.com");
			try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
				
				oos.writeObject(admin);
				oos.close();
				System.out.println("Admin file created with default admin.");
			} catch (IOException e) {
				System.err.println("Error creating admin file: " + e.getMessage());
			}
		}else {
			//These will be test lines and after testing they will be deleted, among with the 
			//Here, create the manager and cashier object by yourself and output them in the employees.dat
			//Uncomment the try and catch below and add the users
//			try {
//				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
//					
//					oos.writeObject(manager);
//					oos.writeObject(cashier);
//					oos.close();
//					System.out.println("Admin file created with default admin.");
//				} catch (IOException e) {
//					System.err.println("Error creating admin file: " + e.getMessage());
//			}
		}
	}
	
}
