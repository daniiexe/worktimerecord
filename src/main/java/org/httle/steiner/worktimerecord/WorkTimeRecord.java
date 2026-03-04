package org.httle.steiner.worktimerecord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkTimeRecord extends Application {
    public static void main(String[] args) {
        Application.launch(WorkTimeRecord.class, args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WorkTimeRecord.class.getResource("worktimerecord.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("WorkTimeRecord");
        stage.show();
    }   }
