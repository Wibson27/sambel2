package com.sambel;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class loginController {

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
    private HBox HBtoSignup;

    @FXML
    private BorderPane Loginpage;

    @FXML
    private Hyperlink Signup;

    @FXML
    private VBox VBloginPage;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelSignup;

    @FXML
    private Button loginBTN;

    @FXML
    private Label logo;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Please enter both email and password.");
            return;
        }

        // Attempt to log in the user
        User loggedInUser = User.login(email, password);

        if (loggedInUser != null) {
            UserSession.getInstance().setUser(loggedInUser);
            showAlert("Login Successful", "Welcome back, " + loggedInUser.getUsername() + "!");
            switchToHomePage();
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToHomePage() {
        try {
            App.setRoot("homePage1");
        } catch (IOException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
        }
    }

    @FXML
    void handleSwitchToRegistration(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        // Initialize your controller after the FXML has been loaded
    }

}
