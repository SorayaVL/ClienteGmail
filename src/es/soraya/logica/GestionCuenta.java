package es.soraya.logica;

import com.sun.mail.imap.IMAPFolder;
import es.soraya.models.Correo;
import es.soraya.models.CuentaCorreo;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class GestionCuenta {
    private static GestionCuenta INSTANCE = null;
    Message mensaje;
    Store store = null;
    IMAPFolder folder = null;
    String subject = null;

    public GestionCuenta() {
    }

    public static GestionCuenta getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new GestionCuenta();

        return INSTANCE;
    }

    public void abrirCuenta(CuentaCorreo cuentaCorreo) throws MessagingException, IOException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);

        store = session.getStore("imaps");
        store.connect("imap.googlemail.com", cuentaCorreo.getEmail(), cuentaCorreo.getPassword());

        folder = (IMAPFolder) store.getFolder("inbox");
        if (!folder.isOpen())
            folder.open(Folder.READ_WRITE);
        Message[] messages = folder.getMessages();
       /* System.out.println("No of Messages : " + folder.getMessageCount());
        System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
        System.out.println(messages.length);*/
        for (int i = 0; i < messages.length; i++) {
            mensaje = messages[i];
            Correo correo = new Correo(mensaje.getFrom(), mensaje.getSubject(),mensaje.getReceivedDate());
            Logica.getINSTANCE().cargarCorreo(correo);

            //System.out.println(getListaCorreo().toString());
            /* System.out.println("*****************************************************************************");
            System.out.println("MESSAGE " + (i + 1) + ":");*/
            //System.out.println(mensaje.getMessageNumber());
            //Object String;
            //System.out.println(folder.getUID(mensaje)
            /*System.out.println("Subject: " + subject);
            System.out.println("From: " + mensaje.getFrom()[0]);
            System.out.println("To: " + mensaje.getAllRecipients()[0]);
            System.out.println("Date: " + mensaje.getReceivedDate());
            System.out.println("Size: " + mensaje.getSize());
            System.out.println(mensaje.getFlags());*/
            System.out.println("Body: \n" + mensaje.getContent());
            System.out.println(mensaje.getContentType());

        }
    }

}
