package es.soraya.models;

public class Correo {

    private String from;
    private String subject;
    private String date;

    public Correo (String from, String subject, String date) {
        this.from = from;
        this.subject = subject;
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
