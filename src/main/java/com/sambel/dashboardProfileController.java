package com.sambel;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class dashboardProfileController {

    @FXML
    private Button logout;

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

    @FXML
    private void handleLogout() throws IOException {
        UserSession.getInstance().clearSession();
        App.setRoot("login3");
    }
    
    public void initialize() {
        // Initialize your controller after the FXML has been loaded
       }
}
