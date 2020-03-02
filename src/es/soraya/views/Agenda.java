package es.soraya.views;

import com.soraya.Reloj;
import com.soraya.SettingAlarm;
import com.soraya.Tarea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Agenda extends BaseController implements Initializable {

    @FXML
    private TextField tfDescripcion;

    @FXML
    private TextField tfhora;

    @FXML
    private TextField tfminutos;

    @FXML
    private TextField tfSegundos;

    @FXML
    private TableView<Tarea> tvTareas;

    @FXML
    private Reloj reloj;

    private Tarea tarea;

    @FXML
    void deleteTarea(ActionEvent event) {
        tarea=tvTareas.getSelectionModel().getSelectedItem();
        reloj.borrarTarea(tarea);

    }

    @FXML
    void getTarea(ActionEvent event) throws ParseException {
        String horaTarea = tfhora.getText()+":"+tfminutos.getText()+":"+tfSegundos.getText();
        System.out.println(horaTarea);
        tvTareas.setItems(reloj.getTareaList());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = simpleDateFormat.parse(horaTarea);
        tarea = new Tarea(tfDescripcion.getText(), date);
        reloj.registrarTarea(tarea);
        limpiaVentana();
        reloj.addSettingAlarm(new SettingAlarm() {
            @Override
            public void ejecuta(Tarea tarea) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Agenda");
                alert.setHeaderText("Nuevo aviso");
                alert.setContentText(horaTarea+": "+tarea.toString());
                alert.show();

            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloj.comienza();

    }

    private void limpiaVentana(){
        tfDescripcion.setText("");

    }


}
