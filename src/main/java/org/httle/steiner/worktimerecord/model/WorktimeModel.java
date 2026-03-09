package org.httle.steiner.worktimerecord.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.httle.steiner.worktimerecord.constants.Constants;
import org.httle.steiner.worktimerecord.util.Logger;

import java.io.*;

/**
 * Model for the worktime to communicate the worked hours between classes

 * Behavior summary:
 * - load hours from a csv file and save them into it (override)
 * - with this class the worked hours can be communicated between different controller classes without using a public static variable or/and method
 */

public class WorktimeModel {
    private final Logger logger = Logger.getInstance();
    private final DoubleProperty workedHours = new SimpleDoubleProperty(0);
    private final File saveFile = new File(Constants.WORKED_HOURS_CSV_FILE.toUri());

    public WorktimeModel() {loadHours();}

    public double getWorkedHours() {return workedHours.get();}
    public DoubleProperty workedHoursProperty() {return workedHours;}
    public void addHours(double hours) {workedHours.set(getWorkedHours() + hours); saveHours();}

    // Load the worked hours from the workedhours.csv file and save it in a variable
    private void loadHours() {
        if (!saveFile.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(saveFile))) {
            String line = br.readLine();
            if (line != null) {
                workedHours.set(Double.parseDouble(line));
            } else {
                workedHours.set(0);
            }
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

    // Save the worked hours in the worked.hours.csv file
    private void saveHours() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write(String.valueOf(getWorkedHours()));
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

    // Clear all worked hours in the variable and the workedhours.csv file
    public void clearHours() {
        workedHours.set(0);
        try (PrintWriter writer = new PrintWriter(new FileWriter(Constants.WORKED_HOURS_CSV_FILE.toFile()))) {
            writer.println(0.0);
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }
}
