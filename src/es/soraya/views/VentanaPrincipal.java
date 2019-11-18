package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipal extends BaseController implements Initializable {
    @FXML
    private TableView<Emails> tvMensajes;
    @FXML
    private WebView wvMensaje;
    @FXML
    private TreeView<String> treeFolders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Logica.getINSTANCE().ListaCuentas.isEmpty()) {
            cargarDialogo("VentanaLogin.fxml", false,400, 250);
            abrirDialogo(true);
        }

        try {
            /*String email = Logica.getINSTANCE().getListaCuentas().get(0).getEmail();
            String password = Logica.getINSTANCE().getListaCuentas().get(0).getPassword();*/
            GestionCuenta.getINSTANCE().abrirCuenta(Logica.getINSTANCE().getListaCuentas().get(0));
            EmailTreeItem root = GestionCuenta.getINSTANCE().cargaCarpetas(Logica.getINSTANCE().getListaCuentas().get(0));
            treeFolders.setRoot(root);
            treeFolders.setShowRoot(false);
            treeFolders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {

                    try {
                       if (t1.getValue().equalsIgnoreCase("INBOX"))
                        GestionCuenta.getINSTANCE().listaEmails(t1.getValue());
                       else GestionCuenta.getINSTANCE().listaEmails("[Gmail]/"+t1.getValue());

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
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
                        webEngine.loadContent(GestionCuenta.getINSTANCE().leerMensaje(email.getMensaje()));

                  } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
