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

import java.util.ArrayList;

public class ViewSectorsView {
    private Manager manager;
    private ArrayList<String> sectors;

    public ViewSectorsView(Manager manager) {
        this.manager = manager;
        this.sectors = new ArrayList<>();
    }

    public VBox getViewContent() {
        sectors = manager.viewSector(); // Get the sectors as strings
        ScrollPane scrollPane = new ScrollPane();
        VBox sectorsLayout = new VBox(15); // Spacing for a clean layout
        scrollPane.setContent(sectorsLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10px;");

        for (String sectorName : sectors) {
            VBox sectorBox = createSectorBox(sectorName);
            sectorsLayout.getChildren().add(sectorBox);
        }

        Button addSectorButton = new Button("+ Add Sector");
        styleButton(addSectorButton, true); // Full-size button styling
        addSectorButton.setOnAction(e -> showAddSectorDialog(sectorsLayout));

        HBox buttonsLayout = new HBox(10, addSectorButton);
        buttonsLayout.setAlignment(Pos.TOP_LEFT);

        VBox layout = new VBox(20, buttonsLayout, scrollPane);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #eaf3fc;");

        return layout;
    }

    private VBox createSectorBox(String sectorName) {
        VBox sectorBox = new VBox(5); // Vertical layout for sector and categories
        sectorBox.setPadding(new Insets(10));
        sectorBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #003366; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        HBox sectorRow = new HBox(10);
        sectorRow.setAlignment(Pos.CENTER_LEFT);

        Label sectorLabel = new Label(sectorName);
        sectorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        sectorLabel.setTextFill(Color.web("#003366"));

        HBox categoryLayout = new HBox(10);
        categoryLayout.setPadding(new Insets(10));
        categoryLayout.setVisible(false);

        // Add existing categories to the category layout
        for (Sector sector : manager.getSectors()) {
            if (sector.getName().equals(sectorName)) {
                for (String categoryName : sector.getCategories()) {
                    Label categoryLabel = new Label(categoryName);
                    styleCategoryLabel(categoryLabel);
                    categoryLayout.getChildren().add(categoryLabel);
                }
                break;
            }
        }

        Button addCategoryButton = new Button("+ Add Category");
        styleButton(addCategoryButton, false); // Small button styling
        addCategoryButton.setOnAction(e -> showAddCategoryDialog(sectorName, categoryLayout));

        // Add click functionality for dropdown effect
        sectorLabel.setOnMouseClicked(e -> toggleCategoryVisibility(categoryLayout));

        // Align sector name and add category button
        HBox.setHgrow(sectorLabel, Priority.ALWAYS);
        sectorRow.getChildren().addAll(sectorLabel, addCategoryButton);

        // Add the sector row and categories layout to the box
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
                sectors.add(sectorName);

                VBox newSectorBox = createSectorBox(sectorName);
                sectorsLayout.getChildren().add(newSectorBox);

                manager.addSector(sectorName); // Add the sector to the manager
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
                // Add the category to the model
                manager.addCategoryToSector(sectorName, categoryName);

                // Refresh category layout
                Label categoryLabel = new Label(categoryName);
                styleCategoryLabel(categoryLabel);
                categoryLayout.getChildren().add(categoryLabel);
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
}
