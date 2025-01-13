package controller;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import model.Sector;
import util.FileHandler;
import view.ViewSectorsView;

public class ViewSectorsController {

    private Stage primaryStage;
    private Manager manager;
    private FileHandler fileHandler;

    public ViewSectorsController(Stage primaryStage, Manager manager, FileHandler fileHandler) {
        this.primaryStage = primaryStage;
        this.manager = manager;
        this.fileHandler = fileHandler;
        loadSectors();

        setupUI();
    }

    private void loadSectors() {
        ArrayList<Sector> loadedSectors = fileHandler.loadSectors();
        manager.setSectors(loadedSectors);
    }

    private void setupUI() {
        VBox layout = new VBox(10);

        Button viewSectorsButton = new Button("View Sectors");
        Button addSectorButton = new Button("Add Sector");

        viewSectorsButton.setOnAction(e -> openViewSectorsView());
        addSectorButton.setOnAction(e -> showAddSectorDialog());

        layout.getChildren().addAll(viewSectorsButton, addSectorButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openViewSectorsView() {
        ViewSectorsView viewSectorsView = new ViewSectorsView(manager, fileHandler);
        VBox viewContent = viewSectorsView.getViewContent();
        Scene newScene = new Scene(viewContent, 400, 400);
        primaryStage.setScene(newScene);
    }

    private void showAddSectorDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Sector");
        dialog.setHeaderText("Enter new sector name:");
        dialog.setContentText("Sector Name:");

        dialog.showAndWait().ifPresent(sectorName -> {
            if (!sectorName.isEmpty()) {
                boolean sectorExists = false;
                for (Sector sector : manager.getSectors()) {
                    if (sector.getName().equalsIgnoreCase(sectorName)) {
                        sectorExists = true;
                        break;
                    }
                }

                if (!sectorExists) {
                    Sector newSector = new Sector(sectorName);
                    manager.addSector(newSector);

                    System.out.println("Sector added: " + sectorName);

                    fileHandler.saveSectors(manager.getSectors());
                } else {
                    System.out.println("Sector already exists: " + sectorName);
                }
            } else {
                System.out.println("Sector name cannot be empty.");
            }
        });
    }

    private void showAddCategoryDialog(Sector sector) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Enter new category name:");
        dialog.setContentText("Category Name:");

        dialog.showAndWait().ifPresent(categoryName -> {
            if (!categoryName.isEmpty()) {
                sector.addCategory(categoryName);

                System.out.println("Category added: " + categoryName);

                fileHandler.saveSectors(manager.getSectors());
            } else {
                System.out.println("Category name cannot be empty.");
            }
        });
    }
}
