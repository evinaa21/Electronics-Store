package view;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import model.Manager;
import controller.ManagerController;

public class ViewItemsView {
    
    private Manager manager; // Instance of Manager

    // Constructor accepts Manager instance
    public ViewItemsView(Manager manager) {
        this.manager = manager;
    }

    public void showViewItemsView() {
        Stage viewItemsStage = new Stage(); // New stage for viewing items

        ListView<String> itemsListView = new ListView<>();

        // Get items from Manager instance
        for (Item item : manager.getItems()) {
            itemsListView.getItems().add(item.toString()); // Display item details
        }

        // Add a "Back" button to return to the manager dashboard
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBackToManagerDashboard(viewItemsStage));

        VBox layout = new VBox(10, itemsListView, backButton);
        Scene scene = new Scene(layout, 300, 300);
        viewItemsStage.setTitle("View Items");
        viewItemsStage.setScene(scene);
        viewItemsStage.show();
    }

    // Method to go back to the Manager Dashboard
    private void goBackToManagerDashboard(Stage viewItemsStage) {
        // Close the current ViewItems view
        viewItemsStage.close();

        // Show the Manager Dashboard again
        ManagerController managerController = new ManagerController(new Stage(), manager);
        Scene managerScene = managerController.getManagerScene();

        Stage managerStage = new Stage();
        managerStage.setTitle("Manager Dashboard");
        managerStage.setScene(managerScene);
        managerStage.show();
    }
}

