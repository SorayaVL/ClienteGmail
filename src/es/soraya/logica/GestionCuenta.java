package es.soraya.logica;

import com.sun.mail.imap.IMAPFolder;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import es.soraya.models.CuentaCorreo;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class GestionCuenta {
    private static GestionCuenta INSTANCE = null;

    Message mensaje;
    Message[] listaMensajes;
    Store store = null;
    IMAPFolder folder = null;
    String subject = null;
    CuentaCorreo cuentaCorreo;
    TreeView<String> treeView;
    MimeMessageParser parser;

    public GestionCuenta() {
    }

    public static GestionCuenta getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new GestionCuenta();

        return INSTANCE;
    }

    public Message[] getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(Message[] listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    public void abrirCuenta(CuentaCorreo cuentaCorreo) throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);

        store = session.getStore("imaps");
        store.connect("imap.googlemail.com", cuentaCorreo.getEmail(), cuentaCorreo.getPassword());
        System.out.println("conectado");
    }

    public void listaEmails(String folderName) throws MessagingException, IOException {
        Logica.getINSTANCE().ListaCorreo.clear();
        folder = (IMAPFolder) store.getFolder(folderName);
        if (!folder.isOpen())
            folder.open(Folder.READ_WRITE);
        listaMensajes = folder.getMessages();
        for (int i = 0; i < listaMensajes.length; i++) {
            mensaje = listaMensajes[i];
            Emails correo = new Emails(mensaje.getFrom(), mensaje.getSubject(), mensaje.getReceivedDate(), mensaje);
            Logica.getINSTANCE().cargarCorreo(correo);
        }
    }

    public EmailTreeItem cargaCarpetas(CuentaCorreo cuentaCorreo) throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list("*");
        EmailTreeItem rootItem = new EmailTreeItem(cuentaCorreo, "Gmail", folder);
        rootItem.setExpanded(true);
        getFolder(folders,rootItem,cuentaCorreo);
        return rootItem;

    }


    public void getFolder(Folder[] folders, EmailTreeItem foldersRoot, CuentaCorreo cuentaCorreo) throws MessagingException {
        for (Folder folder : folders) {
            EmailTreeItem emailTreeItem = new EmailTreeItem(cuentaCorreo, folder.getName(), folder);
            foldersRoot.getChildren().add(emailTreeItem);
            if (folder.getType() == Folder.HOLDS_FOLDERS) {
                System.out.println("folder.getType()" + folder.getType());
                System.out.println("folder.HOLDS_FOLDERS" + folder.HOLDS_FOLDERS);
                getFolder(folder.list(""), emailTreeItem, cuentaCorreo);

            }

        }
    }



    public String leerMensaje(Message mensaje) {
        try {
            MimeMessage mimeMessage = (MimeMessage) mensaje;
            MimeMessageParser parser = new MimeMessageParser(mimeMessage);
            parser.parse();
            String content = parser.getHtmlContent();
            if (content == null){
               return parser.parse().getPlainContent();

            }

            else{
                return content;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

     /* System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
                    System.out.println("_______________________________________________________");
                    System.out.println("No of Messages : " + folder.getMessageCount());
                    System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
                    System.out.println(messages.length);
                    System.out.println(getListaCorreo().toString());
                    System.out.println("*****************************************************************************");
                    System.out.println("MESSAGE " + (i + 1) + ":");
                    System.out.println(mensaje.getMessageNumber());
                    Object String;
                    System.out.println(folder.getUID(mensaje)
                    System.out.println("Subject: " + subject);
                    System.out.println("From: " + mensaje.getFrom()[0]);
                    System.out.println("To: " + mensaje.getAllRecipients()[0]);
                    System.out.println("Date: " + mensaje.getReceivedDate());
                    System.out.println("Size: " + mensaje.getSize());
                    System.out.println(mensaje.getFlags());
                    System.out.println("Body: \n" + mensaje.getContent());
                    System.out.println(mensaje.getContentType());*/




}


