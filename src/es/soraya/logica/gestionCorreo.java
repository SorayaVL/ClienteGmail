package es.soraya.logica;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class gestionCorreo {

public void abrirCuenta() throws MessagingException {
    Store store = null;

    Properties props = System.getProperties();
    props.setProperty("mail.store.protocol", "imaps");

    Session session = Session.getDefaultInstance(props, null);

    store = session.getStore("imaps");
    store.connect("imap.googlemail.com","sorayadamdi@gmail.com", "Estrella3.");
}

public void listaCorreos(Store store) throws MessagingException, IOException {
    IMAPFolder folder = null;
    String subject = null;

    folder = (IMAPFolder) store.getFolder("inbox");


    if(!folder.isOpen())
        folder.open(Folder.READ_WRITE);
    Message[] messages = folder.getMessages();
    System.out.println("No of Messages : " + folder.getMessageCount());
    System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
    System.out.println(messages.length);
    for (int i=0; i < messages.length;i++)
    {

        System.out.println("*****************************************************************************");
        System.out.println("MESSAGE " + (i + 1) + ":");
        Message msg =  messages[i];
        //System.out.println(msg.getMessageNumber());
        //Object String;
        //System.out.println(folder.getUID(msg)

        subject = msg.getSubject();

        System.out.println("Subject: " + subject);
        System.out.println("From: " + msg.getFrom()[0]);
        System.out.println("To: "+msg.getAllRecipients()[0]);
        System.out.println("Date: "+msg.getReceivedDate());
        System.out.println("Size: "+msg.getSize());
        System.out.println(msg.getFlags());
        System.out.println("Body: \n"+ msg.getContent());
        System.out.println(msg.getContentType());

    }
}
}


