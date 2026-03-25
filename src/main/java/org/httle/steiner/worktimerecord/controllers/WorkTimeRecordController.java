package org.httle.steiner.worktimerecord.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.WorkTimeRecord;
import org.httle.steiner.worktimerecord.constants.Constants;
import org.httle.steiner.worktimerecord.model.EntryModel;
import org.httle.steiner.worktimerecord.util.Logger;
import org.httle.steiner.worktimerecord.model.WorktimeModel;
import org.httle.steiner.worktimerecord.util.TimeFormatter;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * Controller for the worktime record UI (landing ui)

 * Behavior summary:
 * - display the total hours per week, worked hours and the hours that still need to be worked in the calendar week
 * - display all worked entries with the employee information
 * - exports the .csv file with the menu file>export on local computer
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

    @FXML private MenuItem menuItemExport;
    @FXML private MenuItem menuItemClear;
    @FXML private MenuItem menuItemRefresh;
    @FXML private MenuItem menuItemExit;

    private WorktimeModel worktimeModel;
    private final Logger logger = Logger.getInstance();
    private final TimeFormatter timeFormatter = TimeFormatter.getInstance();

    // Setting the worktime model in order to communicate between classes with the same worked hours values
    public void setWorktimeModel(WorktimeModel worktimeModel) {
        this.worktimeModel = worktimeModel;

        // %.2fh due to -> % begin of placeholder, .2 -> decimal places, f -> double/float, h -> just to show the unit
        lbHoursWorked.textProperty().bind(Bindings.createStringBinding(() ->
                String.format(timeFormatter.formatDoubleToTime(worktimeModel.getWorkedHours()) + "h \nWorkhours"),
                worktimeModel.workedHoursProperty())
        );

        // Formatting the double in a String datatype in order to achieve the hh:mm format on the labels
        lbHoursRest.textProperty().bind(Bindings.createStringBinding(() -> {
                    double rest = Constants.TOTAL_WORKINGHOURS - worktimeModel.getWorkedHours();

                    if (rest < 0) {return String.format(timeFormatter.formatDoubleToTime(Math.abs(rest)) + "h \nOvertime");}
                    else {return String.format(timeFormatter.formatDoubleToTime(rest) + "h \nRemaining");}
                }, worktimeModel.workedHoursProperty())
        );

        lbHoursSummary.setText(timeFormatter.formatDoubleToTime(Constants.TOTAL_WORKINGHOURS) + "h \nTotal working hours");
    }

    // Initializing all FXML components
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
        menuItemExport.setOnAction(e -> exportCSVFile());
        menuItemClear.setOnAction(e -> clearEntriesCSV());
        menuItemRefresh.setOnAction(e -> refreshEntries());
        menuItemExit.setOnAction(e -> exitApplication());
        menuItemExit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        enterEntries();
    }

    // Opens up the input window in which is the form for a new entry
    private void openInputWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("worktimerecordentry.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            WorkTimeRecordEntryController entryController = fxmlLoader.getController();
            entryController.setWorktimeModel(worktimeModel);
            entryController.setWorkTimeRecordController(this);

            stage.setTitle("Create new entry");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnEntry.getScene().getWindow());
            stage.setScene(scene);
            stage.getIcons().add(new Image(Objects.requireNonNull(WorkTimeRecord.class.getResourceAsStream("img/logo_entry.png"))));
            stage.show();
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

    // Creates a new entry in the work time view table
    private void enterEntries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.ENTRIES_CSV_FILE.toFile()))) {
            String line;
            reader.readLine();

            ObservableList<EntryModel> entries = FXCollections.observableArrayList();

            // All lines in the csv files are getting read and saved in a temporarily EntryModel
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                EntryModel entry = new EntryModel(
                        parts[0], parts[1],parts[2],
                        parts[3], parts[4], parts[5],
                        parts[6], parts[7], parts[8],
                        parts[9]
                        );
                entries.add(entry); // EntryModel getting saved in ObservableList
            }
            workTimeTable.setItems(entries); // Tableview content/items getting set with the ObservableList
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

    // Exports the entries.csv file on the local datastore of the user
    private void exportCSVFile() {
        Stage stage = (Stage) btnEntry.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export CSV file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV files", "*.csv"), // Can be saved as a .csv -
                new FileChooser.ExtensionFilter("Text file", "*.txt")  // or .txt file
        );
        fileChooser.setInitialFileName("entries"); // Presetting the filename

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                Files.copy(Constants.ENTRIES_CSV_FILE, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.log(e.getMessage());
            }
        }
    }

    // Clears all content from the csv file
    private void clearEntriesCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.ENTRIES_CSV_FILE.toFile()));
                PrintWriter writer = new PrintWriter(new FileWriter(Constants.ENTRIES_CSV_FILE.toFile()))) {

            // Writes the header of the csv file
            writer.println("mid;lastname;firstname;project;date;start;end;pause;assignment;notes"); // Writes the header of the csv file

            // Clear all lines except 1, line 2 is empty
            while (((reader.readLine()) != null)) {
                writer.println();
            }

            /*
             *  Refreshes the table view, because entries are cleared
             *  as well as the worked hours, because when no entries
             *  exit, their can't be any worked hours
             */
            refreshEntries();
            worktimeModel.clearHours();
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

    public void refreshEntries() {
        workTimeTable.getItems().clear();
        enterEntries();
    }

    // Exits the application with a MenuItem
    private void exitApplication() {
        Stage stage = (Stage) btnEntry.getScene().getWindow(); // Gets the stage of a fxml component
        stage.close();
    }
}