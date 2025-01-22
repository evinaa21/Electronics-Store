package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Manager;
import view.MonitorCashierPerformanceView;
import util.FileHandlerMANAGER;

public class MonitorCashierPerformanceController {

    private Manager manager;
    private FileHandlerMANAGER fileHandler;
    
    public MonitorCashierPerformanceController(Manager manager, FileHandlerMANAGER fileHandler) {
        this.manager = manager;
        this.fileHandler = fileHandler;
        showMonitorCashierView();
    }

    public void showMonitorCashierView() {
        MonitorCashierPerformanceView monitorCashierView = new MonitorCashierPerformanceView(manager, fileHandler);
        
        Stage monitorCashierStage = new Stage();
        monitorCashierStage.setTitle("Monitor Cashier Performance");
        monitorCashierStage.setScene(new Scene(monitorCashierView.getViewContent(), 400, 300));
        monitorCashierStage.show();
    }
}
