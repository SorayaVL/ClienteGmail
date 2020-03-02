package es.soraya.views;

import com.soraya.Reloj;
import com.soraya.SettingAlarm;
import com.soraya.Tarea;
import es.soraya.logica.Logica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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
    private TableView<Tarea> tvTareas;

    @FXML
    private ComboBox<String> cbHora;

    @FXML
    private ComboBox<String> cbMin;

    @FXML
    private ComboBox<String> cbSeg;


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
        String horaTarea =cbHora.getSelectionModel().getSelectedItem()+":"+cbMin.getSelectionModel().getSelectedItem()+":"+cbSeg.getSelectionModel().getSelectedItem();
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
        Logica.getINSTANCE().cargaHoras();
        Logica.getINSTANCE().cargaMinsSegs();
        cbHora.setItems(Logica.getINSTANCE().listaHoras);
        cbMin.setItems(Logica.getINSTANCE().listaMinSegs);
        cbSeg.setItems(Logica.getINSTANCE().listaMinSegs);
        limpiaVentana();
        reloj.comienza();

    }

    private void limpiaVentana(){
        tfDescripcion.setText("");
        cbHora.getSelectionModel().select(12);
        cbMin.getSelectionModel().select(30);
        cbSeg.getSelectionModel().select(30);
    }


}
