package org.httle.steiner.worktimerecord.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.constants.Constants;
import org.httle.steiner.worktimerecord.model.Logger;
import org.httle.steiner.worktimerecord.model.WorktimeModel;

import java.io.IOException;

/**
 * Controller for the worktime record UI (landing ui)

 * Behavior summary:
 * - display the total hours per week, worked hours and the hours that still need to be worked in the calendar week
 * - display all worked entries with the employee information
 */

public class WorkTimeRecordController {
    @FXML private Button btnEntry;
    @FXML private Label lbHoursSummary;
    @FXML private Label lbHoursWorked;
    @FXML private Label lbHoursRest;

    private WorktimeModel worktimeModel;
    private final Logger logger = Logger.getInstance();

    @FXML
    public void initialize() {
        btnEntry.setOnAction(e -> openInputWindow());
        lbHoursSummary.setText("Gesamt: " + Constants.TOTAL_WORKINGHOURS + "h");
    }

    // Setting the worktime model in order to communicate between classes with the same worked hours values
    public void setWorktimeModel(WorktimeModel worktimeModel) {
        this.worktimeModel = worktimeModel;

        // %.2fh due to -> % begin of placeholder, .2 -> decimal places, f -> double/float, h -> just to show the unit
        lbHoursWorked.textProperty().bind(worktimeModel.workedHoursProperty().asString("Arbeitsstunden: %.2fh"));

        lbHoursRest.textProperty().bind(Bindings.createStringBinding(() -> {
            double rest = Constants.TOTAL_WORKINGHOURS - worktimeModel.getWorkedHours();

            if (rest < 0) {return String.format("Überstunden: %.2fh", Math.abs(rest));}
            else {return String.format("Verbleibend: %.2fh", rest);}
        }, worktimeModel.workedHoursProperty())
        );

    }

    // Opens up the input window in which is the form for a new entry
    private void openInputWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("worktimerecordentry.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            WorkTimeRecordEntryController entryController = fxmlLoader.getController();
            entryController.setWorktimeModel(worktimeModel);

            stage.setTitle("Neuen Eintrag erstellen");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnEntry.getScene().getWindow());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

    // TODO: Create new entries with the information form the ./csv/entries.csv
}
