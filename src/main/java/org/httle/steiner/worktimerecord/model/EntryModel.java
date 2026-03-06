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

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public String getAssignment() {
        return assignment.get();
    }

    public SimpleStringProperty assignmentProperty() {
        return assignment;
    }

    public String getPause() {
        return pause.get();
    }

    public SimpleStringProperty pauseProperty() {
        return pause;
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getProject() {
        return project.get();
    }

    public SimpleStringProperty projectProperty() {
        return project;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getMid() {
        return mid.get();
    }

    public SimpleStringProperty midProperty() {
        return mid;
    }
}
