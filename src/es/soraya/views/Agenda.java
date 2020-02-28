package es.soraya.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Agenda extends BaseController implements Initializable {

    @FXML
    private Spinner<Integer> hora = new Spinner<Integer>();
    private int initialValue= 12;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initialValue);
        hora.setValueFactory(valueFactory);
    }


    @FXML
    void getTarea(ActionEvent event) {
        System.out.println(String.valueOf(hora.getValueFactory()));

    }

}
