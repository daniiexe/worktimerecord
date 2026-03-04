package org.httle.steiner.worktimerecord.model.builder;

/* Might be needed in the project but not sure about it */

public class Entry {
    private final String mid;
    private final String firstName;
    private final String lastName;
    private final String project;
    private final String date;
    private final String start;
    private final String end;
    private final String pause;
    private final String assignment;
    private final String notes;

    private Entry(EntryBuilder builder) {
        this.mid = builder.mid;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.project = builder.project;
        this.date = builder.date;
        this.start = builder.start;
        this.end = builder.end;
        this.pause = builder.pause;
        this.assignment = builder.assignment;
        this.notes = builder.notes;
    }

    public static class EntryBuilder {
        private String mid;
        private String firstName;
        private String lastName;
        private String project;
        private String date;
        private String start;
        private String end;
        private String pause;
        private String assignment;
        private String notes;

        public EntryBuilder withMID(String mid) {this.mid = mid; return this;}
        public EntryBuilder withFirstName(String firstName) {this.firstName = firstName; return this;}
        public EntryBuilder withLastName(String lastName) {this.lastName = lastName; return this;}
        public EntryBuilder withProject(String project) {this.project = project; return this;}
        public EntryBuilder withDate(String date) {this.date = date; return this;}
        public EntryBuilder withStart(String start) {this.start = start; return this;}
        public EntryBuilder withEnd(String end) {this.end = end; return this;}
        public EntryBuilder withPause(String pause) {this.pause = pause; return this;}
        public EntryBuilder withAssignment(String assignment) {this.assignment = assignment; return this;}
        public EntryBuilder withNotes(String notes) {this.notes = notes; return this;}

        public Entry build() {return new Entry(this);}
    }
}
