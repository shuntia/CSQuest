module CSQuest_b76dd44a {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;

    exports your.package.name;
    opens your.package.name to javafx.fxml;
}