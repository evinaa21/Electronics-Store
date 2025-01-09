package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import model.Sector;
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

    private void openViewSectorsView() {
        // Create an instance of ViewSectorsView with the manager
        ViewSectorsView viewSectorsView = new ViewSectorsView(manager); // No need to pass Stage anymore

        // Get the layout (VBox) from ViewSectorsView
        VBox viewContent = viewSectorsView.getViewContent(); // This method returns the layout (VBox)

        // Create a new Scene using the layout from ViewSectorsView
        Scene newScene = new Scene(viewContent, 400, 400);

        // Set the new scene in the primary stage
        primaryStage.setScene(newScene);
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
 // Show a dialog to add a new category
    private void showAddCategoryDialog(Sector sector) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Enter new category name:");
        dialog.setContentText("Category Name:");

        dialog.showAndWait().ifPresent(categoryName -> {
            if (!categoryName.isEmpty()) {
                // Add the category to the given sector
                sector.addCategory(categoryName);  // Assuming addCategory is a method in Sector class
                System.out.println("Category added: " + categoryName);  // Log for confirmation
            } else {
                // Optionally, handle case where the user input is empty
                System.out.println("Category name cannot be empty.");
            }
        });
    }

}


