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
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.mail.Folder;
import javax.mail.MessagingException;
import java.io.File;
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

    private CuentaCorreo cuentaCorreo;


    @FXML
    void generaInforme(ActionEvent event) {
        if (tvCarpetas.getSelectionModel().getSelectedItem() != null)
            folder = tvCarpetas.getSelectionModel().getSelectedItem().getFolder();
        if (folder != null) {
            Logica.getINSTANCE().emailsFolderList.clear();
            GestionCuenta.getINSTANCE().emailsCarpetaInforme(folder);
            JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(Logica.getINSTANCE().emailsFolderList);
            try {
                Map parametros = new HashMap();
                parametros.put("NOMBRECARPETA", folder.getName());

                JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/soraya/jasper/EmailsCarpeta.jasper"), parametros, jrds);
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(stage); //o File file = fileChooser.showOpenDialog(stage);
                //JasperExportManager.exportReportToPdfFile(jasperPrint, "informessalida/reportCarpeta.pdf");
                if (file != null) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Informe generado");
                    alert.setContentText("El correo se ha guardado a pdf");
                    alert.show();
                }

            } catch (Throwable e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error generando el informe");
                alert.setContentText("Ha ocurrido un error generando el informe");
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Seleccione una carpeta");
            alert.setHeaderText("No se ha seleccionado ninguna carpeta");
            alert.setContentText("Debe seleccionar una carpeta para generar el informe");
            alert.show();
        }

    }

    @FXML
    void informeAllEmails(ActionEvent event) throws MessagingException {
        cuentaCorreo = cbCuenta.getSelectionModel().getSelectedItem();
        if (cuentaCorreo!=null){
            GestionCuenta.getINSTANCE().listaEmailsCuenta(cuentaCorreo);
            creaInforme("allEmails.jasper");

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Seleccione una cuenta de correo");
            alert.setHeaderText("No se ha seleccionado ninguna cuenta");
            alert.setContentText("Debe seleccionar una cuenta para generar el informe");
            alert.show();
        }

    }

    public void creaInforme (String nombreInforme){
          JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(Logica.getINSTANCE().emailsFolderList);
            try {
                JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/soraya/jasper/"+nombreInforme),
                        new HashMap<String, Object>(), jrds);
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(stage); //
                if (file != null) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Informe generado");
                    alert.setHeaderText("Correo guardado");
                    alert.setContentText("El correo se ha guardado a pdf");
                    alert.show();
                }


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

}