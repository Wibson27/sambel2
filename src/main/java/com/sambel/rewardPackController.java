package com.sambel;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class rewardPackController {

    @FXML
    private ChoiceBox<String> CBpayment;

    @FXML
    private HBox HBLogo;

    @FXML
    private HBox HBadrress;

    @FXML
    private HBox HBcity;

    @FXML
    private HBox HBcreator;

    @FXML
    private HBox HBjudulProject;

    @FXML
    private HBox HBname;

    @FXML
    private HBox HBpayReward;

    @FXML
    private HBox HBpayemnt;

    @FXML
    private HBox HBphone;

    @FXML
    private HBox HBpostal;

    @FXML
    private HBox HBproduct;

    @FXML
    private HBox HBship;

    @FXML
    private HBox HBstreet;

    @FXML
    private HBox HBsub;

    @FXML
    private HBox HBsubmit;

    @FXML
    private HBox HBsum;

    @FXML
    private HBox HBtotal;

    @FXML
    private ImageView IMVcreator;

    @FXML
    private ImageView IMVproduct;

    @FXML
    private Button SubmitBTN;

    @FXML
    private VBox VBLogo;

    @FXML
    private HBox VBcontribute;

    @FXML
    private VBox VBform;

    @FXML
    private VBox VBform2;

    @FXML
    private VBox VBpay;

    @FXML
    private VBox VBsum;

    @FXML
    private HBox hbSummary;

    @FXML
    private Button homeLogoBTN;

    @FXML
    private Label judulProject;

    @FXML
    private Label labehShipprice;

    @FXML
    private Label labelAddress;

    @FXML
    private Label labelContribute;

    @FXML
    private Label labelIdentitas;

    @FXML
    private Label labelPayment;

    @FXML
    private Label labelPriceQTY;

    @FXML
    private Label labelQTY;

    @FXML
    private Label labelShip;

    @FXML
    private Label labelSub;

    @FXML
    private Label labelSubprice;

    @FXML
    private Label labelSum;

    @FXML
    private Label labelTotal;

    @FXML
    private Label labelTotalprice;

    @FXML
    private Line linePayment;

    @FXML
    private Line lineQTY;

    @FXML
    private Line lineSum;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfPostal;

    @FXML
    private TextField tfStreet;

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Homepage1");
    }

    @FXML
    private void switchToProject() throws IOException {
        App.setRoot("popject");
    }

    @FXML
    private void initialize() {
        // Initialize your controller after the FXML has been loaded
        CBpayment.getItems().addAll("Mastercard", "Visa", "PayPal");
    }

     @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

}
