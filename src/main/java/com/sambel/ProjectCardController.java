package com.sambel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProjectCardController {
    @FXML private ImageView projectImage;
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label fundingLabel;
    @FXML private ProgressBar fundingProgress;
    @FXML private Label deadlineLabel;

    public void setProject(Project project) {
        titleLabel.setText(project.getTitle());
        descriptionLabel.setText(project.getDescription());
        
        String fundingGoalFormatted = String.format("$%.2f", project.getFundingGoal());
        fundingLabel.setText(fundingGoalFormatted + " goal");
        
        fundingProgress.setProgress(0); // Set to 0 for now, update this when you implement funding tracking
        
        LocalDate deadline = project.getFundingDeadline();
        String deadlineFormatted = deadline.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        deadlineLabel.setText("Deadline: " + deadlineFormatted);
    }
}
