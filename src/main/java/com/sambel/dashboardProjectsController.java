package com.sambel;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class dashboardProjectsController {

    @FXML private VBox projectContainer;

    private void loadProjects() {
        User currentUser = UserSession.getInstance().getUser();
        List<Project> projects = currentUser.getProjects();

        projectContainer.getChildren().clear();
        for (Project project : projects) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectCard.fxml"));
                GridPane projectCard = loader.load();
                ProjectCardController controller = loader.getController();
                controller.setProject(project);
                
                // Make the GridPane fill the width of its container
                projectCard.prefWidthProperty().bind(projectContainer.widthProperty());
                
                projectContainer.getChildren().add(projectCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Homepage1");
    }

    @FXML
    private void switchToExplore() throws IOException {
        App.setRoot("explore");
    }

    @FXML
    private void switchToProfile() throws IOException {
        App.setRoot("dashboardProfile");
    }

    @FXML
    private void switchToMessages() throws IOException {
        App.setRoot("dashboardMessages");
    }

    @FXML
    private void switchToContributions() throws IOException {
        App.setRoot("dashboardContributions");
    }

    @FXML
    private void switchToProjects() throws IOException {
        App.setRoot("dashboardProjects");
    }

    @FXML
    private void switchToBadges() throws IOException {
        App.setRoot("dashboardBadges");
    }
    
    public void initialize() {
        // Initialize your controller after the FXML has been loaded
        loadProjects();
       }
}
