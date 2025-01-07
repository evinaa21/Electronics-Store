package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import model.Manager;

public class ViewItemsController {

    private Manager manager;

    public ViewItemsController(Manager manager) {
        this.manager = manager;
        showViewItemsView();
    }

    public void showViewItemsView() {
        Stage itemsStage = new Stage();

        // Display a list of items
        ListView<String> itemsList = new ListView<>();
        for (Item item : manager.getItems()) {
            itemsList.getItems().add(item.toString());
        }

        VBox layout = new VBox(10, itemsList);

        Scene scene = new Scene(layout, 300, 250);
        itemsStage.setTitle("View Items");
        itemsStage.setScene(scene);
        itemsStage.show();
    }
}
