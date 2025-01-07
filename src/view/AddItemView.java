
package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Item;
import model.Manager;
import model.Sector;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddItemView {

    private Manager manager;
    private Stage previousStage; // To store the reference to the previous stage

    // Constructor to pass the Manager instance and previous Stage reference
    public AddItemView(Manager manager, Stage previousStage) {
        this.manager = manager;
        this.previousStage = previousStage;
    }

    // Method to show the Add Item view
    public void showAddItemView() {
        Stage addItemStage = new Stage();

        // Create title label
        Label titleLabel = new Label("Add New Item");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        // Item Name
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();
        HBox nameBox = createInputRow(nameLabel, nameField);

        // Item Sector (formerly category)
        Label sectorLabel = new Label("Item Sector:");
        TextField sectorField = new TextField();
        HBox sectorBox = createInputRow(sectorLabel, sectorField);

        // Item Price
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        HBox priceBox = createInputRow(priceLabel, priceField);

        // Stock Quantity
        Label stockLabel = new Label("Stock Quantity:");
        TextField stockField = new TextField();
        HBox stockBox = createInputRow(stockLabel, stockField);

        // Item Image
        Label imageLabel = new Label("Item Image:");
        Button chooseImageButton = new Button("Choose Image");
        ImageView imageView = new ImageView();
        chooseImageButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        chooseImageButton.setOnAction(event -> chooseImage(imageView));
        HBox imageBox = new HBox(10, imageLabel, chooseImageButton, imageView);
        imageBox.setAlignment(Pos.CENTER_LEFT);

        // Buttons
        Button saveButton = new Button("Save Item");
        Button backButton = new Button("Back");

        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox buttonBox = new HBox(15, saveButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String sector = sectorField.getText().trim(); // Trim whitespace
            String priceText = priceField.getText();
            String stockText = stockField.getText();

            // Validate inputs
            if (name.isEmpty() || sector.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
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

            // Check if the sector exists
            boolean sectorExists = manager.getSectors().stream()
                    .anyMatch(s -> s != null && s.getName() != null && s.getName().equalsIgnoreCase(sector));

            if (!sectorExists) {
                showError("The sector does not exist. Please choose from the available sectors.");
                return;
            }

            // Create new item with entered details
            Item newItem = new Item(name, sector, price, stock);

            // If an image was selected, set it for the item
            if (imageView.getImage() != null) {
                newItem.setImage(imageView.getImage());
            }

            // Add the item using Manager
            manager.addNewItem(newItem);

            // Notify success and close the current window
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Item successfully added!");
            successAlert.showAndWait();

            addItemStage.close();
            previousStage.show();
        });


        // Back Button Action
        backButton.setOnAction(event -> {
            addItemStage.close();
            previousStage.show();
        });


        // Layout and scene
        VBox root = new VBox(20, titleLabel, nameBox, sectorBox, priceBox, stockBox, imageBox, buttonBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 400);
        addItemStage.setTitle("Add New Item");
        addItemStage.setScene(scene);
        addItemStage.show();
    }

    // Utility to create input rows (label + text field)
    private HBox createInputRow(Label label, TextField textField) {
        HBox box = new HBox(10, label, textField);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

 // Method to open file chooser and select image
    private void chooseImage(ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        var file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            
            // Set a specific width and height for the image
            imageView.setImage(image);
            imageView.setFitWidth(100);  // Set a fixed width for the image
            imageView.setFitHeight(100); // Set a fixed height for the image
            imageView.setPreserveRatio(true); // Maintain aspect ratio
        }
    }


    // Method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
