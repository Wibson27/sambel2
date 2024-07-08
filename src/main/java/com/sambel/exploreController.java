package com.sambel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.util.List;

public class exploreController {

    @FXML private TilePane projectContainer;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private ProjectDetailsController contentAreaController;

    // public void onProjectSelected(Project selectedProject) {
    //     // Switch to the project details view if not already there
    //     // This step depends on how you're handling navigation
        
    //     // Update the project details
    //     contentAreaController.setProject(selectedProject);
    // }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Homepage1");
    }

    @FXML
    private void switchToProfile() throws IOException {
        App.setRoot("dashboardProfile");
    }

    @FXML
    private void switchToProject() throws IOException {
        App.setRoot("project");
    }

    @FXML
    public void initialize() {
        // Initialize your controller after the FXML has been loaded
        loadProjects();
       }
    
       private void loadProjects() {
        List<Project> projects = Project.getAllProjects();
        User currentUser = UserSession.getInstance().getUser();

        projectContainer.getChildren().clear();
        for (Project project : projects) {
            if (currentUser != null && project.getCreatorId() == currentUser.getId()) {
                continue;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ExploreProjectCard.fxml"));
                VBox projectCard = loader.load();
                ExploreProjectCardController controller = loader.getController();
                controller.setProject(project);
                controller.setOnProjectClickListener(this::onProjectSelected);
                projectContainer.getChildren().add(projectCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onProjectSelected(Project selectedProject) {
        projectController.setCurrentProject(selectedProject);
        try {
            App.setRoot("project");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
