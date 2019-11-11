package es.soraya.models;

import javax.mail.Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Correo {

    private Address[] from;
    private String subject;
    private Date fecha;


    public String getFrom() {
        String listaFrom="";
        if (from != null){
            for (int i = 0; i<from.length; i++){
                listaFrom+=from[i].toString()+", ";
            }

        }
        if (listaFrom.length()>1) listaFrom= listaFrom.substring(0,listaFrom.length()-2);
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

    public Correo (Address[] from, String subject, Date date) {
        this.from = from;
        this.subject = subject;
        fecha = date;
    }

}
