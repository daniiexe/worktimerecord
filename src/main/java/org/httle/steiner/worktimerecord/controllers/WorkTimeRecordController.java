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
        lbHoursSummary.setText("Gesamt: " + Constants.TOTAL_WORKINGHOURS);
    }

    public void setWorktimeModel(WorktimeModel worktimeModel) {
        this.worktimeModel = worktimeModel;

        lbHoursWorked.textProperty().bind(worktimeModel.workedHoursProperty().asString("Arbeitsstunden: %.2f"));

        lbHoursRest.textProperty().bind(Bindings.createStringBinding(
                () -> String.format("Verbleibend: %.2f" , Constants.TOTAL_WORKINGHOURS - worktimeModel.getWorkedHours()), worktimeModel.workedHoursProperty()
        ));
    }

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
}
