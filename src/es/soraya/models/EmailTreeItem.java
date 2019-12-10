package es.soraya.models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {
    private CuentaCorreo cuentaCorreo;
    private String name;
    private Folder folder;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public CuentaCorreo getCuentaCorreo() {
        return cuentaCorreo;
    }

    public void setCuentaCorreo(CuentaCorreo cuentaCorreo) {
        this.cuentaCorreo = cuentaCorreo;
    }

    public EmailTreeItem(CuentaCorreo cuentaCorreo, String name, Folder folder) {
        super(name);
        this.cuentaCorreo = cuentaCorreo;
        this.name = name;
        this.setFolder(folder);
    }
}
