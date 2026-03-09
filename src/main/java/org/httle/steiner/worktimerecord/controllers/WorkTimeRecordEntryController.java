package org.httle.steiner.worktimerecord.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.constants.Constants;
import org.httle.steiner.worktimerecord.util.Logger;
import org.httle.steiner.worktimerecord.model.WorktimeModel;
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
    private WorktimeModel worktimeModel;

    public void setWorktimeModel(WorktimeModel worktimeModel) {this.worktimeModel = worktimeModel;}
    private WorkTimeRecordController workTimeRecordController;

    public void setWorkTimeRecordController(WorkTimeRecordController workTimeRecordController) {this.workTimeRecordController = workTimeRecordController;}

    @FXML
    public void initialize() {
        btnSave.setOnAction(e -> createEntry());
        btnCancel.setOnAction(e -> closeInputWindow());
        btnClear.setOnAction(e -> clearInput());
        txtDate.setEditable(false);
    }

    private void closeInputWindow() {
        Stage btnStage = (Stage) btnCancel.getScene().getWindow();
        btnStage.close();
    }

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

    private void createEntry() {
        try {
            if (worktimeModel == null) {System.err.println("WorktimeModel is null!"); return;}

            String mid = txtMID.getText();
            String firstName = txtFirstname.getText();
            String lastName = txtLastname.getText();
            String project = txtProject.getText();

            String date;
            double start;
            double end;
            double pause;

            if (txtDate.getValue() != null) {
                date = txtDate.getValue().toString();
            } else {
                date = "";
            }

            if (txtStart.getText().isEmpty()) {
                start = 0;
            } else {
                start = Double.parseDouble(txtStart.getText());
            }

            if (txtEnd.getText().isEmpty()) {
                end = 0;
            } else {
                end = Double.parseDouble(txtEnd.getText());
            }

            if (txtPause.getText().isEmpty()) {
                pause = 0;
            } else {
                pause = Double.parseDouble(txtPause.getText());
            }

            String assignment = txtAssignment.getText();
            String notes = txtNotes.getText();

            double workedHours = (end - start) - pause;
            worktimeModel.addHours(workedHours);



            try (PrintWriter writer = new PrintWriter(new FileWriter(Constants.ENTRIES_CSV_FILE.toFile(), true))) {
                writer.println(mid + ";" + firstName + ";" + lastName + ";" + project + ";" + date + ";" + start + ";" + end + ";" + pause + ";" + assignment + ";" + notes);

                clearInput();
                closeInputWindow();
            } catch (IOException e) {
                logger.log(e.getMessage());
            }

            workTimeRecordController.refreshEntries();
        } catch (Exception e) {
            logger.log(e.getMessage());
        }
    }
}