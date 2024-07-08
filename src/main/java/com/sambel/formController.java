package com.sambel;

import java.io.IOException;

import javafx.fxml.FXML;

public class formController {

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Homepage1");
    }
    
    public void initialize() {
        // Initialize your controller after the FXML has been loaded
       }
}
