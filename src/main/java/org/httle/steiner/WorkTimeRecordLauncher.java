package org.httle.steiner;

import javafx.application.Application;
import org.httle.steiner.worktimerecord.WorkTimeRecord;
import org.httle.steiner.worktimerecord.model.Logger;

public class WorkTimeRecordLauncher {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        Application.launch(WorkTimeRecord.class, args);
        logger.log("Application started");
    }
}
