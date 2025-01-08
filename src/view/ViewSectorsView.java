package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import model.Manager;
import model.Sector;
import java.util.ArrayList;

public class ViewSectorsView {
    private Manager manager; 
    private Stage previousStage; 
    private ArrayList<String> sectors; 

    public ViewSectorsView(Manager manager, Stage previousStage) {
        this.manager = manager;
        this.previousStage = previousStage;
        this.sectors = new ArrayList<>();
    }

    private void addSector(String sectorName) {
        if (sectorName == null || sectorName.trim().isEmpty()) {
            showError("Sector name cannot be empty.");
            return;
        }
        for (Sector sector : manager.getSectors()) {
            if (sector.getName().equalsIgnoreCase(sectorName)) {
                showError("Sector already exists: " + sectorName);
                return;
            }
        }
        Sector newSector = new Sector(sectorName);
        manager.getSectors().add(newSector);
        showSuccess("Sector added successfully: " + sectorName);
    }

    public void showViewSectorsView() {
        Stage sectorsStage = new Stage(); 
        sectors = manager.viewSector(); 

        ScrollPane scrollPane = new ScrollPane();
        VBox sectorsLayout = new VBox(15);
        scrollPane.setContent(sectorsLayout);
        scrollPane.setFitToWidth(true);

        for (String sector : sectors) {
            HBox sectorRow = new HBox(20);
            sectorRow.setAlignment(Pos.CENTER);
            Label sectorLabel = new Label(sector);
            styleSectorLabel(sectorLabel);
            sectorRow.getChildren().add(sectorLabel);
            sectorsLayout.getChildren().add(sectorRow);
        }

        Button addSectorButton = new Button("+ Add Sector");
        addSectorButton.setOnAction(e -> showAddSectorDialog(sectorsLayout));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBackToManagerDashboard(sectorsStage));

        HBox buttonsLayout = new HBox(10, addSectorButton, backButton);
        buttonsLayout.setAlignment(Pos.TOP_LEFT);

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(buttonsLayout, scrollPane);

        AnchorPane.setTopAnchor(buttonsLayout, 10.0);
        AnchorPane.setLeftAnchor(buttonsLayout, 10.0);

        AnchorPane.setTopAnchor(scrollPane, 50.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);

        Scene scene = new Scene(root, 400, 400);
        sectorsStage.setTitle("Item Sectors");
        sectorsStage.setScene(scene);
        sectorsStage.show();
    }

    private void showAddSectorDialog(VBox sectorsLayout) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Sector");
        dialog.setHeaderText("Enter the name of the new sector:");
        dialog.setContentText("Sector name:");

        dialog.showAndWait().ifPresent(sectorName -> {
            if (!sectorName.isEmpty()) {
                sectors.add(sectorName);  
                HBox newSectorRow = new HBox(20);
                newSectorRow.setAlignment(Pos.CENTER);  
                Label newSectorLabel = new Label(sectorName);
                styleSectorLabel(newSectorLabel); 
                newSectorRow.getChildren().add(newSectorLabel);
                sectorsLayout.getChildren().add(newSectorRow);
                manager.addSector(sectorName);
            }
        });
    }

    private void styleSectorLabel(Label label) {
        // Updated with Manager Dashboard colors (dark background, green buttons, etc.)
        label.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                     + "-fx-border-radius: 10px; -fx-padding: 10px; -fx-alignment: center;");

        label.setMaxWidth(Double.MAX_VALUE);
        label.setPrefHeight(40); 

        // Adding hover effect with the proper -fx-effect property
        label.setOnMouseEntered((MouseEvent e) -> {
            label.setEffect(new DropShadow(10, Color.BLACK)); // Subtle shadow effect
        });
        label.setOnMouseExited((MouseEvent e) -> {
            label.setEffect(null); // Remove shadow when mouse exits
        });
    }


    private void goBackToManagerDashboard(Stage sectorsStage) {
        sectorsStage.close();
        if (previousStage != null) {
            previousStage.show(); 
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); 
        alert.setContentText(message); 
        alert.showAndWait(); 
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null); 
        alert.setContentText(message); 
        alert.showAndWait(); 
    }
}

