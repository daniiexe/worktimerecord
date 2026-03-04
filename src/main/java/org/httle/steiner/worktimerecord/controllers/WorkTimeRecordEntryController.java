package org.httle.steiner.worktimerecord.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private static double start;
    private static double end;
    private static double pause;
    private String assignment;
    private String notes;

    private static double workedHours;

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
        mid = txtMID.getText();
        firstName = txtFirstname.getText();
        lastName = txtLastname.getText();
        project = txtProject.getText();
        date = txtDate.getValue().toString();
        start = Double.parseDouble(txtStart.getText());
        end = Double.parseDouble(txtEnd.getText());
        pause = Double.parseDouble(txtPause.getText());
        assignment = txtAssignment.getText();
        notes = txtNotes.getText();

        setWorkedHours();
    }

    public static void setWorkedHours() {workedHours  = (end - start) - pause;}
    public static double getWorkedHours() {return workedHours;}
}
