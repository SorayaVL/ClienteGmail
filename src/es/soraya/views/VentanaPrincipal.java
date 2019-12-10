package es.soraya.views;

import es.soraya.logica.CarpetasService;
import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
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
    private ProgressIndicator progressIndicator;


    private Message mensaje;
    private Folder folder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Logica.getINSTANCE().abreFichero();
        if (Logica.getINSTANCE().olistaCuentas.isEmpty()) { // si no tenemos ninguna cuenta almacenada nos carga la venta de añadir una cuenta,
            // si no directamente nos cargará la ventana principal.
            cargarDialogo("VentanaLogin.fxml", 400, 250, "Login");
            abrirDialogo(true, false);
        }

        try {
            progressIndicator.setVisible(false);
            treeFolders.setRoot(GestionCuenta.getINSTANCE().actualizarTree());
            treeFolders.setShowRoot(false);
            treeFolders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> oldValue, TreeItem<String> newValue) {
                    try {
                        if ((((EmailTreeItem) newValue).getFolder()) != null) {
                            CarpetasService carpetasService = new CarpetasService();// crea el servicio para cargar el Thread
                            carpetasService.start();
                            carpetasService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent workerStateEvent) {
                                    GestionCuenta.getINSTANCE().listaEmails((((EmailTreeItem) newValue).getFolder()));
                                    progressIndicator.setVisible(false);
                                }
                            });
                            carpetasService.setOnRunning(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent workerStateEvent) {

                                    progressIndicator.setVisible(true);
                                    progressIndicator.progressProperty().bind(carpetasService.progressProperty());
                                }
                            });

                        }

                        folder = ((EmailTreeItem) newValue).getFolder();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            tvMensajes.setItems(Logica.getINSTANCE().getListaCorreo());
            tvMensajes.setRowFactory(new Callback<TableView<Emails>, TableRow<Emails>>() {
                @Override
                public TableRow<Emails> call(TableView<Emails> emailsTableView) { //Método que nos marcará los mensajes no leídos en negrita
                    return new TableRow<>() {
                        @Override
                        protected void updateItem(Emails emails, boolean b) {
                            super.updateItem(emails, b);
                            if (emails != null) {
                                if (!emails.isRead())
                                    setStyle("-fx-font-weight:bold");
                            } else
                                setStyle("");
                        }
                    };
                }
            });
            WebEngine webEngine;
            webEngine = wvMensaje.getEngine();

            tvMensajes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Emails>() {
                @Override
                public void changed(ObservableValue<? extends Emails> observableValue, Emails emails, Emails email) {
                    try {
                        if (email != null) {
                            String contenido = GestionCuenta.getINSTANCE().leerMensaje(email.getMensaje());
                            webEngine.loadContent(contenido);
                            mensaje = email.getMensaje();
                        } else
                            webEngine.loadContent("");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


            btnEliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GestionCuenta.getINSTANCE().eliminarMensaje(mensaje);
                    GestionCuenta.getINSTANCE().actualizarTabla(folder);
                }
            });

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gestionCuenta(ActionEvent event) {
        cargarDialogo("ModificaCuenta.fxml", 500, 300, "Gestión Cuentas");
        abrirDialogo(true, false);

    }

    @FXML
    void nuevoEmail(ActionEvent event) {
        cargarDialogo("EscribirEmail.fxml", 1000, 800, "Nuevo Mensaje");
        abrirDialogo(true, true);

    }

    @FXML
    void reenviaMensaje(ActionEvent event) throws IOException, MessagingException {
        cargarDialogo("EscribirEmail.fxml", 1000, 800, "Reenviar Mensaje");
        EscribirEmail controller = (EscribirEmail) cargarDialogo("EscribirEmail.fxml", 1000, 800, "Reenviar Mensaje");
        controller.setMensajeReenviar(mensaje);
        abrirDialogo(true, true);

    }

    @FXML
    void respondeMail(ActionEvent event) throws MessagingException {
        cargarDialogo("EscribirEmail.fxml", 1000, 800, "Responder Mensaje");
        EscribirEmail controller = (EscribirEmail) cargarDialogo("EscribirEmail.fxml", 1000, 800, "Responder Mensaje");
        controller.setMensajeResponder(mensaje);
        abrirDialogo(true, true);
    }

    @FXML
    void cambiaVista(ActionEvent event) {
        cargarDialogo("CambiaTema.fxml", 300, 200, "Cambiar Vista");
        abrirDialogo(true, false);

    }


}
