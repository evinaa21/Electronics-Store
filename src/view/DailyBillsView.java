package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class DailyBillsView {

    private BorderPane mainLayout; // Main layout container
    private StackPane centerContent; // Dynamic center content area
    private VBox billsContainer; // Container for displaying bill details
    private ComboBox<String> filterCategoryDropdown; // Dropdown to filter by category
    private ComboBox<String> filterTimeDropdown; // Dropdown to filter by time (e.g., morning, afternoon)
    private TextField filterAmountRange; // Text field for amount range filtering
    private ScrollPane billsScrollPane; // Scrollable container for bills
    private Button calculateTotalButton; // Button to calculate total of displayed bills
    private Button exportBillsButton; // Button to export bills
    private Button printBillsButton; // Button to print bills
    private Button viewDetailsButton; // Button to view details of a selected bill
    private Label totalSalesLabel; // Label to display total sales for the day

    //Constructor
    public DailyBillsView() {
        mainLayout = new BorderPane(); //Initialize main layout
        centerContent = new StackPane(); //Initialize dynamic center content

        //Title and navigation bar at the top
        HBox navigationBar = createNavigationBar();
        mainLayout.setTop(navigationBar);

        //Initialize the bills container
        billsContainer = new VBox(10); //Vertical layout with spacing
        billsContainer.setPadding(new Insets(10));
        billsContainer.setStyle("-fx-background-color: #f4f4f4;");

        //ScrollPane to wrap the bills container
        billsScrollPane = new ScrollPane(billsContainer);
        billsScrollPane.setFitToWidth(true);
        billsScrollPane.setStyle("-fx-background: #f4f4f4;");

        //Filters section
        HBox filters = createFilters();

        //Actions section
        HBox actions = createActions();
		this.viewDetailsButton = new Button();

        //Total sales display
        totalSalesLabel = new Label("Total Sales for Today: $0.00");
        totalSalesLabel.setFont(new Font("Arial", 16));
        totalSalesLabel.setStyle("-fx-font-weight: bold;");
        HBox totalSalesBox = new HBox(totalSalesLabel);
        totalSalesBox.setAlignment(Pos.CENTER_RIGHT);
        totalSalesBox.setPadding(new Insets(10));

        //Assemble content layout
        VBox contentLayout = new VBox(10, filters, actions, billsScrollPane, totalSalesBox);
        contentLayout.setPadding(new Insets(10));

        //Set the content to center
        centerContent.getChildren().add(contentLayout);
        mainLayout.setCenter(centerContent);
    }

    //Create navigation bar
    private HBox createNavigationBar() {
        HBox navigationBar = new HBox(10);
        navigationBar.setAlignment(Pos.CENTER);
        navigationBar.setPadding(new Insets(10));
        navigationBar.setStyle("-fx-background-color: #007BFF;");

        Label titleLabel = new Label("Daily Bills");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        navigationBar.getChildren().add(titleLabel);
        return navigationBar;
    }

    //Create filters section
    private HBox createFilters() {
        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER_LEFT);
        filters.setPadding(new Insets(10));

        // Filter by category
        filterCategoryDropdown = new ComboBox<>();
        filterCategoryDropdown.getItems().addAll("All Categories", "Home Electronics", "Mobile Gadgets", "Computing & Laptops", "Audio & Video");
        filterCategoryDropdown.setPromptText("Filter by Category");

        // Filter by time
        filterTimeDropdown = new ComboBox<>();
        filterTimeDropdown.getItems().addAll("All Times", "Morning", "Afternoon", "Evening");
        filterTimeDropdown.setPromptText("Filter by Time");

        // Filter by amount range
        filterAmountRange = new TextField();
        filterAmountRange.setPromptText("Filter by Amount Range (e.g., 100-500)");

        // Add filters to layout
        filters.getChildren().addAll(
                new Label("Category:"), filterCategoryDropdown,
                new Label("Time:"), filterTimeDropdown,
                new Label("Amount Range:"), filterAmountRange
        );

        return filters;
    }

    // Create actions section
    private HBox createActions() {
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(10));

        // View details button
        viewDetailsButton = new Button("View Details");
        viewDetailsButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");

        // Calculate total button
        calculateTotalButton = new Button("Calculate Total");
        calculateTotalButton.setStyle("-fx-background-color: #28A745; -fx-text-fill: white;");

        // Export bills button
        exportBillsButton = new Button("Export Bills");
        exportBillsButton.setStyle("-fx-background-color: #17A2B8; -fx-text-fill: white;");

        // Print bills button
        printBillsButton = new Button("Print Bills");
        printBillsButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black;");

        // Add buttons to layout
        actions.getChildren().addAll(viewDetailsButton, calculateTotalButton, exportBillsButton, printBillsButton);

        return actions;
    }

    // Getters for UI elements
    public BorderPane getViewContent() {
        return mainLayout;
    }

    public VBox getBillsContainer() {
        return billsContainer;
    }

    public ComboBox<String> getFilterCategoryDropdown() {
        return filterCategoryDropdown;
    }

    public ComboBox<String> getFilterTimeDropdown() {
        return filterTimeDropdown;
    }

    public TextField getFilterAmountRange() {
        return filterAmountRange;
    }

    public Button getCalculateTotalButton() {
        return calculateTotalButton;
    }

    public Button getExportBillsButton() {
        return exportBillsButton;
    }

    public Button getPrintBillsButton() {
        return printBillsButton;
    }

    public Button getViewDetailsButton() {
        return viewDetailsButton;
    }

    public Label getTotalSalesLabel() {
        return totalSalesLabel;
    }
}
