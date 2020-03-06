package es.soraya.informes.extra;

import java.util.ArrayList;
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

}
