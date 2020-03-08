package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.Carpeta;
import es.soraya.models.CuentaCorreo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.mail.Folder;
import javax.mail.MessagingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Informes extends BaseController implements Initializable {

    @FXML
    private ComboBox<CuentaCorreo> cbCuenta;

    @FXML
    private TableView<Carpeta> tvCarpetas;

    private Folder folder;

    @FXML
    void generaInforme(ActionEvent event) {
        folder=tvCarpetas.getSelectionModel().getSelectedItem().getFolder();
        GestionCuenta.getINSTANCE().emailsCarpetaInforme(folder);
        JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(Logica.getINSTANCE().emailsFolderList);
        try {
            Map parametros = new HashMap();
            parametros.put("NOMBRECARPETA", folder.getName());

            JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/soraya/jasper/EmailsCarpeta.jasper"), parametros, jrds);
          /*  JasperPrint jasperPrint = JasperFillManager.fillReport(
                    getClass().getResourceAsStream("/es/soraya/jasper/VistaEmail.jasper"),
                    parametros, jrds);*/
            JasperExportManager.exportReportToPdfFile(jasperPrint, "informessalida/reportCarpeta.pdf");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Informe generado");
            alert.setContentText("El correo se ha guardado a pdf");
            alert.show();
        } catch (Throwable e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error generando el informe");
            alert.setContentText("Ha ocurrido un error generando el informe");
            alert.show();
        }




    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCuenta.setItems(Logica.getINSTANCE().getListaCuentas());
        cbCuenta.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CuentaCorreo>() {
            @Override
            public void changed(ObservableValue<? extends CuentaCorreo> observableValue, CuentaCorreo cuentaCorreo, CuentaCorreo t1) {
                try {
                    GestionCuenta.getINSTANCE().rellenaCarpetas(t1);
                    tvCarpetas.setItems(Logica.getINSTANCE().listaCarpetas);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });



    }





   /*@FXML
    void listamails(ActionEvent event) throws IOException, MessagingException {
       Logica.getINSTANCE().crearEmailsInforme();
       JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(Logica.getINSTANCE().emailsList);
       try {
           JasperPrint jasperPrint = JasperFillManager.fillReport(
                   getClass().getResourceAsStream("/es/soraya/jasper/VistaEmail.jasper"),
                   new HashMap<String, Object>(), jrds);
           JasperExportManager.exportReportToPdfFile(jasperPrint, "informessalida/report.pdf");
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Informe generado");
           alert.setContentText("El correo se ha guardado a pdf");
           alert.show();
       } catch (Throwable e) {
           e.printStackTrace();
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("ERROR");
           alert.setHeaderText("Error generando el informe");
           alert.setContentText("Ha ocurrido un error generando el informe");
           alert.show();
       }

   }*/


}