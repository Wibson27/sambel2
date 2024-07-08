package com.sambel;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.time.LocalDate;

public class formulirController {
    
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField imagePath;
    @FXML private TextField fundingGoalField;
    @FXML private DatePicker fundingDeadlinePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Button createProjectButton;

    public void initialize() {
        System.out.println("FormulirController initialized");
        // Use Platform.runLater to ensure UI is fully loaded
        javafx.application.Platform.runLater(this::populateComboBox);
    }

    private void populateComboBox() {
        categoryComboBox.getItems().clear(); // Clear existing items if any
        categoryComboBox.getItems().addAll(
            "Waste Management", "Water Conservation", "Wildlife Conservation", "Energy Conservation", "Sustainable Living"
        );
        System.out.println("ComboBox populated with " + categoryComboBox.getItems().size() + " items");
    }

    @FXML
    private void handleCreateProject(ActionEvent event) throws IOException{
        User currentUser = UserSession.getInstance().getUser();
        if (currentUser == null) {
            showAlert("Error", "No user logged in. Please log in and try again.");
            return;
        }

        if (validateInputs()) {
            String title = titleField.getText();
            String description = descriptionArea.getText();
            String imagePathStr = imagePath.getText().isEmpty() ? null : imagePath.getText();
            double fundingGoal = Double.parseDouble(fundingGoalField.getText());
            LocalDate fundingDeadline = fundingDeadlinePicker.getValue();
            String category = categoryComboBox.getValue();

            Project newProject = currentUser.createProject(
                title, 
                description, 
                imagePathStr, 
                fundingGoal, 
                fundingDeadline.toString(),
                category
            );

            if (newProject != null) {
                showAlert("Success", "Project created successfully!");
                clearInputs();
            } else {
                showAlert("Error", "Failed to create project. Please try again.");
            }
        }

        
        switchToDashboardProject();
    }

    private boolean validateInputs() {
        if (titleField.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter a project title.");
            return false;
        }
        if (descriptionArea.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter a project description.");
            return false;
        }
        try {
            Double.parseDouble(fundingGoalField.getText());
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid funding goal.");
            return false;
        }
        if (fundingDeadlinePicker.getValue() == null) {
            showAlert("Validation Error", "Please select a funding deadline.");
            return false;
        }
        if (categoryComboBox.getValue() == null) {
            showAlert("Validation Error", "Please select a project category.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearInputs() {
        titleField.clear();
        descriptionArea.clear();
        imagePath.clear();
        fundingGoalField.clear();
        fundingDeadlinePicker.setValue(null);
        categoryComboBox.setValue(null);
    }

    @FXML
    private void switchToDashboardProject() throws IOException {
        App.setRoot("dashboardProjects");
    }
   
}
