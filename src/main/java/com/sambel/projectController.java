package com.sambel;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class projectController {
    
    @FXML
    private BorderPane contentArea;

    @FXML
    private ProjectDetailsController contentAreaController;

    @FXML
    private static Project currentProject;

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

    // @FXML
    // private void switchToRewardPack() throws IOException {
    //     App.setRoot("rewardPack");
    // }

    @FXML
    private void switchToFormulir() throws IOException {
        App.setRoot("formulir");
    }

    // @FXML
    // private void switchToDonation() throws IOException {
    //     App.setRoot("donation");
    // }

    public static void setCurrentProject(Project project) {
        currentProject = project;
    }

    @FXML
    public void initialize() {
        if (currentProject != null) {
            loadProjectDetails(currentProject);
        }
        // Initialize your controller after the FXML has been loaded
       }
    
    public void switchToProjectDetails(Project project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectDetails.fxml"));
            Parent projectDetailsNode = loader.load();
            ProjectDetailsController controller = loader.getController();
            controller.setProject(project);
            
            contentArea.setCenter(projectDetailsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProjectDetails(Project project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectDetails.fxml"));
            Parent projectDetailsNode = loader.load();
            ProjectDetailsController controller = loader.getController();
            controller.setProject(project);
            
            contentArea.setCenter(projectDetailsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
