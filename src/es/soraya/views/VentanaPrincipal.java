package es.soraya.views;

import es.soraya.logica.Logica;
import es.soraya.models.Correo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipal implements Initializable{
 @FXML
  private TableView<Correo> tvMensajes;
 @FXML
  private WebView wvMensaje;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvMensajes.setItems(Logica.getINSTANCE().getListaCorreo());
        tvMensajes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Correo>() {
            @Override
            public void changed(ObservableValue<? extends Correo> observableValue, Correo correo, Correo t1) {



            }
        });

    }
}
