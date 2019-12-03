package es.soraya.views;

/*Javadoc offline

Descargar la carpeta con el Javadoc en el Git aparece.
Dentro de la carpeta del Javadoc seleccionar todas las carpetas por separado dar OK y decir que es Javadoc.*/

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.mail.Email;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VentanaPrincipal extends BaseController implements Initializable {
    @FXML
    private TableView<Emails> tvMensajes;
    @FXML
    private WebView wvMensaje;
    @FXML
    private TreeView<String> treeFolders;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEscribir;

    private Message mensaje;
    private CuentaCorreo cuentaCorreo;
    private Folder folder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Logica.getINSTANCE().abreFichero();
        if (Logica.getINSTANCE().olistaCuentas.isEmpty()) {
            cargarDialogo("VentanaLogin.fxml", 400, 250, "Login");
            abrirDialogo(true, false);
        }

        try {
            for (int i = 0; i < Logica.getINSTANCE().olistaCuentas.size(); i++) {
                int valor = i;
                GestionCuenta.getINSTANCE().abrirCuenta(Logica.getINSTANCE().getListaCuentas().get(valor));
                EmailTreeItem root = GestionCuenta.getINSTANCE().cargaCarpetas(Logica.getINSTANCE().getListaCuentas().get(valor));
                treeFolders.setRoot(root);
                treeFolders.setShowRoot(true);
                treeFolders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                    @Override
                    public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> oldValue, TreeItem<String> newValue) {
                        try {
                            if ((((EmailTreeItem) newValue).getFolder()) != null)
                                GestionCuenta.getINSTANCE().listaEmails((((EmailTreeItem) newValue).getFolder()).getFullName(), Logica.getINSTANCE().olistaCuentas.get(valor));
                            cuentaCorreo = Logica.getINSTANCE().olistaCuentas.get(valor);
                            folder = ((EmailTreeItem) newValue).getFolder();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                });

            tvMensajes.setItems(Logica.getINSTANCE().getListaCorreo());
            WebEngine webEngine;
            webEngine = wvMensaje.getEngine();

            tvMensajes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Emails>() {
                @Override
                public void changed(ObservableValue<? extends Emails> observableValue, Emails emails, Emails email) {
                    try {
                        if (email!=null){
                            String contenido = GestionCuenta.getINSTANCE().leerMensaje(email.getMensaje());
                            webEngine.loadContent(contenido);
                            mensaje= email.getMensaje();
                        }
                        else
                            webEngine.loadContent("");

                  } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        btnEliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GestionCuenta.getINSTANCE().eliminarMensaje(mensaje, cuentaCorreo, folder);
            }
        });

    }

    @FXML
    void gestionCuenta(ActionEvent event) {
        cargarDialogo("ModificaCuenta.fxml",500, 300, "Gestión Cuentas");
        abrirDialogo(true, false);

    }

    @FXML
    void nuevoEmail(ActionEvent event) {
        cargarDialogo("EscribirEmail.fxml",1000,800, "Nuevo Mensaje");
        abrirDialogo(true, true);

    }

    @FXML
    void reenviaMensaje(ActionEvent event) throws IOException, MessagingException {
        cargarDialogo("EscribirEmail.fxml",1000, 800, "Reenviar Mensaje");
        EscribirEmail controller = (EscribirEmail)cargarDialogo("EscribirEmail.fxml",1000, 800, "Reenviar Mensaje");
        controller.setMensajeReenviar(mensaje);
        abrirDialogo(true,true);

    }

    @FXML
    void respondeMail(ActionEvent event) throws MessagingException {
        cargarDialogo("EscribirEmail.fxml",1000,800, "Responder Mensaje");
        EscribirEmail controller = (EscribirEmail)cargarDialogo("EscribirEmail.fxml",1000, 800, "Responder Mensaje");
        controller.setMensajeResponder(mensaje);
        abrirDialogo(true, true);
    }



}
