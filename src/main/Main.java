package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
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
		String filepath = "employees.txt";
		File file = new File(filepath);
		
		if(!file.exists()) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
				writer.write("1,Florjon Allkaj,50000,Admin,Florjon05,password,11/04/2005,0696428069,florionallkaj@gmail.com");
				writer.newLine();
				writer.close();
				System.out.println("Admin file created with default admin.");
			} catch (IOException e) {
				System.err.println("Error creating admin file: " + e.getMessage());
			}
		}	
	}
	
}
