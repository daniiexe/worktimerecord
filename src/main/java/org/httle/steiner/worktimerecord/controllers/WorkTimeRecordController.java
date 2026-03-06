package org.httle.steiner.worktimerecord.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.constants.Constants;
import org.httle.steiner.worktimerecord.model.EntryModel;
import org.httle.steiner.worktimerecord.model.Logger;
import org.httle.steiner.worktimerecord.model.WorktimeModel;

import java.io.*;
import java.lang.module.ModuleDescriptor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

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

    @FXML private TableView<EntryModel> workTimeTable;
    @FXML private TableColumn<EntryModel, String> colMID;
    @FXML private TableColumn<EntryModel, String> colLastname;
    @FXML private TableColumn<EntryModel, String> colFirstname;
    @FXML private TableColumn<EntryModel, String> colProject;
    @FXML private TableColumn<EntryModel, String> colDate;
    @FXML private TableColumn<EntryModel, String> colStart;
    @FXML private TableColumn<EntryModel, String> colEnd;
    @FXML private TableColumn<EntryModel, String> colPause;
    @FXML private TableColumn<EntryModel, String> colAssignment;
    @FXML private TableColumn<EntryModel, String> colNotes;

    @FXML private MenuBar menuBar;
    @FXML private Menu file;
    @FXML private MenuItem menuItemExport;

    private WorktimeModel worktimeModel;
    private final Logger logger = Logger.getInstance();

    @FXML
    public void initialize() {
        colMID.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("project"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colPause.setCellValueFactory(new PropertyValueFactory<>("pause"));
        colAssignment.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        btnEntry.setOnAction(e -> openInputWindow());
        // menuItemExport.setOnAction(e -> exportCSVFile());
        lbHoursSummary.setText("Gesamt: " + Constants.TOTAL_WORKINGHOURS + "h");
        enterEntries();
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

    // TODO: Create new entries with the information form the ./csv/entries.csv
    private void enterEntries() {
        try (BufferedReader reader = new BufferedReader(new FileReader("csv/entries.csv"))) {
            String line;
            reader.readLine();

            ObservableList<EntryModel> entries = FXCollections.observableArrayList();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                EntryModel entry = new EntryModel(
                        parts[0], parts[1],parts[2],
                        parts[3], parts[4], parts[5],
                        parts[6], parts[7], parts[8],
                        parts[9]
                        );

                entries.add(entry);
            }

            workTimeTable.setItems(entries);

        } catch (IOException e) {
            logger.log(e.getMessage());
        }
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

//    private void exportCSVFile() {
//        try {
//            File csvFile = Path.of("csv/entries.csv").toFile();
//            Path target = Path.of("C://");
//
//            Files.copy(csvFile.toPath(), target);
//        } catch (IOException e) {
//            logger.log(e.getMessage());
//        }
//    }
}
