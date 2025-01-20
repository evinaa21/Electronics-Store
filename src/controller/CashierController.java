	package controller;
	
	
	
	import java.util.ArrayList;
	import java.util.Date;
	
	
	
	import javafx.geometry.Pos;
	import javafx.scene.Node;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
	import javafx.scene.layout.*;
	import javafx.scene.text.Text;
	import javafx.stage.Stage;
	import model.Bill;
	import model.Cashier;
	import model.Item;
	import model.Sector;
	import model.Supplier;
	import util.FileHandler;
	import view.CashierView;
	import view.CreateBillView;
	import view.DailyBillsView;
	
	
	
	public class CashierController {
	
	    private final Stage primaryStage; // Primary application stage
	    private final Cashier cashier; // The currently logged-in cashier
	    private final Sector assignedSector; // Sector assigned to the cashier
	    private final FileHandler fileHandler; // Handles file operations
	    private final BorderPane mainLayout; // Main layout for the dashboard
	    private final StackPane centerContent; // Area for dynamic content
	    private Scene cashierScene; // Cashier scene
	
	
	public CashierController(Stage primaryStage, Cashier cashier) {
		this.primaryStage = primaryStage; // Assign the stage
		this.cashier = cashier; // Assign the logged-in cashier
		this.assignedSector = cashier.getSector(); // Get the cashier's assigned sector
		this.fileHandler = new FileHandler(); // Initialize FileHandler
		this.mainLayout = new BorderPane(); // Main layout for the dashboard
		this.centerContent = new StackPane(); // Area for dynamic content
		System.out.println("Cashier view needs to load now...");
		loadDataFromFiles();
		setupUI(); // Set up the UI components
	 }
	
	
	
	//Load data from binary and other files
	private void loadDataFromFiles() {
		// Load inventory items, bills from binary or text files
		ArrayList<Item> items = fileHandler.loadInventoryBySector(cashier.getSector().getName());
		cashier.setItems(items);
	
		ArrayList<Bill> bills = fileHandler.loadBills(cashier.getName(), new Date(System.currentTimeMillis()));
		cashier.setBills(bills);
	}
	
	
	private void setupUI() {
		
		CashierView cashierView = new CashierView(assignedSector.getName(), this, primaryStage, cashier, fileHandler);
		cashierView.setupUI(mainLayout, centerContent);
		
		//Set up the scene
		cashierScene = new Scene(mainLayout, 800, 600);
		primaryStage.setTitle("Cashier Dashboard");
		primaryStage.setScene(cashierScene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		
	
	}
	
	public void openHomePage() {
		CashierView cashierView = new CashierView(assignedSector.getName(), this, primaryStage, cashier, fileHandler);
		cashierView.showHomePage();
	}
	
	public void openCreateBillView() {
		CreateBillView createBillView = new CreateBillView();
		updateCenterContent(createBillView.getViewContent());
	}
	
	public void openDailyBillsView() {
		DailyBillsView dailyBillsView = new DailyBillsView();
		updateCenterContent(dailyBillsView.getViewContent());
	}
	
	private void updateCenterContent(Node content) {
		centerContent.getChildren().clear();
		centerContent.getChildren().add(content);
	}
	
	//Check if the selected item is out of stock
	public void handleItemSelection(String selectedItemName) {
	    boolean isOutOfStock = fileHandler.isItemOutOfStock(selectedItemName, cashier.getSector().getName());

	    if (isOutOfStock) {
	        // Notify the cashier that the selected item is out of stock
	        CashierView.showOutOfStockAlert(selectedItemName);
	    }
	}
	
	public Scene getCashierScene() {
		return cashierScene;
	}
	
	public BorderPane getMainLayout() {
		return mainLayout;
	}
	
	public StackPane getCenterContent() {
		return centerContent;
	}
	
}
	

	
	

	
	     /*
	
	
	
	    private void showCreateBillView() {
	
	        // Initialize the Create Bill view and controller
	
	        CreateBillView createBillView = new CreateBillView(); // Pass the sector to the view
	
	        CreateBillController createBillController = new CreateBillController(
	
	                createBillView.getItemsContainer(),
	
	                createBillView.getTotalField(),
	
	                createBillView.getCategoryDropdown(),
	
	                createBillView.getItemDropdown(),
	
	                assignedSector 
	
	        );
	
	
	
	        // Update the center content with the Create Bill view
	
	        centerContent.getChildren().clear();
	
	        centerContent.getChildren().add(createBillView.getView());
	
	    }
	
	
	
	    private void showDailyBillsView() {
	
	        // Initialize the Daily Bills view and controller
	
	        DailyBillsView dailyBillsView = new DailyBillsView();
	
	        DailyBillsController dailyBillsController = new DailyBillsController(
	
	        		dailyBillsView.getBillsContainer(),
	
	        		dailyBillsView.getFilterCategoryDropdown(),
	
	        		assignedSector
	
	        ); 
	
	
	
	        // Update the center content with the Daily Bills view
	
	        centerContent.getChildren().clear();
	
	        centerContent.getChildren().add(dailyBillsView.getMainLayout());
	
	    }
	
	
	
	   
	
	    public Cashier getCashier() {
	
	    	return this.cashier;
	
	    }
	
	}
	*/