package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import model.Item;

public class RestockItemController {

    private Manager manager;

    public RestockItemController(Manager manager) {
        this.manager = manager;
        showRestockItemView();
    }

    public void showRestockItemView() {
        Stage restockItemStage = new Stage();

        Label nameLabel = new Label("Item Name:");
        TextField nameField = new TextField();

        Label quantityLabel = new Label("Restock Quantity:");
        TextField quantityField = new TextField();

        Button restockButton = new Button("Restock Item");
        restockButton.setOnAction(event -> {
            String itemName = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            // Logic to restock item
            Item item = findItemByName(itemName);
            if (item != null) {
                item.restockItem(quantity);
                restockItemStage.close(); // Close the restock window
            } else {
                showError("Item not found");
            }
        });

        VBox layout = new VBox(10, nameLabel, nameField, quantityLabel, quantityField, restockButton);

        Scene scene = new Scene(layout, 300, 200);
        restockItemStage.setTitle("Restock Item");
        restockItemStage.setScene(scene);
        restockItemStage.show();
    }

    private Item findItemByName(String name) {
        for (Item item : manager.getItems()) {
            if (item.getItemName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
