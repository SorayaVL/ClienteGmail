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

import javax.mail.MessagingException;
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
            String email = Logica.getINSTANCE().getListaCuentas().get(0).getEmail();
            String password = Logica.getINSTANCE().getListaCuentas().get(0).getPassword();
            GestionCuenta.getINSTANCE().abrirCuenta(email, password);
            EmailTreeItem root = GestionCuenta.getINSTANCE().cargaCarpetas();
            treeFolders.setRoot(root);
            treeFolders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {
                    try {
                        GestionCuenta.getINSTANCE().listaEmails(t1.getValue());

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
            webEngine.load("https://www.google.es");
            tvMensajes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
