package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import model.Item;

public class AddItemController {

    private Manager manager;

    public AddItemController(Manager manager) {
        this.manager = manager;
        showAddItemView();
    }

    public void showAddItemView() {
        Stage addItemStage = new Stage();

        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();

        Label categoryLabel = new Label("Item Category:");
        TextField categoryField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label stockLabel = new Label("Stock Quantity:");
        TextField stockField = new TextField();

        Button saveButton = new Button("Save Item");
        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String category = categoryField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            // Logic to add item
            Item newItem = new Item(name, category, price, stock);
            manager.addNewItem(newItem);

            addItemStage.close(); // Close the add item window
        });

        VBox layout = new VBox(10, nameLabel, nameField, categoryLabel, categoryField, priceLabel, priceField, stockLabel, stockField, saveButton);

        Scene scene = new Scene(layout, 300, 300);
        addItemStage.setTitle("Add New Item");
        addItemStage.setScene(scene);
        addItemStage.show();
    }
}
