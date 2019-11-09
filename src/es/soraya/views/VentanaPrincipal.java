package es.soraya.views;

import es.soraya.models.Correo;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipal extends BaseController implements Initializable{

    public class PleaseProvideControllerClassName {

        @FXML
        private TableView<Correo> tvMensajes;

    }

    public void initialize(URL url, ResourceBundle resourceBundle){



    }

}
