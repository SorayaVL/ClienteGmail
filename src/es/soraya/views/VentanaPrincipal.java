package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
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
            cargarDialogo("VentanaLogin.fxml", 500, 250);
            abrirDialogo(true);
        }

        try {
            String email = Logica.getINSTANCE().getListaCuentas().get(0).getEmail();
            String password = Logica.getINSTANCE().getListaCuentas().get(0).getPassword();
            GestionCuenta.getINSTANCE().abrirCuenta(email, password);
            GestionCuenta.getINSTANCE().listaEmails();
            EmailTreeItem root = GestionCuenta.getINSTANCE().cargaCarpetas();
            treeFolders.setRoot(root);
            //treeFolders=new TreeView<>(String)(GestionCuenta.getINSTANCE().cargaCarpetas());
            tvMensajes.setItems(Logica.getINSTANCE().getListaCorreo());
            tvMensajes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                }
            });
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }


    }
}
