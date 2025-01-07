package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import view.ViewSectorsView;

public class ViewSectorsController {

    private Stage primaryStage;
    private Manager manager; // Manager instance

    public ViewSectorsController(Stage primaryStage, Manager manager) {
        this.primaryStage = primaryStage;
        this.manager = manager;
        setupUI();
    }

    private void setupUI() {
        VBox layout = new VBox(10);

        Button viewSectorsButton = new Button("View Sectors");
        Button addSectorButton = new Button("Add Sector");

        // Open the view to see sectors
        viewSectorsButton.setOnAction(e -> openViewSectorsView());

        // Show dialog to add a new sector
        addSectorButton.setOnAction(e -> showAddSectorDialog());

        layout.getChildren().addAll(viewSectorsButton, addSectorButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Open the ViewSectorsView to display existing sectors
    private void openViewSectorsView() {
        // Create an instance of ViewSectorsView with the manager
        ViewSectorsView viewSectorsView = new ViewSectorsView(manager);
        viewSectorsView.showViewSectorsView(); // Show the sectors view
    }

    // Show a dialog to add a new sector
    private void showAddSectorDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Sector");
        dialog.setHeaderText("Enter new sector name:");
        dialog.setContentText("Sector Name:");

        dialog.showAndWait().ifPresent(sectorName -> {
            if (!sectorName.isEmpty()) {
                // Add the sector to the manager and the UI list
                manager.addSector(sectorName);  // Assuming manager has addSector method
                System.out.println("Sector added: " + sectorName); // Log for confirmation
            } else {
                // Optionally, handle case where the user input is empty
                System.out.println("Sector name cannot be empty.");
            }
        });
    }
}



