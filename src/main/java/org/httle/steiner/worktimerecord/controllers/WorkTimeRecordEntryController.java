package org.httle.steiner.worktimerecord.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WorkTimeRecordEntryController {
    @FXML
    private Button btnCancel;

    private static double workedHours = 8;

    @FXML
    public void initialize() {btnCancel.setOnAction(e -> closeInputWindow());}

    private void closeInputWindow() {
        Stage btnStage = (Stage) btnCancel.getScene().getWindow();
        btnStage.close();
    }

    public static double getWorkedHours() {return workedHours;}
}
