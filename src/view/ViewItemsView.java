package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Item;
import model.Manager;

public class ViewItemsView {
    private Manager manager;

    public ViewItemsView(Manager manager) {
        this.manager = manager;
    }

    public VBox getViewContent() {
        // Create a TextField for search
        Label searchLabel = new Label("Search Items:");
        searchLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter item name...");
        styleTextField(searchField);

        // Create a FlowPane for displaying items
        FlowPane itemsFlowPane = new FlowPane();
        itemsFlowPane.setHgap(20);
        itemsFlowPane.setVgap(20);
        itemsFlowPane.setAlignment(Pos.TOP_LEFT);

        // Add action listener for search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateFilters(searchField, itemsFlowPane));
        
     // Reset Filters Button
        Button resetButton = new Button("Reset Filters");
        styleResetButton(resetButton); // Apply the new dark blue style
        resetButton.setOnAction(e -> {
            searchField.clear();
            displayItems(itemsFlowPane, "");
        });

        // Set the layout
        HBox searchBox = new HBox(15, searchLabel, searchField);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        // Main layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
        layout.getChildren().addAll(searchBox, resetButton, itemsFlowPane);

        // Display the items initially
        displayItems(itemsFlowPane, "");

        return layout;
    }

    private void displayItems(FlowPane flowPane, String searchQuery) {
        flowPane.getChildren().clear();
        for (Item item : manager.getItems()) {
            boolean searchMatches = searchQuery.isEmpty() || item.getItemName().toLowerCase().contains(searchQuery.toLowerCase());

            if (searchMatches) {
                displayItem(flowPane, item);
            }
        }
    }

    private void displayItem(FlowPane flowPane, Item item) {
        VBox itemBox = new VBox(10);
        itemBox.setAlignment(Pos.CENTER);
        itemBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-padding: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 5);");
        itemBox.setPrefWidth(180);

        ImageView itemImage = new ImageView(item.getImage());
        itemImage.setFitWidth(150);
        itemImage.setFitHeight(150);
        itemImage.setPreserveRatio(true);
        itemImage.setSmooth(true);

        Label itemName = new Label(item.getItemName());
        itemName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label itemPrice = new Label("$" + item.getPrice());
        itemPrice.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

        Label itemStock = new Label("Stock: " + item.getItemQuantity());
        itemStock.setStyle("-fx-font-size: 14px; -fx-text-fill: #999999;");

        itemBox.getChildren().addAll(itemImage, itemName, itemPrice, itemStock);
        flowPane.getChildren().add(itemBox);

        // Set item click action to open details page
        itemBox.setOnMouseClicked(event -> openItemDetailsPage(item));
    }

    private void updateFilters(TextField searchField, FlowPane itemsFlowPane) {
        String searchQuery = searchField.getText();
        displayItems(itemsFlowPane, searchQuery);
    }
 // Styling method for the reset button
    private void styleResetButton(Button button) {
        button.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 10px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #002244; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 10px;"));
    }

    private void openItemDetailsPage(Item item) {
        // Create a new stage for the item details
        Stage itemStage = new Stage();
        itemStage.setTitle(item.getItemName() + " - Details");

        // Layout for item details
        VBox itemDetailsLayout = new VBox(20);
        itemDetailsLayout.setAlignment(Pos.CENTER);
        itemDetailsLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");

        ImageView itemImage = new ImageView(item.getImage());
        itemImage.setFitWidth(250);
        itemImage.setFitHeight(250);
        itemImage.setPreserveRatio(true);
        itemImage.setSmooth(true);

        Label itemName = new Label(item.getItemName());
        itemName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label itemDescription = new Label(item.getDescription());
        itemDescription.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666; -fx-wrap-text: true;");
        itemDescription.setMaxWidth(400);

        Label itemPrice = new Label("Price: $" + item.getPrice());
        itemPrice.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");

        Label itemStock = new Label("Stock: " + item.getItemQuantity());
        itemStock.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");

        Button closeButton = new Button("Close");
        styleButton(closeButton);
        closeButton.setOnAction(e -> itemStage.close());

        itemDetailsLayout.getChildren().addAll(itemImage, itemName, itemDescription, itemPrice, itemStock, closeButton);

        Scene itemScene = new Scene(itemDetailsLayout, 500, 600);
        itemStage.setScene(itemScene);
        itemStage.show();
    }

    // Styling methods for consistency
    private void styleTextField(TextField textField) {
        textField.setStyle("-fx-font-size: 14px; -fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-padding: 10px; -fx-border-color: #cccccc;");
        textField.setPrefWidth(300);
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 10px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 10px;"));
    }
}
