package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import model.Item;
import model.Manager;
import model.Supplier;
import util.FileHandlerMANAGER;
import java.util.ArrayList;


public class AddItemView {
    private Manager manager;
    private VBox parentLayout;


    private FileHandlerMANAGER fileHandler;
    public AddItemView(Manager manager, SupplierView supplierView) {
        this.manager = manager;
        this.parentLayout = new VBox();
    }

    public ScrollPane getViewContent() {
        
        Label titleLabel = new Label("Add New Item");
        titleLabel.setFont(new Font("Arial", 28));
        titleLabel.setTextFill(Color.WHITE);

        parentLayout.setStyle("-fx-background-color: #2C3E50;");
        parentLayout.setSpacing(20);
        parentLayout.setAlignment(Pos.CENTER);

        VBox formContainer = new VBox(20);
        formContainer.setStyle("-fx-background-color: #34495E; -fx-padding: 30; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 5, 0, 0, 2);");
        formContainer.setPrefWidth(0.75 * parentLayout.getWidth());  
        formContainer.setMinWidth(500); 

        
        Label formTitle = new Label("Enter Item Details");
        formTitle.setFont(new Font("Arial", 22));
        formTitle.setTextFill(Color.WHITE);

        GridPane formGrid = new GridPane();
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setMaxWidth(Double.MAX_VALUE);

        
        Label nameLabel = new Label("Item Name:");
        nameLabel.setTextFill(Color.WHITE);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter item name");
        nameField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);

        Label categoryLabel = new Label("Item Category:");
        categoryLabel.setTextFill(Color.WHITE);
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter item category");
        categoryField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(categoryLabel, 0, 1);
        formGrid.add(categoryField, 1, 1);

        Label priceLabel = new Label("Price:");
        priceLabel.setTextFill(Color.WHITE);
        TextField priceField = new TextField();
        priceField.setPromptText("Enter price");
        priceField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(priceLabel, 0, 2);
        formGrid.add(priceField, 1, 2);

        Label stockLabel = new Label("Stock Quantity:");
        stockLabel.setTextFill(Color.WHITE);
        TextField stockField = new TextField();
        stockField.setPromptText("Enter stock quantity");
        stockField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(stockLabel, 0, 3);
        formGrid.add(stockField, 1, 3);
        Label descriptionLabel = new Label("Item Description:");
        descriptionLabel.setTextFill(Color.WHITE);
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter item description");
        descriptionField.setWrapText(true);
        descriptionField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(descriptionLabel, 0, 4);
        formGrid.add(descriptionField, 1, 4);

        Label supplierLabel = new Label("Select Supplier:");
        supplierLabel.setTextFill(Color.WHITE);
        ComboBox<String> supplierComboBox = new ComboBox<>();
        supplierComboBox.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        loadSuppliersIntoComboBox(supplierComboBox);
        formGrid.add(supplierLabel, 0, 5);
        formGrid.add(supplierComboBox, 1, 5);

        Label imageLabel = new Label("Item Image:");
        imageLabel.setTextFill(Color.WHITE);
        Button chooseImageButton = new Button("Choose Image");
        chooseImageButton.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-border-radius: 5;");
        ImageView imageView = new ImageView();
        chooseImageButton.setOnAction(event -> chooseImage(imageView));

        HBox imageBox = new HBox(10, imageLabel, chooseImageButton, imageView);
        imageBox.setAlignment(Pos.CENTER_LEFT);
        formGrid.add(imageBox, 0, 6, 2, 1);

        Button saveButton = new Button("Save Item");
        saveButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-padding: 12px 20px; -fx-border-radius: 5;");
        saveButton.setFont(new Font("Arial", 14));

        HBox buttonBox = new HBox(20, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String sector = categoryField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            String category = categoryField.getText();
            String description = descriptionField.getText();
            String supplierName = supplierComboBox.getValue();
            String imagePath = imageView.getImage() != null ? imageView.getImage().getUrl() : "";

            if (name.isEmpty() || sector.isEmpty() || priceText.isEmpty() || stockText.isEmpty() ||
                category.isEmpty() || description.isEmpty() || supplierName == null) {
                showError("All fields are required, including supplier selection!");
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);

                Item newItem = new Item(name, sector, price, stock, category, description, supplierName, imagePath);

                fileHandler.addNewItem(newItem);

                ArrayList<Supplier> suppliers = fileHandler.loadSuppliers(); 
                for (Supplier supplier : suppliers) {
                    if (supplier.getSupplierName().equals(supplierName)) {
                        supplier.getSuppliedItems().add(newItem); 
                    }
                }

                fileHandler.saveSuppliers(suppliers);

                ArrayList<Item> savedItems = fileHandler.loadInventory();
                if (savedItems.contains(newItem)) {
                    showSuccess("Item added successfully!");
                } else {
                    showError("Failed to save the item.");
                }

            } catch (NumberFormatException e) {
                showError("Price and Stock must be numeric!");
            } catch (Exception e) {
                showError("An error occurred while saving the item: " + e.getMessage());
            }
        });

     
        formContainer.getChildren().addAll(formTitle, formGrid, buttonBox);
        parentLayout.getChildren().addAll(titleLabel, formContainer);
        parentLayout.setPadding(new Insets(30));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(parentLayout);
        scrollPane.setFitToWidth(true);  
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrolling
        scrollPane.setFitToHeight(true); // Ensure content fits the height of the ScrollPane

        // Add a listener to handle window resizing dynamically
        scrollPane.widthProperty().addListener((obs, oldValue, newValue) -> {
            parentLayout.setPrefWidth(newValue.doubleValue());
            formContainer.setPrefWidth(newValue.doubleValue());
        });

        return scrollPane;
    }

    private void loadSuppliersIntoComboBox(ComboBox<String> supplierComboBox) {
        ArrayList<String> suppliers = manager.getSupplierNames();
        if (suppliers != null && !suppliers.isEmpty()) {
            supplierComboBox.getItems().addAll(suppliers);
        } else {
            supplierComboBox.setPromptText("No suppliers available");
        }
    }

    private void chooseImage(ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        var file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imageView.setImage(new Image(file.toURI().toString()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
        }
    }

    // Helper method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show success messages
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	public void setFileHandler(FileHandlerMANAGER fileHandler) {
		this.fileHandler=fileHandler;
		
	}

}

