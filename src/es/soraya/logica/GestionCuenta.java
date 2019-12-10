package es.soraya.logica;


import com.sun.mail.imap.IMAPFolder;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import es.soraya.models.CuentaCorreo;
import javafx.scene.control.TreeItem;
import org.apache.commons.mail.*;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GestionCuenta {
    private static GestionCuenta INSTANCE = null;
    private Message[] listaMensajes;
    private Folder folder;
    private Store store;
    private TreeItem rootItem;
    private List<TreeItem<String>> listaTreeitem = new ArrayList<>();

    public GestionCuenta() {
    }

    public static GestionCuenta getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new GestionCuenta();

        return INSTANCE;
    }

    /**
     * Método para abrir cada una de las cuentas de correo de Gmail
     *
     * @param cuentaCorreo
     */

    public void abrirCuenta(CuentaCorreo cuentaCorreo) {
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", cuentaCorreo.getEmail(), cuentaCorreo.getPassword());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para cargar la lista de correos de una carpeta.
     * Limpia los mails que tenemos en la lista de correos para que no sigan apareciendo en el tableview.
     * comprueba que la carpeta tiene mensajes. Si tiene mensajes abre la carpeta en modo lectura escritura, y se guarda todos los mensajes del folder en un array
     * crea un objeto mensaje por cada uno de la lista y lo añade a la lista de correos.
     *
     * @throws MessagingException
     */

    public void listaEmails(Folder folder) {
        Logica.getINSTANCE().listaCorreo.clear();
        // IMAPFolder folder = (IMAPFolder) cuentaCorreo.getStore().getFolder(folderName);
        try {
            if (folder.getType() != 2) {
                if (!folder.isOpen())
                    folder.open(Folder.READ_WRITE);
                listaMensajes = folder.getMessages();
                for (int i = 0; i < listaMensajes.length; i++) {
                    Message mensaje = listaMensajes[i];
                    Emails correo = new Emails(mensaje.getFrom(), mensaje.getSubject(), mensaje.getReceivedDate(), mensaje);
                    Logica.getINSTANCE().cargarCorreo(correo);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que actualiza la tabla de la venta con la lista de los emails por cada carpeta.
     * Comrpueba que se haya seleccionado a alguna carpeta para poder cargar la lista de mails.
     *
     * @param folder
     */
    public void actualizarTabla(Folder folder) {
        if (folder != null) {
            Logica.getINSTANCE().getListaCorreo().clear();
            listaEmails(folder);
        }

    }

    /**
     * Método para crear el árbol con las carpetas de las distintas cuentas.
     * Para que pueda cargar todas las carpetas al abrir la aplicación, accede al método abrir cuenta por cada cuenta que hay en la lista de cuentas, y crea el
     * ItemRoot por cada una de ellas. Para añadir las carpetas se llama al método cargarcarpetas().
     * Para poder cargar más de una cuenta, como solo podemos tener un nodo raíz, se crea una lista en la que se añaden los nodos raíz de cada una de las cuentas.
     * Se crea un Nodo raíz sin valor (dummyRoot) y se añaden como hijos los nodos raíz de cada una de las cuentas, de esta forma, solo tenemos un nodo raíz (dummyRoot)
     *
     * @return
     * @throws MessagingException
     */

    public TreeItem<String> actualizarTree() throws MessagingException {

        for (int i = 0; i < Logica.getINSTANCE().getListaCuentas().size(); i++) {
            abrirCuenta(Logica.getINSTANCE().olistaCuentas.get(i));
            CuentaCorreo cuentaCorreo = Logica.getINSTANCE().olistaCuentas.get(i);
            rootItem = new EmailTreeItem(cuentaCorreo, cuentaCorreo.getEmail(), folder);
            rootItem.getChildren().add(cargaCarpetas(Logica.getINSTANCE().olistaCuentas.get(i)));
            listaTreeitem.add(rootItem);
        }

        TreeItem<String> dummyRoot = new TreeItem<>();
        for (TreeItem treeItem : listaTreeitem) {
            dummyRoot.getChildren().add(treeItem);

        }

        return dummyRoot;
    }

    /**
     * Método que nos devuelve las carpetas de cada una de las cuentas.
     * carga el método getFolder, que devuelve además las subcarpetas de cada carpeta, por lo tanto, llamando a este método
     * obtenemos todas las carpetas y subcarpetas de una cuenta.
     *
     * @param cuentaCorreo
     * @return
     * @throws MessagingException
     */

    public TreeItem cargaCarpetas(CuentaCorreo cuentaCorreo) throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list();
        EmailTreeItem rootItem = new EmailTreeItem(cuentaCorreo, cuentaCorreo.getEmail(), folder);
        rootItem.setExpanded(true);
        getFolder(folders, rootItem, cuentaCorreo);
        return rootItem;
    }

    /**
     * Método que se encarga de cargar las subcarpetas de cada carpeta. Se trata de un método recursivo,
     * si la carpeta contiene más carpetas, se llama de nuevo a este mismo método
     *
     * @param folders
     * @param foldersRoot
     * @param cuentaCorreo
     * @throws MessagingException
     */
    private void getFolder(Folder[] folders, EmailTreeItem foldersRoot, CuentaCorreo cuentaCorreo) throws MessagingException {
        for (Folder folder : folders) {
            EmailTreeItem emailTreeItem = new EmailTreeItem(cuentaCorreo, folder.getName(), folder);
            foldersRoot.getChildren().add(emailTreeItem);
            if (folder.getType() == Folder.HOLDS_FOLDERS) {
                getFolder(folder.list(), emailTreeItem, cuentaCorreo);
            }

        }
    }

    /**
     * Método que saca por pantalla un mensaje.
     * Dado un mensaje, el método verifica si se trata de un mensaje de texto plano o un mensaje HTML
     * el método de vuelve en un String el mensaje que se pasará al Webview de la pantalla principal para poder visualizarlo.
     *
     * @param mensaje
     * @return
     */
    public String leerMensaje(Message mensaje) {
        try {
            MimeMessage mimeMessage = (MimeMessage) mensaje;
            MimeMessageParser parser = new MimeMessageParser(mimeMessage);
            parser.parse();
            String content = parser.getHtmlContent();

            if (content == null) {
                return parser.getPlainContent();

            } else {
                return content;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Método para eliminar un mensaje, si el mensaje está en una carpeta diferente de "trash" se moverá a esta carpeta
     * si el mensaje ya se encuentra en la carpeta "Trash" se elimina completamente.
     *
     * @param message
     */


    public void eliminarMensaje(Message message) {
        if (message != null) {
            Folder folder = message.getFolder();
            store = message.getFolder().getStore();
            try {
                if (!folder.isOpen())
                    folder.open(Folder.READ_WRITE);
                if (!folder.getName().equals("[Gmail]/Trash")) {
                    IMAPFolder folderBasura = (IMAPFolder) store.getFolder("[Gmail]/Trash");
                    folder.copyMessages(new Message[]{message}, folderBasura);
                    folder.close();
                } else {
                    message.setFlag(Flags.Flag.DELETED, true);
                    folder.close();

                }


            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Método que envía un mensaje de texto plano, utilizando la librería de commons.Mail
     *
     * @param usuario
     * @param password
     * @param de
     * @param para
     * @param cC
     * @param bCC
     * @param asunto
     * @param mensaje
     */

    public void emailSet(String usuario, String password, String de, String[] para, String[] cC, String[] bCC,
                         String asunto, String mensaje) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(usuario, password));
            email.setSSLOnConnect(true);
            email.setFrom(de);
            email.addTo(para);
            if (!(cC == null))
                email.addCc(cC);
            if (!(bCC == null))
                email.addBcc(bCC);
            email.setSubject(asunto);
            email.setMsg(mensaje);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

    public void modificarCuenta(CuentaCorreo cuentaCorreo) {
        int posicion = Logica.getINSTANCE().olistaCuentas.indexOf(cuentaCorreo);
        Logica.getINSTANCE().olistaCuentas.set(posicion, cuentaCorreo);
    }
}
