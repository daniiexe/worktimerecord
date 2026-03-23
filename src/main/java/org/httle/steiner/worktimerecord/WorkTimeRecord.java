package org.httle.steiner.worktimerecord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.controllers.WorkTimeRecordController;
import org.httle.steiner.worktimerecord.model.WorktimeModel;
import org.httle.steiner.worktimerecord.util.Logger;

import java.io.IOException;
import java.util.Objects;

/**
 * Worktime record main UI class

 * Behavior summary:
 * - creates a sharedModel between this class and the controllers to communicate the worked hours
 * - shows the main stage of the work time record application
 */

public class WorkTimeRecord extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Shared worktime model for the communication of the worked hours between classes
        WorktimeModel sharedModel = new WorktimeModel();
        Logger logger = Logger.getInstance();

        // Loading the fxml file and creating new scene
        FXMLLoader fxmlLoader = new FXMLLoader(WorkTimeRecord.class.getResource("worktimerecord.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Getting controller to set the shared model for the worked hours
        WorkTimeRecordController controller = fxmlLoader.getController();
        controller.setWorktimeModel(sharedModel);

        // Configure the scene
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("WorkTimeRecord");
        stage.getIcons().add(new Image(Objects.requireNonNull(WorkTimeRecord.class.getResourceAsStream("img/logo.png"))));
        stage.show();

        // Logging the start of the application
        logger.log("Application started");
    }
}
