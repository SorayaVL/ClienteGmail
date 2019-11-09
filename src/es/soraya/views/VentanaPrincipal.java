package es.soraya.views;

import es.soraya.logica.Logica;
import es.soraya.models.Correo;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipal extends BaseController implements Initializable{
 @FXML
  private TableView<Correo> tvMensajes;


    public void initialize(URL url, ResourceBundle resourceBundle){
        tvMensajes.setItems((ObservableList<Correo>) Logica.getINSTANCE().getListaCorreo());



    }

}
