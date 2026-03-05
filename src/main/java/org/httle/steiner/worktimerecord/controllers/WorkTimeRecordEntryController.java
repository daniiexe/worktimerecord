package org.httle.steiner.worktimerecord.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.constants.Constants;
import org.httle.steiner.worktimerecord.model.Logger;
import org.httle.steiner.worktimerecord.model.WorktimeModel;

import java.io.*;

public class WorkTimeRecordEntryController {
    @FXML private Button btnCreate;
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

    private String mid;
    private String firstName;
    private String lastName;
    private String project;
    private String date;
    private double start;
    private double end;
    private double pause;
    private String assignment;
    private String notes;

    private WorktimeModel worktimeModel;
    private final Logger logger = Logger.getInstance();

    public void setWorktimeModel(WorktimeModel worktimeModel) {this.worktimeModel = worktimeModel;}

    @FXML
    public void initialize() {
        btnCreate.setOnAction(e -> createEntry());
        btnCancel.setOnAction(e -> closeInputWindow());
        btnClear.setOnAction(e -> clearInput());
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

            mid = txtMID.getText();
            firstName = txtFirstname.getText();
            lastName = txtLastname.getText();
            project = txtProject.getText();

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

            assignment = txtAssignment.getText();
            notes = txtNotes.getText();

            double workedHours = (end - start) - pause;
            if (workedHours < 0) workedHours = 0;

            worktimeModel.addHours(workedHours);

            try (PrintWriter writer = new PrintWriter(new FileWriter("csv/entries.csv", true))) {
                writer.println(mid + ";" + firstName + ";" + lastName + ";" + project + ";" + date + ";" + start + ";" + end + ";" + pause + ";" + assignment + ";" + notes);

                clearInput();
                closeInputWindow();

            } catch (IOException e) {
                logger.log(e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            logger.log(e.getMessage());
            e.printStackTrace();
        }
    }
}
