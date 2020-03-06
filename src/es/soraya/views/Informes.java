package es.soraya.views;

import es.soraya.logica.Logica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Informes extends BaseController implements Initializable {


    @FXML
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
