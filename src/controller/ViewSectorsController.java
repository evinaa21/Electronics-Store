// Controller: ViewSectorsController
package controller;

import java.util.ArrayList;
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
        ViewSectorsView view = new ViewSectorsView(manager);
        view.setFileHandler(fileHandler);
        view.setController(this);
        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.show();
    }

    public void addSector(String sectorName) {
        if (!sectorName.isEmpty()) {
            boolean sectorExists = manager.getSectors().stream()
                    .anyMatch(sector -> sector.getName().equalsIgnoreCase(sectorName));

            if (!sectorExists) {
                Sector newSector = new Sector(sectorName);
                manager.addSector(newSector);
                fileHandler.saveSectors(manager.getSectors());
            } else {
                System.out.println("Sector already exists: " + sectorName);
            }
        } else {
            System.out.println("Sector name cannot be empty.");
        }
    }

    public void addCategory(String sectorName, String categoryName) {
        if (!categoryName.isEmpty()) {
            Sector sector = manager.getSectors().stream()
                    .filter(s -> s.getName().equals(sectorName))
                    .findFirst()
                    .orElse(null);

            if (sector != null) {
                sector.addCategory(categoryName);
                fileHandler.saveSectors(manager.getSectors());
            } else {
                System.out.println("Sector not found: " + sectorName);
            }
        } else {
            System.out.println("Category name cannot be empty.");
        }
    }
}
