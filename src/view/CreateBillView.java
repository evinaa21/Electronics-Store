package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Sector;

public class CreateBillView {
    private final BorderPane mainLayout; //Main layout container
    private final VBox itemsContainer; //Container for displaying added items
    private final ComboBox<String> categoryDropdown; //Dropdown to select category
    private final ComboBox<String> itemDropdown; //Dropdown to select item
    private final TextField quantityField; //Input field for the quantity
    private final TextField totalField; //Field to display the current total
    private final Button addItemButton; //Button to add items to the bill
    private final Button finalizeBillButton; // Button to finalize and save the bill
   
    public CreateBillView() {
        mainLayout = new BorderPane(); //Initialize main layout
        mainLayout.setPadding(new Insets(10)); //Add padding to the layout

        //Input section at the top
        HBox inputBox = new HBox(10); //Horizontal layout for input fields
        inputBox.setAlignment(Pos.CENTER_LEFT); //Align elements to the left

        //Label and dropdown for category
        Label categoryLabel = new Label("Category:");
        categoryDropdown = new ComboBox<>();                                     
        categoryDropdown.setPromptText("Select Category");
        //set kategorite me dore

        //Label and dropdown for item
        Label itemLabel = new Label("Item:");
        itemDropdown = new ComboBox<>();
        itemDropdown.setPromptText("Select Item");

        //Label and text field for the quantity
        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");

        //Button to add items to the bill
        addItemButton = new Button("Add Item");
        addItemButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-weight: bold;");

        //Add all input elements to the input box
        inputBox.getChildren().addAll(categoryLabel, categoryDropdown, itemLabel, itemDropdown, quantityLabel, quantityField, addItemButton);

        //Container for displaying the list of items added to the bill
        itemsContainer = new VBox(10); //Vertical layout for items
        itemsContainer.setPadding(new Insets(10)); //Padding inside the container
        itemsContainer.setStyle("-fx-background-color: #f4f4f4;"); //Background color for visibility

        //Total and finalize section at the bottom
        HBox totalBox = new HBox(10); //Horizontal layout for total and button
        totalBox.setAlignment(Pos.CENTER_RIGHT); //Align elements to the right

        //Label and text field for the total amount
        Label totalLabel = new Label("Total:");
        totalField = new TextField("0.00"); //Default value for total
        totalField.setEditable(false); //Prevent user from editing the total

        //Button to finalize the bill
        finalizeBillButton = new Button("Finalize Bill");
        finalizeBillButton.setStyle("-fx-background-color: #28A745; -fx-text-fill: white; -fx-font-weight: bold;");
 
        //Add total elements to the total box
        totalBox.getChildren().addAll(totalLabel, totalField, finalizeBillButton);

        //Assemble main layout
        mainLayout.setTop(inputBox); //Input section at the top
        mainLayout.setCenter(itemsContainer); //Items list in the center
        mainLayout.setBottom(totalBox); //Total and finalize section at the bottom
    }

    //Getters for the main layout
    public BorderPane getViewContent() {
        return mainLayout;
    }

    public ComboBox<String> getCategoryDropdown() {
        return categoryDropdown;
    }

    public ComboBox<String> getItemDropdown() {
        return itemDropdown;
    }

    public TextField getQuantityField() {
        return quantityField;
    }

    public TextField getTotalField() {
        return totalField;
    }

    public Button getAddItemButton() {
        return addItemButton;
    }

    public Button getFinalizeBillButton() {
        return finalizeBillButton;
    }

    public VBox getItemsContainer() {
        return itemsContainer;
    }
    
    //Add an item to the items container
    public void addItemToDisplay(String itemInfo) {
    	Label itemLabel = new Label(itemInfo);
    	itemsContainer.getChildren().add(itemLabel);
    }
    
    //Update the total field
    public void updateTotal(double total) {
    	totalField.setText(String.format("%.2f", total));
    }
    
    //Reset all fields to their initial state
    public void resetView() {
        itemsContainer.getChildren().clear();
        totalField.setText("0.00");
        categoryDropdown.getSelectionModel().clearSelection();
        itemDropdown.getItems().clear();
        quantityField.clear();
    }
    
    //Validate input fields
    public boolean validateInputs() {
        boolean isValid = true;

        if (categoryDropdown.getValue() == null) {
            categoryDropdown.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            categoryDropdown.setStyle(null);
        }

        if (itemDropdown.getValue() == null) {
            itemDropdown.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            itemDropdown.setStyle(null);
        }

        if (quantityField.getText().isEmpty() || !quantityField.getText().matches("\\d+")) {
            quantityField.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            quantityField.setStyle(null);
        }

        return isValid;
    }
}
