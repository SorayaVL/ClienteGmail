package es.soraya.models;

import javax.mail.Folder;

public class Carpeta {
    private Folder folder;

   private String nombre;

    public String getNombre() {
        return nombre;
    }

    public Carpeta(String nombre) {
        this.nombre = nombre;
    }



    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;

    }

    public Carpeta(Folder folder, String nombre) {
        this.folder = folder;
        this.nombre = nombre;
    }
}
