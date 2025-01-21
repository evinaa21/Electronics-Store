package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import controller.CashierController;
import controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;
import model.Cashier;
import model.Sector;
import model.User;
import util.Role;
import view.LoginView;

public class Main extends Application {

//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			// Initialize objects
//			Sector sector = new Sector("Home Electronics");
//			Role role = Role.Cashier; // Assuming there is a Role enum with CASHIER
//			Cashier cashier = new Cashier("John Doe", 3000.0, role, "jdoe", "password123", new Date(), "1234567890",
//					"johndoe@example.com", sector);
//
//			// Set up the UI with CashierController
//			System.out.println("Creating cashier controller...");
//			CashierController cashierController = new CashierController(primaryStage, cashier);
//
//			// Set up the scene
//			Scene scene = cashierController.getCashierScene();
//			primaryStage.setTitle("Cashier Dashboard");
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	// Launch the application
//	public static void main(String[] args) {
//		launch(args);
//	}

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

		if (!file.exists()) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(2005, Calendar.APRIL, 11); // Year, Month (0-based), Day
			Date date = calendar.getTime();
			Admin admin = new Admin("Florjon Allkaj", 50000, Role.Admin, "Flori05", "password", date, "069 642 8069",
					"Florionallkaj@gmail.com");
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
