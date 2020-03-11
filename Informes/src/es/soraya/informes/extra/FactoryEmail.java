package es.soraya.informes.extra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FactoryEmail {

    public static List<EmailInforme> createListEmails()
    {
        List<EmailInforme> emailsList = new ArrayList<>();
        EmailInforme emailInforme=new EmailInforme("Remitente1","Asunto1","Contenido1");
        EmailInforme emailInforme2=new EmailInforme("Remitente2","Asunto2","Contenido2");
        EmailInforme emailInforme3=new EmailInforme("Remitente3","Asunto3","Contenido4");
        emailsList.add(emailInforme);
        emailsList.add(emailInforme2);
        emailsList.add(emailInforme3);
        return emailsList;
    }

    public static List<EmailsFolder> listaEmailsFolder()
    {
        List<EmailsFolder> emailsFoldersList = new ArrayList<>();
        emailsFoldersList.add(new EmailsFolder("Carpeta1", "Remitente1", "Asunto1", new Date()));
        emailsFoldersList.add(new EmailsFolder("Carpeta2", "Remitente2", "Asunto2", new Date()));
        emailsFoldersList.add(new EmailsFolder("Carpeta3", "Remitente3", "Asunto3", new Date()));
        emailsFoldersList.add(new EmailsFolder("Carpeta4", "Remitente4", "Asunto4", new Date()));

        return emailsFoldersList;
    }

    public static List<EmailsFolder> listaEmailsAgrupados(){
        List<EmailsFolder> listaAgrupada = new ArrayList<>();
        listaAgrupada.add(new EmailsFolder("Carpeta1", "Remitente1", "Asunto1", new Date(), "contenido1"));
        listaAgrupada.add(new EmailsFolder("Carpeta1", "Remitente2", "Asunto2", new Date(), "contenido2"));
        listaAgrupada.add(new EmailsFolder("Carpeta1", "Remitente3", "Asunto3", new Date(), "contenido3"));
        listaAgrupada.add(new EmailsFolder("Carpeta2", "Remitente1", "Asunto1", new Date(), "contenido1"));
        listaAgrupada.add(new EmailsFolder("Carpeta2", "Remitente2", "Asunto2", new Date(), "contenido2"));
        listaAgrupada.add(new EmailsFolder("Carpeta2", "Remitente3", "Asunto3", new Date(), "contenido3"));
        listaAgrupada.add(new EmailsFolder("Carpeta3", "Remitente1", "Asunto1", new Date(), "contenido1"));
        listaAgrupada.add(new EmailsFolder("Carpeta3", "Remitente2", "Asunto2", new Date(), "contenido2"));
        listaAgrupada.add(new EmailsFolder("Carpeta3", "Remitente3", "Asunto3", new Date(), "contenido3"));

        return listaAgrupada;
    }

}
