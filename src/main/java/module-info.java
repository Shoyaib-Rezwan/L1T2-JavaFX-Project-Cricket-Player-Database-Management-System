module com.example.mainpoject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

//    opens com.example.mainpoject to javafx.fxml;
//    exports com.example.mainpoject;
    opens Main to javafx.fxml;
    exports Main;
    opens Database to javafx.fxml;
    exports Database;
    opens Sever to javafx.fxml;
    exports Sever;
    opens NetworkUtil to javafx.fxml;
    exports NetworkUtil;
    opens SideWindows to javafx.fxml;
    exports SideWindows;
}