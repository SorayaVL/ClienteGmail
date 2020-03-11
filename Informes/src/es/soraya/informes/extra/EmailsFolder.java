package es.soraya.informes.extra;

import java.util.Date;

public class EmailsFolder {

    private String nombreCarpeta;
    private String remitente;
    private String asunto;
    private String contenido;
    private Date fecha;

    public String getNombreCarpeta() {
        return nombreCarpeta;
    }

    public void setNombreCarpeta(String nombreCarpeta) {
        this.nombreCarpeta = nombreCarpeta;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public EmailsFolder(String nombreCarpeta, String remitente, String asunto, Date fecha) {
        this.nombreCarpeta = nombreCarpeta;
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
    }

    public EmailsFolder(String nombreCarpeta, String remitente, String asunto,  Date fecha, String contenido) {
        this.nombreCarpeta = nombreCarpeta;
        this.remitente = remitente;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fecha = fecha;
    }
}
