package controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Manager;
import model.Sector;
import model.Supplier;
import model.Item;
import util.FileHandlerMANAGER;
import view.*;

import java.util.ArrayList;

public class ManagerController {
    private Stage primaryStage;
    private Manager manager;
    private FileHandlerMANAGER fileHandler;
    private BorderPane mainLayout;
    private StackPane centerContent;
    private Scene managerScene;

    public ManagerController(Stage primaryStage, Manager manager) {
        this.primaryStage = primaryStage;
        this.manager = manager;
        this.fileHandler = new FileHandlerMANAGER();
        this.mainLayout = new BorderPane();
        this.centerContent = new StackPane();
        loadDataFromFiles();
        setupUI();
    }

    private void loadDataFromFiles() {
        ArrayList<Item> items = fileHandler.loadInventory();
        manager.setItems(items);

        ArrayList<Supplier> suppliers = fileHandler.loadSuppliers();
        manager.setSuppliers(suppliers);

        ArrayList<Sector> loadedSectors = fileHandler.loadManagerSectors();
        
        // Assuming 'manager' is a reference to the Manager object and has a setSectors() method
        manager.setSectors(loadedSectors);
    }

    private void setupUI() {
        ManagerView managerView = new ManagerView(this, primaryStage, manager, fileHandler);
        managerView.setupUI(mainLayout, centerContent);
        managerScene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Manager Dashboard");
        primaryStage.setScene(managerScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void openHomePage() {
        ManagerView managerView = new ManagerView(this, primaryStage, manager, fileHandler);
        managerView.showHomePage();
    }

    public void openAddItemView() {
        AddItemView addItemView = new AddItemView(manager, null);
        addItemView.setFileHandler(fileHandler);
        updateCenterContent(addItemView.getViewContent());
    }

    public void openRestockItemView() {
        RestockItemView restockItemView = new RestockItemView(manager, fileHandler);
        updateCenterContent(restockItemView.getViewContent());
    }

    public void openGenerateReportView() {
        GenerateReportView generateReportView = new GenerateReportView(manager, fileHandler);
        updateCenterContent(generateReportView.getViewContent());
    }

    public void openSupplierView() {
        SupplierView supplierView = new SupplierView(manager, fileHandler);
        updateCenterContent(supplierView.getViewContent());
    }

    public void openMonitorCashierPerformanceView() {
        MonitorCashierPerformanceView monitorCashierPerformanceView = new MonitorCashierPerformanceView(manager, fileHandler);
        updateCenterContent(monitorCashierPerformanceView.getViewContent());
    }

    public void openViewSectorsView() {
        ViewSectorsView sectorsView = new ViewSectorsView(manager);
        updateCenterContent(sectorsView.getSceneContent());
    }

    public void openViewItemsView() {
        ViewItemsController viewItemsController = new ViewItemsController(manager, fileHandler);
        VBox containerLayout = new VBox();
        viewItemsController.showViewItemsView(containerLayout);
        updateCenterContent(containerLayout);
    }

    private void updateCenterContent(Node content) {
        centerContent.getChildren().clear();
        centerContent.getChildren().add(content);
    }

    public int getLowStockItemsCount() {
        // Get the list of sectors the manager is responsible for
        ArrayList<Sector> managerSectors = manager.getSectors();

        // Use fileHandler to get low stock items filtered by manager's sectors
        ArrayList<Item> lowStockItems = fileHandler.notifyLowStockforManager(5, managerSectors);
        
        return lowStockItems.size();
    }


    public Scene getManagerScene() {
        return managerScene;
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }

    public StackPane getCenterContent() {
        return centerContent;
    }
}
