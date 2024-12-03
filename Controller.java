package com.example.samtransbusstopdisplay;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.List;

public class Controller {
	@FXML
	private Label firstMinLabel;
	@FXML
	private Label secondMinLabel;
	@FXML
	private Label thirdMinLabel;
	private Timeline timeline;

	public Controller() {
		// Initialize the timeline to update every minute
		timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> updateBusTimes()));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	@FXML
	public void initialize() {
		updateBusTimes();
	}
	void updateBusTimes() {
		updateLabels(CurrentBuses.currentBusTimes250(), firstMinLabel, secondMinLabel);
		updateLabels(CurrentBuses.currentBusTimes56(), thirdMinLabel, null);
	}
	private void updateLabels(List<String> busTimes, Label firstLabel, Label secondLabel) {
		if (busTimes != null && busTimes.size() == 1) {
			firstMinLabel.setText(busTimes.get(0) + " min");
			if (secondLabel != null) {
				secondLabel.setText("");
			}
		} else if (busTimes != null && busTimes.size() >= 2) {
			firstLabel.setText(busTimes.get(0) + " min");
			if (secondLabel != null) {
				secondLabel.setText(busTimes.get(1) + " min");
			}
		} else {
			firstLabel.setText("N/A");
			if (secondLabel != null) {
				secondLabel.setText("");
			}
		}
	}

}