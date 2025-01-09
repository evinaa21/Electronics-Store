package controller;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import view.ViewItemsView;

public class ViewItemsController {

    private Manager manager;

    public ViewItemsController(Manager manager) {
        this.manager = manager;
    }

    public void showViewItemsView() {
        // Create the ViewItemsView and pass the Manager instance to it
        ViewItemsView viewItemsView = new ViewItemsView(manager);

        // Get the VBox content from the ViewItemsView
        VBox layout = viewItemsView.getViewContent();

        // Set up the scene and stage
        Scene scene = new Scene(layout, 600, 400);  // You can adjust the size as necessary
        Stage itemsStage = new Stage();
        itemsStage.setTitle("View Items");
        itemsStage.setScene(scene);
        itemsStage.show();
    }
}
