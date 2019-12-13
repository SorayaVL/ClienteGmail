package es.soraya.views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CambiaTema extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> temaCB;


    @FXML
    void cambiaTema(ActionEvent event) {
        if (temaCB.getSelectionModel().getSelectedItem() != null)
            Application.setUserAgentStylesheet(temaCB.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temaCB.getItems().addAll(Application.STYLESHEET_CASPIAN, Application.STYLESHEET_MODENA);
    }

}
