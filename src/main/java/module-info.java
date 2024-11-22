module lk.ijse.gdse71.finalproject.jotit {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires static lombok;
    requires jbcrypt;
    requires org.apache.commons.lang3;
    requires prettytime;
    requires jakarta.mail;

    opens lk.ijse.gdse71.finalproject.jotit.controller to javafx.fxml;
    opens lk.ijse.gdse71.finalproject.jotit.controller.components to javafx.fxml;
    exports lk.ijse.gdse71.finalproject.jotit;
    exports lk.ijse.gdse71.finalproject.jotit.dto.tm;

}