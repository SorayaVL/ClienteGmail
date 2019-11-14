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


    public EmailTreeItem(CuentaCorreo cuentaCorreo, String name) {
        super(name);
        this.cuentaCorreo = cuentaCorreo;
        this.name = name;
    }
}
