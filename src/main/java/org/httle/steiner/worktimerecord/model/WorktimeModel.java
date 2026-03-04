package org.httle.steiner.worktimerecord.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class WorktimeModel {
    private final DoubleProperty workedHours = new SimpleDoubleProperty(0);

    public double getWorkedHours() {return workedHours.get();}
    public DoubleProperty workedHoursProperty() {return workedHours;}
    public void addHours(double hours) {workedHours.set(getWorkedHours() + hours);}
}
