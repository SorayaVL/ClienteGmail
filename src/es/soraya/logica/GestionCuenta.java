package es.soraya.logica;

import com.sun.mail.imap.IMAPFolder;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import es.soraya.models.CuentaCorreo;
import org.apache.commons.mail.*;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GestionCuenta {
    private static GestionCuenta INSTANCE = null;
    private IMAPFolder folder;
    private Message[] listaMensajes;

    public GestionCuenta() {
    }

    public static GestionCuenta getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new GestionCuenta();

        return INSTANCE;
    }


    public void abrirCuenta(CuentaCorreo cuentaCorreo) throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);
        cuentaCorreo.setStore(session.getStore("imaps"));
        cuentaCorreo.getStore().connect("imap.googlemail.com", cuentaCorreo.getEmail(), cuentaCorreo.getPassword());
        System.out.println("Metodo abrir cuenta");
    }

    /**
     * Método para cargar la lista de correos de una carpeta.
     * Limpia los mails que tenemos en la lista de correos para que no sigan apareciendo en el tableview.
     * comprueba que la carpeta tiene mensajes. Si tiene mensajes abre la carpeta en modo lectura escritura, y se guarda todos los mensajes del folder en un array
     * crea un objeto mensaje por cada uno de la lista y lo añade a la lista de correos.
     *
     * @param folderName
     * @param cuentaCorreo
     * @throws MessagingException
     */

    public void listaEmails(String folderName, CuentaCorreo cuentaCorreo) throws MessagingException {
        Logica.getINSTANCE().listaCorreo.clear();
        IMAPFolder folder = (IMAPFolder) cuentaCorreo.getStore().getFolder(folderName);
        if (folder.getType() != 2) {
            if (!folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
                listaMensajes = folder.getMessages();
                for (int i = 0; i < listaMensajes.length; i++) {
                    Message mensaje = listaMensajes[i];
                    Emails correo = new Emails(mensaje.getFrom(), mensaje.getSubject(), mensaje.getReceivedDate(), mensaje);
                    Logica.getINSTANCE().cargarCorreo(correo);
                }

            }

        }
    }


    public EmailTreeItem cargaCarpetas(CuentaCorreo cuentaCorreo) throws MessagingException {
        Folder[] folders = cuentaCorreo.getStore().getDefaultFolder().list();
        EmailTreeItem rootItem = new EmailTreeItem(cuentaCorreo, cuentaCorreo.getEmail(), folder);
        rootItem.setExpanded(true);
        getFolder(folders, rootItem, cuentaCorreo);
        return rootItem;

    }


    private void getFolder(Folder[] folders, EmailTreeItem foldersRoot, CuentaCorreo cuentaCorreo) throws MessagingException {
        for (Folder folder : folders) {
            EmailTreeItem emailTreeItem = new EmailTreeItem(cuentaCorreo, folder.getName(), folder);
            foldersRoot.getChildren().add(emailTreeItem);
            if (folder.getType() == Folder.HOLDS_FOLDERS) {
                getFolder(folder.list(), emailTreeItem, cuentaCorreo);
                System.out.println(folder.getName());

            }

        }
    }


    public String leerMensaje(Message mensaje) {
        try {
            System.out.println(mensaje.getContentType());
            MimeMessage mimeMessage = (MimeMessage) mensaje;
            MimeMessageParser parser = new MimeMessageParser(mimeMessage);
            parser.parse();
            String content = parser.getHtmlContent();

            if (content == null) {
                System.out.println(parser.getPlainContent());
                return parser.getPlainContent();

            } else {
                System.out.println("Mensaje HTML");
                return content;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public void eliminarMensaje (Message message, CuentaCorreo cuentaCorreo, Folder folder){
        try {
            if (!folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
                IMAPFolder folderBasura = (IMAPFolder) cuentaCorreo.getStore().getFolder("[Gmail]/Trash");
                folder.copyMessages(new Message[]{message}, folderBasura);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }


        }

    public void emailSet (String usuario, String password, String de, String[] para, String[] cC, String[] bCC,
                          String asunto, String mensaje){
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(usuario, password));
            email.setSSLOnConnect(true);
            email.setFrom(de);
            email.addTo(para);
            if (cC.length>1)
                email.addCc(cC);
            if (bCC.length>1)
                email.addBcc(bCC);
            email.setSubject(asunto);
            email.setMsg(mensaje);
            System.out.println("Mensaje enviado con exito");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
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
