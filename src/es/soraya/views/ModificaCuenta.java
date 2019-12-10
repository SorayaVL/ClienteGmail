package es.soraya.views;

import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class ModificaCuenta extends BaseController implements Initializable, Serializable {
    @FXML
    private TableView<CuentaCorreo> tvCuentas;

    @FXML
    void aniadirCuenta(ActionEvent event) {
        cargarDialogo("VentanaLogin.fxml", 400, 250, "Añadir Cuenta");
        abrirDialogo(true, false);
        Logica.getINSTANCE().guardarFichero();
    }

    @FXML
    void eliminarCuenta(ActionEvent event) {
        Logica.getINSTANCE().olistaCuentas.remove(tvCuentas.getSelectionModel().getSelectedIndex());
        Logica.getINSTANCE().guardarFichero();
    }

    @FXML
    void modificar(ActionEvent event) {
        VentanaLogin controller = (VentanaLogin) cargarDialogo("VentanaLogin.fxml", 400, 250, "Modificar Cuenta");
        CuentaCorreo cuentaCorreo = tvCuentas.getSelectionModel().getSelectedItem();
        controller.setCuentaModificar(cuentaCorreo);
        abrirDialogo(true, false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvCuentas.setItems(Logica.getINSTANCE().olistaCuentas);
    }
}
