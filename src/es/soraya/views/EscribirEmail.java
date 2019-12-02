package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static es.soraya.logica.Logica.getINSTANCE;

public class EscribirEmail extends BaseController implements Initializable {

        @FXML
        private HTMLEditor htmlMensaje;

        @FXML
        private ComboBox<String> cbDe;

        @FXML
        private TextField tfPara;

        @FXML
        private TextField tfAsunto;


    @FXML
    private TextField tfCC;

    @FXML
    private TextField tfCCO;


    ObservableList<String> nombreCuenta= FXCollections.observableArrayList();
    private String password;
    private String usuario;

        public void initialize (URL url, ResourceBundle resourceBundle){
            cargaCombo();
            if (nombreCuenta!=null)
                cbDe.setItems(nombreCuenta);
            cbDe.getSelectionModel().selectFirst();

        }

        private ObservableList<String> cargaCombo() {
        for (int i = 0; i< getINSTANCE().listaCuentas.size(); i++){
            nombreCuenta.add(getINSTANCE().listaCuentas.get(i).getEmail());
            usuario=getINSTANCE().listaCuentas.get(i).getEmail();
            password=getINSTANCE().listaCuentas.get(i).getPassword();
        }
        return nombreCuenta;
    }



    @FXML
    void enviarEmail(ActionEvent event) {
            String cuerpoMail = pasaTexto(htmlMensaje.getHtmlText());
            String [] para = tfPara.getText().split(Pattern.quote(","));
        String [] cC=null;
        String [] cCO=null;
        System.out.println(tfCC.getText());
        System.out.println(tfCCO.getText());
        if (tfCC.getText()!="")
            cC= tfCC.getText().split(Pattern.quote(","));
        if (tfCCO.getText()!="")
            cCO = tfCCO.getText().split(Pattern.quote(","));
           GestionCuenta.getINSTANCE().emailSet
                    (usuario, password,cbDe.getSelectionModel().getSelectedItem(), para, cC, cCO,
                            tfAsunto.getText(),cuerpoMail);
        Stage stage = ((Stage)((Node) event.getSource()).getScene().getWindow());
        stage.close();
          }

    private static String pasaTexto(String htmlTexto) {
        String resultado;
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlTexto);
        final StringBuffer text = new StringBuffer(htmlTexto.length());

        while (matcher.find()) {
            matcher.appendReplacement(text, " ");
        }

        matcher.appendTail(text);
        resultado = text.toString().trim();
        return resultado;
    }




}
