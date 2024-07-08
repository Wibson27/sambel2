package com.sambel;

import java.io.IOException;

import javafx.fxml.FXML;

public class dashboardMessagesController {

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
       }
}
