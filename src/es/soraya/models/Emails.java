package es.soraya.models;

import com.sun.mail.imap.IMAPFolder;
import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import javafx.scene.control.TreeView;

import javax.mail.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Emails extends CuentaCorreo {

    private Address[] from;
    private String subject;
    private Date fecha;
    private IMAPFolder folder;
    private Message mensaje;
    Message[] listaMensajes;

    CuentaCorreo cuentaCorreo;
    TreeView<String> treeView;


    public Emails() {
    }


    public String getFrom() {
        String listaFrom = "";
        if (from != null) {
            for (int i = 0; i < from.length; i++) {
                listaFrom += from[i].toString() + ", ";
            }

        }
        if (listaFrom.length() > 1) listaFrom = listaFrom.substring(0, listaFrom.length() - 2);
        return listaFrom;
    }


    public void setFrom(Address[] from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String date = dateFormat.format(fecha);
        return date;
    }

    public void setDate(Date date) {
        this.fecha = date;
    }

    public Emails(Address[] from, String subject, Date date) {
        this.from = from;
        this.subject = subject;
        fecha = date;

    }


}
