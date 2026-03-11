package org.httle.steiner.worktimerecord.constants;

import java.nio.file.Path;

/**
 * Constants class

 * Behavior summary:
 * - constant for the total working hours per week
 */

public class Constants {
    public static final int TOTAL_WORKINGHOURS = 38; // Total working hours during the week
    public static final Path ENTRIES_CSV_FILE = Path.of("csv/entries.csv");
    public static final Path WORKED_HOURS_CSV_FILE = Path.of("csv/workedhours.csv");
    public static final Path LOGGING_CSV_FILE = Path.of("log/logs.log");
}
