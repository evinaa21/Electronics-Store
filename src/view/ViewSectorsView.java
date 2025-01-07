package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Manager;
import model.Sector;
import java.util.ArrayList;

public class ViewSectorsView {
    private Manager manager; // Instance variable
    private Stage previousStage;  // Reference to the previous stage
    private ArrayList<String> sectors;  // ArrayList to store sectors dynamically

    // Constructor that accepts Manager instance and the previous Stage (Manager Dashboard)
    public ViewSectorsView(Manager manager, Stage previousStage) {
        this.manager = manager;
        this.previousStage = previousStage;
        this.sectors = new ArrayList<>(); // Initialize the ArrayList
    }

    // Method to add a new sector in ViewSector
    private void addSector(String sectorName) {
        // Check if the sector name is null or empty
        if (sectorName == null || sectorName.trim().isEmpty()) {
            showError("Sector name cannot be empty.");
            return;
        }
        // Check for duplicate sector in the manager's list
        for (Sector sector : manager.getSectors()) {
            if (sector.getName().equalsIgnoreCase(sectorName)) {
                showError("Sector already exists: " + sectorName);
                return;
            }
        }
        // Create a new Sector object and add it to the manager's list
        Sector newSector = new Sector(sectorName);
        manager.getSectors().add(newSector);
        // Notify the user of the successful addition
        showSuccess("Sector added successfully: " + sectorName);
    }

    // Method to display the sectors view
    public void showViewSectorsView() {
        Stage sectorsStage = new Stage(); // New stage for viewing sectors
        // Assume Manager has a method to view item sectors
        sectors = manager.viewSector();  // Get sectors from the manager (Assuming this returns an ArrayList)

        // Create ScrollPane for scrollable content
        ScrollPane scrollPane = new ScrollPane();
        VBox sectorsLayout = new VBox(10);  // Layout to contain all sector rows
        scrollPane.setContent(sectorsLayout);
        scrollPane.setFitToWidth(true); // Make sure the content fits the width of the ScrollPane

        // Loop through the sectors and create a row for each one
        for (String sector : sectors) {
            HBox sectorRow = new HBox(15);  // Horizontal layout for each sector row
            sectorRow.setAlignment(Pos.CENTER);  // Center align the sector row
            // Create a label for each sector with style
            Label sectorLabel = new Label(sector);
            styleSectorLabel(sectorLabel); // Apply button-like style to the label
            // Add sector label to the row (HBox)
            sectorRow.getChildren().add(sectorLabel);
            // Add the sector row to the main VBox layout
            sectorsLayout.getChildren().add(sectorRow);
        }

        // Create the "Add Sector" button with a plus symbol
        Button addSectorButton = new Button("+ Add Sector");
        // When the button is clicked, show a dialog to add a new sector
        addSectorButton.setOnAction(e -> showAddSectorDialog(sectorsLayout));

        // Create a "Back" button to return to the manager dashboard
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBackToManagerDashboard(sectorsStage));

        // Create a top-left buttons layout using HBox
        HBox buttonsLayout = new HBox(10, addSectorButton, backButton);
        buttonsLayout.setAlignment(Pos.TOP_LEFT);  // Align buttons to the top left
        // Set the buttons layout as the top part of the window using an AnchorPane
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(buttonsLayout, scrollPane);

        // Anchor buttons to the top-left corner
        AnchorPane.setTopAnchor(buttonsLayout, 10.0);  // Set distance from top edge
        AnchorPane.setLeftAnchor(buttonsLayout, 10.0);  // Set distance from left edge

        // Anchor ScrollPane to the rest of the space
        AnchorPane.setTopAnchor(scrollPane, 50.0);  // Push the scroll pane below the buttons
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);

        // Set up the scene and stage
        Scene scene = new Scene(root, 400, 400);
        sectorsStage.setTitle("Item Sectors");
        sectorsStage.setScene(scene);
        sectorsStage.show();
    }

    // Method to show a dialog to add a new sector
    private void showAddSectorDialog(VBox sectorsLayout) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Sector");
        dialog.setHeaderText("Enter the name of the new sector:");
        dialog.setContentText("Sector name:");

        // Get the user's input and add the new sector
        dialog.showAndWait().ifPresent(sectorName -> {
            if (!sectorName.isEmpty()) {
                // Add the new sector to the ArrayList and update the Manager
                sectors.add(sectorName);  // Add to the ArrayList

                // Add the new sector row to the layout
                HBox newSectorRow = new HBox(15);
                newSectorRow.setAlignment(Pos.CENTER);  // Center the new sector row
                Label newSectorLabel = new Label(sectorName);
                styleSectorLabel(newSectorLabel); // Apply style to new label
                newSectorRow.getChildren().add(newSectorLabel);
                sectorsLayout.getChildren().add(newSectorRow); // Add new sector row to layout

                manager.addSector(sectorName); // Assuming the Manager class has this method
            }
        });
    }

    // Method to style sector labels to make them look like buttons
    private void styleSectorLabel(Label label) {
        label.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; "
                    + "-fx-border-radius: 10px; -fx-padding: 10px; -fx-alignment: center;");
        label.setMaxWidth(Double.MAX_VALUE); // Set a maximum width if needed
        label.setPrefHeight(40);  // Set a height to make it look like a button
    }

    // Method to go back to the Manager Dashboard
    private void goBackToManagerDashboard(Stage sectorsStage) {
        // Close the current ViewSectors window
        sectorsStage.close();
        // Show the Manager Dashboard again
        if (previousStage != null) {
            previousStage.show(); // Show the previous (Manager Dashboard) window
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // No header text
        alert.setContentText(message); // Display the error message
        alert.showAndWait(); // Wait for the user to close the alert
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null); // No header text
        alert.setContentText(message); // Display the success message
        alert.showAndWait(); // Wait for the user to close the alert
    }
}

