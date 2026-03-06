package org.httle.steiner.worktimerecord.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.httle.steiner.worktimerecord.util.Logger;

import java.io.*;


// TODO: Translate decimal numbers to hour unit numbers
public class WorktimeModel {
    private final Logger logger = Logger.getInstance();
    private final DoubleProperty workedHours = new SimpleDoubleProperty(0);
    private final File saveFile = new File("csv/workedhours.csv");

    public WorktimeModel() {loadHours();}

    public double getWorkedHours() {return workedHours.get();}
    public DoubleProperty workedHoursProperty() {return workedHours;}
    public void addHours(double hours) {workedHours.set(getWorkedHours() + hours); saveHours();}

    private void loadHours() {
        if (!saveFile.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(saveFile))) {
            String line = br.readLine();
            if (line != null) {
                workedHours.set(Double.parseDouble(line));
            }
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }

    private void saveHours() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write(String.valueOf(getWorkedHours()));
        } catch (IOException e) {
            logger.log(e.getMessage());
        }
    }
}
