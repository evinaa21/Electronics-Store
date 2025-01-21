package controller;

import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import model.Manager;
import util.FileHandlerMANAGER;
import view.ViewItemsView;

public class ViewItemsController {

    private Manager manager;
    private FileHandlerMANAGER fileHandler;
    private ViewItemsView viewItemsView;

    public ViewItemsController(Manager manager, FileHandlerMANAGER fileHandler) {
        this.manager = manager;
        this.fileHandler = fileHandler;
        this.viewItemsView = new ViewItemsView(manager, fileHandler);
    }

    public void showViewItemsView(VBox containerLayout) {
        // Initialize the necessary UI components
        TextField searchField = new TextField();
        ComboBox<String> filterComboBox = new ComboBox<>();
        ComboBox<String> sortComboBox = new ComboBox<>();
        FlowPane itemsFlowPane = new FlowPane();

        // Get the content from the ViewItemsView
        ScrollPane viewContent = viewItemsView.getViewContent();

        // Update the existing container layout with the new view content
        containerLayout.getChildren().clear();  // Clear any existing content
        containerLayout.getChildren().add(viewContent);  // Add the new view content
    }
}
