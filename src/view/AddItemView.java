package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Item;
import model.Manager;

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
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Create the form using GridPane to align fields and labels
        GridPane formGrid = new GridPane();
        formGrid.setVgap(15); // Vertical gap between rows
        formGrid.setHgap(10); // Horizontal gap between columns
        formGrid.setAlignment(Pos.CENTER);

        // Item Name
        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();
        styleTextField(nameField);
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);

        // Item Sector (formerly category)
        Label sectorLabel = new Label("Item Sector:");
        TextField sectorField = new TextField();
        styleTextField(sectorField);
        formGrid.add(sectorLabel, 0, 1);
        formGrid.add(sectorField, 1, 1);

        // Item Price
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        styleTextField(priceField);
        formGrid.add(priceLabel, 0, 2);
        formGrid.add(priceField, 1, 2);

        // Stock Quantity
        Label stockLabel = new Label("Stock Quantity:");
        TextField stockField = new TextField();
        styleTextField(stockField);
        formGrid.add(stockLabel, 0, 3);
        formGrid.add(stockField, 1, 3);

        // Item Image
        Label imageLabel = new Label("Item Image:");
        Button chooseImageButton = new Button("Choose Image");
        ImageView imageView = new ImageView();
        chooseImageButton.setStyle("-fx-background-color: linear-gradient(to bottom, #6a89cc, #4a69bd); -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        chooseImageButton.setOnAction(event -> chooseImage(imageView));

        // Image row (image label, button, and image view)
        HBox imageBox = new HBox(10, imageLabel, chooseImageButton, imageView);
        imageBox.setAlignment(Pos.CENTER_LEFT);
        formGrid.add(imageBox, 0, 4, 2, 1); // Span both columns for the image section

        // Buttons
        Button saveButton = createStyledButton("Save Item");
        Button backButton = createStyledButton("Back");

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
        VBox root = new VBox(20, titleLabel, formGrid, buttonBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 500); // Adjusted window size for a more spacious look
        addItemStage.setTitle("Add New Item");
        addItemStage.setScene(scene);
        addItemStage.show();
    }

    // Utility to create input rows (label + text field) and style them
    private void styleTextField(TextField textField) {
        textField.setStyle("-fx-border-radius: 10px; -fx-padding: 5px; -fx-font-size: 14px;");
    }

    // Method to create styled buttons
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #6a89cc, #4a69bd); " +
                        "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;" +
                        "-fx-padding: 10px 20px; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        button.setPrefWidth(180);
        return button;
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
