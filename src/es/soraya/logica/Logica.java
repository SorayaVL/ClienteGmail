package es.soraya.logica;

import es.soraya.models.CuentaCorreo;
import es.soraya.models.EmailInforme;
import es.soraya.models.Emails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logica implements Serializable {

    private static Logica INSTANCE = null;
    private List<CuentaCorreo> listaCuentasList = new ArrayList<CuentaCorreo>();
    private File file = new File("cuentas.dat");
    public List<EmailInforme> emailsList = new ArrayList<>();

    public Logica() {
    }

    public static Logica getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Emails> listaCorreo = FXCollections.observableArrayList();

    public ObservableList<CuentaCorreo> getListaCuentas() {
        return olistaCuentas;
    }

    public ObservableList<CuentaCorreo> olistaCuentas = FXCollections.observableArrayList();

    public ObservableList<Emails> getListaCorreo() {
        return listaCorreo;
    }

    public void cargarCorreo(Emails correo) {
        listaCorreo.add(correo);
    }

    public void aniadirCuenta(CuentaCorreo cuentaCorreo) {
        olistaCuentas.add(cuentaCorreo);
    }
    public ObservableList<String> listaHoras = FXCollections.observableArrayList();
    public ObservableList<String> listaMinSegs = FXCollections.observableArrayList();

    public List<EmailInforme> getEmailsList() {
        return emailsList;
    }

    public void setEmailsList(List<EmailInforme> emailsList) {
        this.emailsList = emailsList;
    }

    public void cargaHoras(){
        for (int i= 0 ; i<24; i++){
            listaHoras.add(String.valueOf(i));
        }
    }

    public void cargaMinsSegs(){
        for (int i= 0 ; i<60; i++){
            listaMinSegs.add(String.valueOf(i));
        }
    }


    public void guardarFichero() {
        listaCuentasList = new ArrayList<CuentaCorreo>(olistaCuentas);
        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(file));
            fichero.writeObject(listaCuentasList);
            fichero.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abreFichero() {
        try {
            if (file.length() > 0) {
                ObjectInputStream cargaFichero = new ObjectInputStream(new FileInputStream(file));
                listaCuentasList = (ArrayList) cargaFichero.readObject();
                cargaFichero.close();
                for (CuentaCorreo cuentaCorreo : listaCuentasList) {
                    aniadirCuenta(cuentaCorreo);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void crearEmailsInforme() throws IOException, MessagingException {
        for (Emails emails : listaCorreo){
            String remitente = emails.getFrom();
            String asunto = emails.getSubject();
            String contenido = convertirMessage(emails.getMensaje());
            emailsList.add(new EmailInforme(remitente, asunto, contenido));

        }
    }

    public void addEmailtoReport(EmailInforme emailInforme){
        emailsList.add(emailInforme);
    }

    public void limpiarEmailsList (){
        emailsList.clear();
    }

    /**
     * Método para convertir un objeto tipo Message en tipo String para leerlo en pdf.
     * @param message
     * @return
     * @throws IOException
     * @throws MessagingException
     */

    public String convertirMessage (Message message) throws IOException, MessagingException {
        Multipart multipart = (Multipart) message.getContent();
        BodyPart bodyPart = multipart.getBodyPart(0);
        String content = bodyPart.getContent().toString();
        return content;
    }




}
