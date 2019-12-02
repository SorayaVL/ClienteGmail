package es.soraya.views;

import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionCuenta extends BaseController implements Initializable{
    @FXML
    private TableView<CuentaCorreo> tvCuentas;


    @FXML
    void aniadirCuenta(ActionEvent event) {

    }

    @FXML
    void eliminarCuenta(ActionEvent event) {


    }

    @FXML
    void modificar(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvCuentas.setItems(Logica.getINSTANCE().listaCuentas);
    }
}
