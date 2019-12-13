package es.soraya.views;

import es.soraya.logica.Logica;
import es.soraya.models.Emails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javax.mail.Folder;
import java.net.URL;
import java.util.ResourceBundle;

public class CargaInbox extends BaseController implements Initializable {

    @FXML
    private ComboBox<Emails> cbCorreo;

    @FXML
    private Button btnAceptar;


    Folder folder;

    @FXML
    void mostrarAsunto(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Asunto");
        alert.setHeaderText("Asunto");
        alert.setContentText("");
        alert.show();

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           cbCorreo.setItems(Logica.getINSTANCE().getListaCorreo());


    }
}
