package com.sambel;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class homepageController {

    @FXML private Button userNameButton;

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Homepage1");
    }

    @FXML
    private void switchToExplore() throws IOException {
        App.setRoot("explore");
    }

    @FXML
    private void switchToFormulir() throws IOException {
        App.setRoot("formulir");
    }

    @FXML
    private void switchToProfile() throws IOException {
        App.setRoot("dashboardProfile");
    }

    @FXML
    private void switchToDonation() throws IOException {
        App.setRoot("donation");
    }
    
    public void initialize() {
        // Initialize your controller after the FXML has been loaded
        User currentUser = UserSession.getInstance().getUser();
        if (currentUser != null) {
            userNameButton.setText(currentUser.getUsername());
        } else {
            userNameButton.setText("Guest");
        }
       }
}
