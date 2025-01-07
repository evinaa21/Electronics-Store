package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Manager;

import java.util.List;

import controller.ManagerController;

public class ViewSectorsView {

    private Manager manager; // Instance variable

    // Constructor that accepts Manager instance
    public ViewSectorsView(Manager manager) {
        this.manager = manager;
    }

    // Method to display the sectors view
    public void showViewSectorsView() {
        Stage sectorsStage = new Stage();
        
        // Assume Manager has a method to view item sectors
        List<String> sectors = manager.viewSector();
        
        // Initialize ListView to display sectors
        ListView<String> sectorsList = new ListView<>();
        sectorsList.getItems().addAll(sectors); // Use real sectors from Manager

        // Create the "Add Sector" button with a plus symbol
        Button addSectorButton = new Button("+ Add Sector");

        // When the button is clicked, show a dialog to add a new sector
        addSectorButton.setOnAction(e -> showAddSectorDialog(sectorsList));

        // Create a "Back" button to return to the manager dashboard
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBackToManagerDashboard(sectorsStage));

        // Create a layout with the buttons and ListView
        VBox layout = new VBox(10, addSectorButton, sectorsList, backButton);
        
        Scene scene = new Scene(layout, 300, 250);
        sectorsStage.setTitle("Item Sectors");
        sectorsStage.setScene(scene);
        sectorsStage.show();
    }

    // Method to show a dialog to add a new sector
    private void showAddSectorDialog(ListView<String> sectorsList) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Sector");
        dialog.setHeaderText("Enter the name of the new sector:");
        dialog.setContentText("Sector name:");

        // Get the user's input and add the new sector
        dialog.showAndWait().ifPresent(sectorName -> {
            if (!sectorName.isEmpty()) {
                // Add the new sector to the list and update the Manager
                sectorsList.getItems().add(sectorName);
                manager.addSector(sectorName); // Assuming the Manager class has this method
            }
        });
    }
    
    // Method to go back to the Manager Dashboard
    private void goBackToManagerDashboard(Stage sectorsStage) {
        // Close the current ViewSectors window
        sectorsStage.close();

        // Show the Manager Dashboard again
        ManagerController managerController = new ManagerController(new Stage(), manager);
        Scene managerScene = managerController.getManagerScene();

        Stage managerStage = new Stage();
        managerStage.setTitle("Manager Dashboard");
        managerStage.setScene(managerScene);
        managerStage.show();
    }
}
