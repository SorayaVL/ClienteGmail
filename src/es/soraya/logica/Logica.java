package es.soraya.logica;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.util.MailSSLSocketFactory;
import es.soraya.models.Correo;
import es.soraya.models.CuentaCorreo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;

    public Logica() {
    }

    public static Logica getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Correo> ListaCorreo = FXCollections.observableArrayList();
    public List<CuentaCorreo> ListaCuentas = new ArrayList<>();

    public ObservableList<Correo> getListaCorreo() {
        return ListaCorreo;
    }

    public void setListaCorreo(ObservableList<Correo> listaCorreo) {
        ListaCorreo = listaCorreo;
    }

    public List<CuentaCorreo> getListaCuentas() {
        return ListaCuentas;
    }

    public void cargarCorreo(Correo correo) {
        ListaCorreo.add(correo);
    }

    public void aniadirCuenta(CuentaCorreo cuentaCorreo) {
        ListaCuentas.add(cuentaCorreo);
    }

}
