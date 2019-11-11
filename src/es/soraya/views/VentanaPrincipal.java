package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.Correo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipal extends BaseController implements Initializable{
 @FXML
  private TableView<Correo> tvMensajes;
 @FXML
  private WebView wvMensaje;

     @Override
    public void initialize  (URL url, ResourceBundle resourceBundle) {
         if(Logica.getINSTANCE().ListaCuentas.isEmpty()){
             cargarDialogo("VentanaLogin.fxml", 500, 250);
             abrirDialogo(true);
         }

         try {
             String email= Logica.getINSTANCE().ListaCuentas.get(0).getEmail();
             String password = Logica.getINSTANCE().ListaCuentas.get(0).getPassword();
             GestionCuenta.getINSTANCE().abrirCuenta(email, password);
             GestionCuenta.getINSTANCE().obtenerCarpetas();
             tvMensajes.setItems(Logica.getINSTANCE().getListaCorreo());
         } catch (MessagingException | IOException e) {
             e.printStackTrace();
         }




    }
}
