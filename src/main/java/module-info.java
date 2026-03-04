module org.httle.steiner.worktimerecord {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.httle.steiner.worktimerecord to javafx.fxml;
    exports org.httle.steiner.worktimerecord;
    exports org.httle.steiner.worktimerecord.controllers;
    opens org.httle.steiner.worktimerecord.controllers to javafx.fxml;

}