package es.soraya.views;

import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class VentanaLogin extends BaseController{

        @FXML
        private Button btnAceptar;

        @FXML
        private TextField tfPassword;

        @FXML
        private TextField tfEmail;

        @FXML
        void salvaCuenta(ActionEvent event) {
                CuentaCorreo cuenta = new CuentaCorreo(tfEmail.getText(), tfPassword.getText());
                Logica.getINSTANCE().aniadirCuenta(cuenta);
                Logica.getINSTANCE().guardarFichero();
                Stage stage= ((Stage)((Node) event.getSource()).getScene().getWindow());
                stage.close();
        }



}
