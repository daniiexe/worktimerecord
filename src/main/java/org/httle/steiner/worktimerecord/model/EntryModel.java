package org.httle.steiner.worktimerecord.model;

import javafx.beans.property.SimpleStringProperty;

public class EntryModel {
    private final SimpleStringProperty mid;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty project;
    private final SimpleStringProperty date;
    private final SimpleStringProperty start;
    private final SimpleStringProperty end;
    private final SimpleStringProperty pause;
    private final SimpleStringProperty assignment;
    private final SimpleStringProperty notes;

    public EntryModel(String mid, String lastName, String firstName, String project, String date, String start, String end, String pause, String assignment, String notes) {
        this.mid = new SimpleStringProperty(mid);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.project = new SimpleStringProperty(project);
        this.date = new SimpleStringProperty(date);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
        this.pause = new SimpleStringProperty(pause);
        this.assignment = new SimpleStringProperty(assignment);
        this.notes = new SimpleStringProperty(notes);
    }
}
