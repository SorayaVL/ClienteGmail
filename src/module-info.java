module ClienteGmail {

        requires javafx.graphics;
        requires javafx.controls;
        requires javafx.fxml;
        requires org.controlsfx.controls;
        requires java.mail;
        requires javafx.media;
        requires javafx.web;
        requires commons.email;
        requires ComponenteReloj;
        requires jasperreports;
        requires java.sql;
        requires org.docgene.help.jfx;

        exports es.soraya.models;
        exports es.soraya.logica;
        exports es.soraya.views;
        exports es.soraya;
        //exports javafx.scene.transform to ComponenteReloj;


        opens es.soraya.views to javafx.fxml;

        }