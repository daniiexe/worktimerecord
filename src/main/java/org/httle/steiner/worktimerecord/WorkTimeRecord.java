package org.httle.steiner.worktimerecord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.httle.steiner.worktimerecord.controllers.WorkTimeRecordController;
import org.httle.steiner.worktimerecord.model.WorktimeModel;

import java.io.IOException;

public class WorkTimeRecord extends Application {
    public static void main(String[] args) {
        Application.launch(WorkTimeRecord.class, args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        WorktimeModel sharedModel = new WorktimeModel();

        FXMLLoader fxmlLoader = new FXMLLoader(WorkTimeRecord.class.getResource("worktimerecord.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        WorkTimeRecordController controller = fxmlLoader.getController();
        controller.setWorktimeModel(sharedModel);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("WorkTimeRecord");
        stage.show();
    }
}
