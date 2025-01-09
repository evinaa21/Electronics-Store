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
import model.Category;
import model.Item;
import model.Manager;
import model.Sector;

public class AddItemView {
    private Manager manager;
    private VBox parentLayout;

    public AddItemView(Manager manager) {
        this.manager = manager;
        this.parentLayout = new VBox();
    }

    public VBox getViewContent() {
        // Main title
        Label titleLabel = new Label("Add New Item");
        titleLabel.setFont(new Font("Arial", 28));
        titleLabel.setTextFill(Color.WHITE);

        // Parent container with dark blue background
        parentLayout.setStyle("-fx-background-color: #2C3E50;");
        parentLayout.setSpacing(20);
        parentLayout.setAlignment(Pos.CENTER);

        // Form container with lighter blue background
        VBox formContainer = new VBox(20);
        formContainer.setStyle("-fx-background-color: #34495E; -fx-padding: 30; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 5, 0, 0, 2);");

        // Title inside the form container
        Label formTitle = new Label("Enter Item Details");
        formTitle.setFont(new Font("Arial", 22));
        formTitle.setTextFill(Color.WHITE);

        // Form Grid for input fields
        GridPane formGrid = new GridPane();
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        // Label and Input for Item Name
        Label nameLabel = new Label("Item Name:");
        nameLabel.setTextFill(Color.WHITE);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter item name");
        nameField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);

        // Label and Input for Item Category
        Label categoryLabel = new Label("Item Category:");
        categoryLabel.setTextFill(Color.WHITE);
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter item category");
        categoryField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(categoryLabel, 0, 1);
        formGrid.add(categoryField, 1, 1);

        // Label and Input for Item Price
        Label priceLabel = new Label("Price:");
        priceLabel.setTextFill(Color.WHITE);
        TextField priceField = new TextField();
        priceField.setPromptText("Enter price");
        priceField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(priceLabel, 0, 2);
        formGrid.add(priceField, 1, 2);

        // Label and Input for Stock Quantity
        Label stockLabel = new Label("Stock Quantity:");
        stockLabel.setTextFill(Color.WHITE);
        TextField stockField = new TextField();
        stockField.setPromptText("Enter stock quantity");
        stockField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(stockLabel, 0, 3);
        formGrid.add(stockField, 1, 3);

        // Item Description
        Label descriptionLabel = new Label("Item Description:");
        descriptionLabel.setTextFill(Color.WHITE);
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter item description");
        descriptionField.setWrapText(true);
        descriptionField.setStyle("-fx-border-radius: 5; -fx-border-color: #5D6D7E;");
        formGrid.add(descriptionLabel, 0, 4);
        formGrid.add(descriptionField, 1, 4);

        // Item Image
        Label imageLabel = new Label("Item Image:");
        imageLabel.setTextFill(Color.WHITE);
        Button chooseImageButton = new Button("Choose Image");
        chooseImageButton.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-border-radius: 5;");
        ImageView imageView = new ImageView();
        chooseImageButton.setOnAction(event -> chooseImage(imageView));

        HBox imageBox = new HBox(10, imageLabel, chooseImageButton, imageView);
        imageBox.setAlignment(Pos.CENTER_LEFT);
        formGrid.add(imageBox, 0, 5, 2, 1);

        // Save Button
        Button saveButton = new Button("Save Item");
        saveButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-padding: 12px 20px; -fx-border-radius: 5;");
        saveButton.setFont(new Font("Arial", 14));

        HBox buttonBox = new HBox(20, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Save button action handling
        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String category = categoryField.getText().trim();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            String description = descriptionField.getText(); 

            if (name.isEmpty() || category.isEmpty() || priceText.isEmpty() || stockText.isEmpty() || description.isEmpty()) {
                showError("All fields must be filled in.");
                return;
            }

            double price;
            int stock;

            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                showError("Price must be a valid number.");
                return;
            }

            try {
                stock = Integer.parseInt(stockText);
            } catch (NumberFormatException e) {
                showError("Stock must be a valid integer.");
                return;
            }

            boolean categoryExists = false;
            for (Sector sector : manager.getSectors()) {
                if (sector.getCategories().contains(category)) {
                    categoryExists = true;
                    break;
                }
            }

            if (!categoryExists) {
                showError("The category does not exist. Please choose from the available categories.");
                return;
            }

            Item newItem = new Item(name, category, price, stock);
            newItem.setDescription(description); 
            if (imageView.getImage() != null) {
                newItem.setImage(imageView.getImage());
            }

            manager.addNewItem(newItem);
            showSuccess("Item added successfully!");
        });

        // Add everything to the layout
        formContainer.getChildren().addAll(formTitle, formGrid, buttonBox);
        parentLayout.getChildren().addAll(titleLabel, formContainer);
        parentLayout.setPadding(new Insets(30));

        return parentLayout;
    }

    private void chooseImage(ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        var file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
