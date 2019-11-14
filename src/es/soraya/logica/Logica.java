package es.soraya.logica;

import com.sun.mail.imap.IMAPFolder;
import es.soraya.models.Emails;
import es.soraya.models.CuentaCorreo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Folder;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logica {

    private static Logica INSTANCE = null;

    public Logica() {
    }

    public static Logica getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Emails> ListaCorreo = FXCollections.observableArrayList();
    public List<CuentaCorreo> ListaCuentas = new ArrayList<>();

    public ObservableList<Emails> getListaCorreo() {
        return ListaCorreo;
    }

    public void setListaCorreo(ObservableList<Emails> listaCorreo) {
        ListaCorreo = listaCorreo;
    }

    public List<CuentaCorreo> getListaCuentas() {
        return ListaCuentas;
    }

    public void cargarCorreo(Emails correo) {
        ListaCorreo.add(correo);
    }

    public void aniadirCuenta(CuentaCorreo cuentaCorreo) {
        ListaCuentas.add(cuentaCorreo);
    }





}
