package com.sambel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.function.Consumer;

public class ExploreProjectCardController {
    @FXML private ImageView projectImage;
    @FXML private Label titleLabel;
    @FXML private ProgressBar fundingProgress;
    @FXML private Label fundingLabel;
    private Project project;
    private Consumer<Project> onClickListener;


    public void setProject(Project project) {
        this.project = project;
        updateUI();
    }

    private void updateUI() {
        titleLabel.setText(project.getTitle());

        double fundingPercentage = 0.5; // Replace with actual calculation
        fundingProgress.setProgress(fundingPercentage);
        
        String fundingText = String.format("IDR %.2f raised | %.0f%% funded", 
                                           50000.00, fundingPercentage * 100);
        fundingLabel.setText(fundingText);
    }

    public void setOnProjectClickListener(Consumer<Project> listener) {
        this.onClickListener = listener;
    }

    @FXML
    private void onCardClicked() {
        if (onClickListener != null) {
            onClickListener.accept(project);
        }
    }
}