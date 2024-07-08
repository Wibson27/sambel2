package com.sambel;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class signUP2Controller {

    @FXML
    private HBox HBlabelLogin;

    @FXML
    private HBox HBloginBTN;

    @FXML
    private HBox HBloginPage;

    @FXML
    private HBox HBlogo;

    @FXML
    private HBox HBpfPassword;

    @FXML
    private HBox HBtfUsername;

    @FXML
    private HBox HBtfUsername1;

    @FXML
    private HBox HBtoSignup;

    @FXML
    private BorderPane Loginpage;

    @FXML
    private VBox VBloginPage;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelSignup;

    @FXML
    private Label logo;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button submitBTN;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfUsername;

    @FXML
    private Hyperlink toLoginBTN;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login3");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        // Additional validation (e.g., email format)
        if (!isValidEmail(email)) {
            showAlert("Validation Error", "Please enter a valid email address.");
            return;
        }

        // Register the user
        User.register(username, email, password);

        showAlert("Registration Successful", "User registered successfully.");

        try {
            switchToLogin();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not switch to login page. Please navigate manually.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    @FXML
    private void initialize() {
        // Initialize your controller after the FXML has been loaded
    }

}
