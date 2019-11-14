package es.soraya.logica;

import com.sun.mail.imap.IMAPFolder;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import es.soraya.models.CuentaCorreo;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.mail.*;
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

    public GestionCuenta() {
    }

    public static GestionCuenta getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new GestionCuenta();

        return INSTANCE;
    }

    public void abrirCuenta( String email, String password) throws MessagingException {


        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);

        store = session.getStore("imaps");
        store.connect("imap.googlemail.com", email, password);
        System.out.println("conectado");
    }

     public void listaEmails() throws MessagingException, IOException {
         folder = (IMAPFolder) store.getFolder("inbox");
         if (!folder.isOpen())
             folder.open(Folder.READ_WRITE);
         listaMensajes = folder.getMessages();
         for (int i = 0; i < listaMensajes.length; i++) {
             mensaje = listaMensajes[i];
             Emails correo = new Emails(mensaje.getFrom(), mensaje.getSubject(), mensaje.getReceivedDate());
             Logica.getINSTANCE().cargarCorreo(correo);
         }
     }

     public EmailTreeItem cargaCarpetas() throws MessagingException {
            EmailTreeItem rootItem = new EmailTreeItem(cuentaCorreo,"dirección email");
            Folder[] folders = store.getDefaultFolder().list(/*"*"*/);
            rootItem.setExpanded(true);
            for (Folder folder : folders) {
                if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
                   EmailTreeItem item = new EmailTreeItem(cuentaCorreo,folder.getName().toString());
                   rootItem.getChildren().add(item);
                 System.out.println(folder.getName() + ": " + folder.getMessageCount());

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
         }
            return rootItem;
     }

     /*   public EmailTreeItem cargaCarpeta (CuentaCorreo cuentaCorreo){
        EmailTreeItem emailTreeItem = new EmailTreeItem(cuentaCorreo.getEmail());
        Folder[] folders = cuentaCorreo.getStore().getDefaultFolder().list();
        getFolder(folders, emailTreeItem, cuentaCorreo);
        return emailTreeItem;
        }

        public void getFolder (Folder[] folders, EmailTreeItem foldersRoot, CuentaCorreo cuentaCorreo){
        for (Folder folder : folders){



    }


        }*/

   private TreeItem<String> crearBranch(String folder, TreeItem<String> padre) {
       TreeItem<String> item = new TreeItem<>(folder);
       item.setExpanded(true);
       padre.getChildren().add(item);// añadimos los items al itemPadre (el del nivel superior)
       return item;


    }
}
