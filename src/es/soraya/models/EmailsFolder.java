package es.soraya.models;

import javax.mail.Address;
import java.util.Date;

public class EmailsFolder {

    private String nombreCarpeta;
    private Address[] remitente;
    private String asunto;
    private Date fecha;

    public String getNombreCarpeta() {
        return nombreCarpeta;
    }

    public void setNombreCarpeta(String nombreCarpeta) {
        this.nombreCarpeta = nombreCarpeta;
    }

    public String getRemitente() {
        String listaFrom = "";
        if (remitente != null) {
            for (int i = 0; i < remitente.length; i++) {
                listaFrom += remitente[i].toString() + ", ";
            }

        }
        if (listaFrom.length() > 1) listaFrom = listaFrom.substring(0, listaFrom.length() - 2);
        return listaFrom;
    }

   /* public String getRemitente() {
        return remitente;
    }*/


    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EmailsFolder(String nombreCarpeta, Address[] remitente, String asunto, Date fecha) {
        this.nombreCarpeta = nombreCarpeta;
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
    }
}