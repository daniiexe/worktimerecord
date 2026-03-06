module org.httle.steiner.worktimerecord {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.httle.steiner.worktimerecord to javafx.fxml;
    exports org.httle.steiner.worktimerecord;
    exports org.httle.steiner.worktimerecord.controllers;
    opens org.httle.steiner.worktimerecord.controllers to javafx.fxml;
    opens org.httle.steiner.worktimerecord.model to javafx.fxml;
    exports org.httle.steiner.worktimerecord.model;
    exports org.httle.steiner.worktimerecord.util;
    opens org.httle.steiner.worktimerecord.util to javafx.fxml;

}