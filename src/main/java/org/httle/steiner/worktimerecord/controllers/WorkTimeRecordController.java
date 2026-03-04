package org.httle.steiner.worktimerecord.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.constants.Constants;

import java.io.IOException;

public class WorkTimeRecordController {
    @FXML private Button btnEntry;
    @FXML private Label lbHoursSummary;
    @FXML private Label lbHoursWorked;
    @FXML private Label lbHoursRest;

    @FXML
    public void initialize() {
        btnEntry.setOnAction(e -> openInputWindow());
        updateHoursSummary();
        updateHoursWorked();
        updateHoursRest();
    }

    private void openInputWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("worktimerecordentry.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            stage.setTitle("Neuen Eintrag erstellen");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnEntry.getScene().getWindow());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateHoursSummary() {lbHoursSummary.setText("Gesamt: " + Constants.TOTAL_WORKINGHOURS);}

    private void updateHoursWorked() {
        double hoursWorked = WorkTimeRecordEntryController.getWorkedHours();
        lbHoursWorked.setText("Arbeitsstunden: " + hoursWorked);
    }

    private void updateHoursRest() {
        double hoursRest = Constants.TOTAL_WORKINGHOURS - WorkTimeRecordEntryController.getWorkedHours();
        lbHoursRest.setText("Verbleibend: " + hoursRest);
    }
}
