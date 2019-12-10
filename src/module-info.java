module ClienteGmail {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.mail;
    requires javafx.media;
    requires javafx.web;
    requires commons.email;


    exports es.soraya.models;
    exports es.soraya.logica;
    exports es.soraya.views;
    exports es.soraya;

    opens es.soraya.views to javafx.fxml;

}