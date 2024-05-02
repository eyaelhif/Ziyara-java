module com.example.ziyara {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;
    requires mail;
    requires java.prefs;
    requires javafx.web;

    opens test to javafx.fxml;
    exports test;
    exports controllers;
    opens controllers to javafx.fxml;
    opens models to javafx.base;
}