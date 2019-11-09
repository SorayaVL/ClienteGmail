package es.soraya.logica;

import com.sun.mail.util.MailSSLSocketFactory;
import es.soraya.models.Correo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
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

    private List<Correo> ListaCorreo = new ArrayList<Correo>();

    public List<Correo> getListaCorreo() {
        return ListaCorreo;
    }




}
