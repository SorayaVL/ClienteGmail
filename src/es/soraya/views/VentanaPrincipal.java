package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
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
    private ArrayList<Message> listMensajes = new ArrayList<Message>();
    private Message mensaje;
    private Message[] listaMensajes;
    private CuentaCorreo cuentaCorreo;
    private Folder folder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Logica.getINSTANCE().ListaCuentas.isEmpty()) {
            cargarDialogo("VentanaLogin.fxml", false,400, 250);
            abrirDialogo(true);
        }

        try {
            GestionCuenta.getINSTANCE().abrirCuenta(Logica.getINSTANCE().getListaCuentas().get(0));
            EmailTreeItem root = GestionCuenta.getINSTANCE().cargaCarpetas(Logica.getINSTANCE().getListaCuentas().get(0));
            treeFolders.setRoot(root);
            treeFolders.setShowRoot(true);
            treeFolders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> oldValue, TreeItem<String> newValue) {
                    try {
                        if ((((EmailTreeItem) newValue).getFolder())!=null)
                        GestionCuenta.getINSTANCE().listaEmails((((EmailTreeItem) newValue).getFolder()).getFullName(), Logica.getINSTANCE().ListaCuentas.get(0));
                        cuentaCorreo = Logica.getINSTANCE().ListaCuentas.get(0);
                        folder= ((EmailTreeItem) newValue).getFolder();
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
                            webEngine.loadContent(GestionCuenta.getINSTANCE().leerMensaje(email.getMensaje()));
                            mensaje=email.getMensaje();
                        }

                        else
                            webEngine.loadContent("");

                  } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

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






}
