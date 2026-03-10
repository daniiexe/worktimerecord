package org.httle.steiner.worktimerecord.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.constants.Constants;
import org.httle.steiner.worktimerecord.util.Logger;
import org.httle.steiner.worktimerecord.model.WorktimeModel;
import org.httle.steiner.worktimerecord.util.TimeFormatter;

import java.io.*;

/**
 * Controller for the worktime entry UI and saving the values into csv files

 * Behavior summary:
 * - create new entries
 * - clear the form
 * - all entries are getting saved in entries.csv file in the root of the project in the directory csv
 *      - ./csv/entries.csv
 */

public class WorkTimeRecordEntryController {
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnClear;

    @FXML private TextField txtMID;
    @FXML private TextField txtFirstname;
    @FXML private TextField txtLastname;
    @FXML private TextField txtProject;
    @FXML private DatePicker txtDate;
    @FXML private TextField txtStart;
    @FXML private TextField txtEnd;
    @FXML private TextField txtPause;
    @FXML private TextArea txtAssignment;
    @FXML private TextArea txtNotes;

    private final Logger logger = Logger.getInstance();
    private final TimeFormatter timeFormatter = TimeFormatter.getInstance();
    private WorktimeModel worktimeModel;

    public void setWorktimeModel(WorktimeModel worktimeModel) {this.worktimeModel = worktimeModel;}
    private WorkTimeRecordController workTimeRecordController;

    public void setWorkTimeRecordController(WorkTimeRecordController workTimeRecordController) {this.workTimeRecordController = workTimeRecordController;}

    // Initializing all FXML components
    @FXML
    public void initialize() {
        btnSave.setOnAction(e -> createEntry());
        btnCancel.setOnAction(e -> closeInputWindow());
        btnClear.setOnAction(e -> clearInput());
        txtDate.setEditable(false);
    }

    // Closes the input window from the entry form
    private void closeInputWindow() {
        Stage btnStage = (Stage) btnCancel.getScene().getWindow();
        btnStage.close();
    }

    // Clears all input values of the entry form
    private void clearInput() {
        txtMID.clear();
        txtFirstname.clear();
        txtLastname.clear();
        txtProject.clear();
        txtDate.setValue(null);
        txtStart.clear();
        txtEnd.clear();
        txtPause.clear();
        txtAssignment.clear();
        txtNotes.clear();
    }

    /**
     * Creates a new entry when button is clicked in the entry form
     * and loads this entry in the csv file and refreshes the tableview
     * and loads all csv saved entries when calling the refreshEntries() method
     */
    private void createEntry() {
        try {
            if (worktimeModel == null) {System.err.println("WorktimeModel is null!"); return;}

            String mid = "";
            String firstName = "";
            String lastName = "";
            String project = "";
            String date = "";
            double start = 0.0;
            double end = 0.0;
            double pause = 0.0;
            String assignment = "";
            String notes = "";
            boolean success = false;

            /*
             * Checking every single text field, datepicker and textarea if it has
             * any values, if not, method mandatoryErrorPopup(String message)
             * is being called to inform the user, to enter a value.
             */

            while (!success) {
                if (txtMID.getText().isBlank()) {
                    mandatoryErrorPopup("MID must be entered!");
                    break;
                } else {
                    mid = txtMID.getText();
                }

                if (txtLastname.getText().isBlank()) {
                    mandatoryErrorPopup("Lastname must be entered!");
                    break;
                } else {
                    lastName = txtLastname.getText();
                }

                if (txtFirstname.getText().isBlank()) {
                    mandatoryErrorPopup("Firstname must be entered!");
                    break;
                } else {
                    firstName = txtFirstname.getText();
                }

                if (txtProject.getText().isBlank()) {
                    mandatoryErrorPopup("Project must be entered!");
                    break;
                } else {
                    project = txtProject.getText();
                }

                if (txtDate.getValue() == null) {
                    mandatoryErrorPopup("Date must be picked!");
                    break;
                } else {
                    date = txtDate.getValue().toString();
                }

                if (txtStart.getText().isBlank()) {
                    mandatoryErrorPopup("Start time must be entered!");
                    break;
                } else {
                    start = timeFormatter.formatTimeToDouble(txtStart.getText());
                }

                if (txtEnd.getText().isBlank()) {
                    mandatoryErrorPopup("End time must be entered!");
                    break;
                } else {
                    end = timeFormatter.formatTimeToDouble(txtEnd.getText());
                }

                // TODO: ISSUE: When pause time is < -1, e.g. -0:30, than it's not negative - need to fix
                if (txtPause.getText().isBlank()) {
                    mandatoryErrorPopup("Pause time must be entered!");
                    break;
                } else if (timeFormatter.formatTimeToDouble(txtPause.getText()) < 0.0){
                    pauseErrorPopup("Pause time is negative!");
                    break;
                } else if (timeFormatter.formatTimeToDouble(txtPause.getText()) >= (end - start) / 60) {
                    pauseErrorPopup("Pause time is longer then worked hours");
                    break;
                } else {
                    pause = timeFormatter.formatStringToDouble(txtPause.getText());
                }

                if (txtAssignment.getText().isBlank()) {
                    mandatoryErrorPopup("Assignment must be entered!");
                    break;
                } else {
                    assignment = txtAssignment.getText();
                }

                if (txtNotes.getText().isBlank()) {
                    notes = " ";
                } else {
                    notes = txtNotes.getText();
                }

                success = true;
            }

            if (!success) return;

            double workedHours = (end - start) - pause;
            worktimeModel.addHours(workedHours);

            // Writes the entered entry in the csv file
            try (PrintWriter writer = new PrintWriter(new FileWriter(Constants.ENTRIES_CSV_FILE.toFile(), true))) {
                writer.println(mid + ";" + firstName + ";" + lastName + ";" + project + ";" + date + ";" + timeFormatter.formatDoubleToTime(start) + ";" + timeFormatter.formatDoubleToTime(end) + ";" + timeFormatter.formatDoubleToString(pause) + ";" + assignment + ";" + notes);
            } catch (IOException e) {
                logger.log(e.getMessage());
            }

            // Refreshes the complete tableview, clears all inputs and closes the window
            workTimeRecordController.refreshEntries();
            clearInput();
            closeInputWindow();
        } catch (Exception e) {
            logger.log(e.getMessage());
        }
    }

    // Method calling used for error popup for mandatory field which are not filled
    private void mandatoryErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Mandatory field missing");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method calling when pause time is negative or pause time is longer then worked hours
    private void pauseErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pause time isn't correct!");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}