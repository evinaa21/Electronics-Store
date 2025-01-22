package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Manager;
import view.RestockItemView;
import util.FileHandlerMANAGER;

public class RestockItemController {

    private Manager manager;
    private FileHandlerMANAGER fileHandler;

    public RestockItemController(Manager manager, FileHandlerMANAGER fileHandler) {
        this.manager = manager;
        this.fileHandler = fileHandler;
        showRestockItemView();
    }
    public void showRestockItemView() {
         RestockItemView restockItemView = new RestockItemView(manager, fileHandler);
        
        Stage restockItemStage = new Stage();
        restockItemStage.setTitle("Restock Item");
        restockItemStage.setScene(new Scene(restockItemView.getViewContent(), 400, 300));
        restockItemStage.show();
    }
}
