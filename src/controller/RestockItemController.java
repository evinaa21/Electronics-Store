package controller;

import javafx.stage.Stage;
import model.Manager;
import view.RestockItemView;
import util.FileHandlerMANAGER;

public class RestockItemController {

    private Manager manager;
    private FileHandlerMANAGER fileHandler;

    public RestockItemController(Manager manager, FileHandlerMANAGER fileHandler) {
        if (manager == null || fileHandler == null) {
            throw new IllegalArgumentException("Manager and FileHandler cannot be null.");
        }
        this.manager = manager;
        this.fileHandler = fileHandler;
        showRestockItemView();
    }

    public void showRestockItemView() {
        // Create the view for restocking items
        RestockItemView restockItemView = new RestockItemView(manager, fileHandler);
        
        // Create and display the stage for the view
        Stage restockItemStage = new Stage();
        restockItemStage.setTitle("Restock Item");
        restockItemStage.setScene(new javafx.scene.Scene(restockItemView.getViewContent(), 400, 300));
        restockItemStage.show();
    }
}
