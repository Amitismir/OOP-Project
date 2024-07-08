module untitled3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    exports view;
    exports Controller;
    opens Model;
    opens Controller to javafx.fxml;
    opens view to javafx.fxml;
}