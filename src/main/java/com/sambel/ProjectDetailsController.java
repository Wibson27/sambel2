package com.sambel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.NumberFormat;
import java.util.Locale;

public class ProjectDetailsController {

    @FXML private ImageView projectImage;
    @FXML private Label projectTitle;
    @FXML private Label creatorName;
    @FXML private Label fundingAmount;
    @FXML private ProgressBar fundingProgress;
    @FXML private Label fundingGoal;
    @FXML private Label donorsCount;
    @FXML private Label daysLeft;
    @FXML private TextArea storyText;
    @FXML private ListView<String> updatesList;
    @FXML private ListView<String> commentsList;

    private Project currentProject;
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @FXML
    private void initialize() {
        setupListViews();
    }

    private void setupListViews() {
        updatesList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setWrapText(true);
                    setPrefWidth(0);
                }
            }
        });

        commentsList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setWrapText(true);
                    setPrefWidth(0);
                }
            }
        });
    }

    public void setProject(Project project) {
        this.currentProject = project;
        updateUI();
    }

    private void updateUI() {
        if (currentProject != null) {
            projectTitle.setText(currentProject.getTitle());
            // projectImage.setImage(new Image(currentProject.getImagePath()));
            
            User creator = User.getById(currentProject.getCreatorId());
            creatorName.setText(creator.getUsername());

            double currentFunding = currentProject.getCurrentFunding();
            fundingAmount.setText(currencyFormatter.format(currentFunding));
            
            double fundingPercentage = currentFunding / currentProject.getFundingGoal();
            fundingProgress.setProgress(fundingPercentage);
            
            fundingGoal.setText(currencyFormatter.format(currentProject.getFundingGoal()) + " goal");
            
            int donors = currentProject.getDonorsCount();
            donorsCount.setText(donors + " Donor" + (donors != 1 ? "s" : ""));
            
            long daysUntilDeadline = ChronoUnit.DAYS.between(LocalDate.now(), currentProject.getFundingDeadline());
            daysLeft.setText(daysUntilDeadline + " days left");

            storyText.setText(currentProject.getDescription());
            
            updatesList.setItems(FXCollections.observableArrayList(currentProject.getUpdates()));
            commentsList.setItems(FXCollections.observableArrayList(currentProject.getComments()));
        }
    }

    @FXML
    private void showRewardPacks() {
        // Implement reward packs display logic
        System.out.println("Show reward packs");
        // TODO: Implement the logic to display reward packs
        // This could involve opening a new dialog or updating a part of the current view
    }

    @FXML
    private void donate() {
        User currentUser = UserSession.getInstance().getUser();
        if (currentUser != null) {
            // TODO: Implement donation logic
            // This could involve opening a donation dialog, processing the payment, etc.
            System.out.println("Processing donation for logged-in user: " + currentUser.getUsername());
            // After successful donation:
            // currentUser.donate(currentProject.getId(), amount, paymentMethod);
            // updateUI(); // Refresh the view to reflect new donation
        } else {
            // TODO: Show login prompt or navigate to login screen
            System.out.println("User needs to log in to donate");
            // You might want to open a login dialog or redirect to a login page
        }
    }
}