package org.httle.steiner.worktimerecord.util;

import org.httle.steiner.worktimerecord.constants.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Logger for logging important logs for exceptions etc.

 * Behavior summary:
 * - create a one and only instance of the logger object
 * - getting the logger instance
 * - method for logging
 */

public class Logger {
    private static Logger instance;

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Constants.LOGGING_CSV_FILE.toFile(), true))) {
            writer.println("[Log " + LocalDateTime.now() + "]: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
