package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Manager;
import model.Sector;
import util.FileHandler;

import java.util.ArrayList;

public class ViewSectorsView {
    private Manager manager;

    private ArrayList<String> sectors;

    private FileHandler fileHandler;


    public ViewSectorsView(Manager manager, FileHandler fileHandler) {
        this.manager = manager;
        this.fileHandler = fileHandler;
        this.manager.setSectors(fileHandler.loadSectors());
        System.out.println("Sectors loaded from file: " + manager.getSectors());
    }

    public VBox getViewContent() {
        ArrayList<Sector> sectors = manager.viewSector();
        ScrollPane scrollPane = new ScrollPane();
        VBox sectorsLayout = new VBox(15);
        scrollPane.setContent(sectorsLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10px;");

        // Add existing sectors to the view
        for (Sector sector : sectors) {
            VBox sectorBox = createSectorBox(sector);
            sectorsLayout.getChildren().add(sectorBox);
        }

        Button addSectorButton = new Button("+ Add Sector");
        styleButton(addSectorButton, true);
        addSectorButton.setOnAction(e -> showAddSectorDialog(sectorsLayout));

        HBox buttonsLayout = new HBox(10, addSectorButton);
        buttonsLayout.setAlignment(Pos.TOP_LEFT);

        VBox layout = new VBox(20, buttonsLayout, scrollPane);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #eaf3fc;");

        return layout;
    }

    private VBox createSectorBox(Sector sector) {
        VBox sectorBox = new VBox(5);
        sectorBox.setPadding(new Insets(10));
        sectorBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #003366; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        HBox sectorRow = new HBox(10);
        sectorRow.setAlignment(Pos.CENTER_LEFT);

        Label sectorLabel = new Label(sector.getName());
        sectorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        sectorLabel.setTextFill(Color.web("#003366"));

        HBox categoryLayout = new HBox(10);
        categoryLayout.setPadding(new Insets(10));
        categoryLayout.setVisible(false);

        // Add existing categories to the category layout
        for (String categoryName : sector.getCategories()) {
            Label categoryLabel = new Label(categoryName);
            styleCategoryLabel(categoryLabel);
            categoryLayout.getChildren().add(categoryLabel);
        }

        Button addCategoryButton = new Button("+ Add Category");
        styleButton(addCategoryButton, false);
        addCategoryButton.setOnAction(e -> showAddCategoryDialog(sector.getName(), categoryLayout));

        // Add click functionality for dropdown effect
        sectorLabel.setOnMouseClicked(e -> toggleCategoryVisibility(categoryLayout));

        sectorRow.getChildren().addAll(sectorLabel, addCategoryButton);
        sectorBox.getChildren().addAll(sectorRow, categoryLayout);

        return sectorBox;
    }

    private void toggleCategoryVisibility(HBox categoryLayout) {
        categoryLayout.setVisible(!categoryLayout.isVisible());
    }

    private void showAddSectorDialog(VBox sectorsLayout) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Sector");
        dialog.setHeaderText("Enter the name of the new sector:");
        dialog.setContentText("Sector name:");

        dialog.showAndWait().ifPresent(sectorName -> {
            if (!sectorName.isEmpty()) {
                // Create a new sector object and add it to the manager
                Sector newSector = new Sector(sectorName);
                manager.addSector(newSector);

                // Refresh sector view after adding new sector
                VBox newSectorBox = createSectorBox(newSector);
                sectorsLayout.getChildren().add(newSectorBox);

                // Save the sectors to file
                fileHandler.saveSectors(manager.getSectors());

                System.out.println("Sector added: " + sectorName);
            } else {
                System.out.println("Sector name cannot be empty.");
            }
        });
    }

    private void showAddCategoryDialog(String sectorName, HBox categoryLayout) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Enter the name of the new category:");
        dialog.setContentText("Category name:");

        dialog.showAndWait().ifPresent(categoryName -> {
            if (!categoryName.isEmpty()) {
                manager.addCategoryToSector(sectorName, categoryName);

                Label categoryLabel = new Label(categoryName);
                styleCategoryLabel(categoryLabel);
                categoryLayout.getChildren().add(categoryLabel);
                fileHandler.saveSectors(manager.getSectors());
            }
        });
    }

    private void styleButton(Button button, boolean isFullSize) {
        if (isFullSize) {
            button.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: 10px;");
        } else {
            button.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: 5px 10px;");
        }
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #002244; -fx-text-fill: white; -fx-font-size: " + (isFullSize ? "14px" : "12px") + "; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: " + (isFullSize ? "10px" : "5px 10px") + ";"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #003366; -fx-text-fill: white; -fx-font-size: " + (isFullSize ? "14px" : "12px") + "; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-padding: " + (isFullSize ? "10px" : "5px 10px") + ";"));
    }

    private void styleCategoryLabel(Label label) {
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        label.setTextFill(Color.web("#333333"));
        label.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 5px 10px; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-background-radius: 5px;");
    }


    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

}
