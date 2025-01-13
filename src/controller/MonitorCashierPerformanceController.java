package controller;

import javafx.stage.Stage;
import model.Manager;
import view.MonitorCashierPerformanceView;
import util.FileHandler;

public class MonitorCashierPerformanceController {

    private Manager manager;
    private FileHandler fileHandler;
    

    // Constructor to initialize the manager and fileHandler
    public MonitorCashierPerformanceController(Manager manager, FileHandler fileHandler) {
        if (manager == null || fileHandler == null) {
            throw new IllegalArgumentException("Manager and FileHandler cannot be null.");
        }
        this.manager = manager;
        this.fileHandler = fileHandler;
        showMonitorCashierView();
    }

    // Method to show the MonitorCashierView
    public void showMonitorCashierView() {
        // Create the view for monitoring cashier performance
        MonitorCashierPerformanceView monitorCashierView = new MonitorCashierPerformanceView(manager, fileHandler);
        
        // Create and display the stage for the view
        Stage monitorCashierStage = new Stage();
        monitorCashierStage.setTitle("Monitor Cashier Performance");
        monitorCashierStage.setScene(new javafx.scene.Scene(monitorCashierView.getViewContent(), 400, 300));
        monitorCashierStage.show();
    }
}
