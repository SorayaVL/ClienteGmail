package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;


public class VentanaLogin extends BaseController implements Initializable, Serializable {

    private CuentaCorreo cuentaCorreo;

    @FXML
    private Button btnAceptar;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    void salvaCuenta(ActionEvent event) {
        if (cuentaCorreo != null) {
            cuentaCorreo.setEmail(tfEmail.getText());
            cuentaCorreo.setPassword(tfPassword.getText());
            GestionCuenta.getINSTANCE().modificarCuenta(cuentaCorreo);
        }
        CuentaCorreo cuenta = new CuentaCorreo(tfEmail.getText(), tfPassword.getText());
        Logica.getINSTANCE().aniadirCuenta(cuenta);
        Logica.getINSTANCE().guardarFichero();
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }


    public void setCuentaModificar(CuentaCorreo cuentaCorreo) {
        this.cuentaCorreo = cuentaCorreo;
        tfEmail.setText(cuentaCorreo.getEmail());
        tfPassword.setText(cuentaCorreo.getPassword());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       /* ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(tfEmail, Validator.createEmptyValidator("Inserte el email"));
        validationSupport.registerValidator(tfPassword, Validator.createEmptyValidator("Inserte la contraseña"));*/


        System.out.println("Estoy en el metodo initialize de Ventana Login");
    }
}
