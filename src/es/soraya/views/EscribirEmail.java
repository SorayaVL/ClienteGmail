package es.soraya.views;

import es.soraya.logica.GestionCuenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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


    ObservableList<String> nombreCuenta = FXCollections.observableArrayList();
    private String password;
    private String usuario;
    private Message mensaje;
    private String cuerpoMail;
    private String[] para;
    String[] cC=null;
    String[] cCO=null;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargaCombo();
        if (nombreCuenta != null)
            cbDe.setItems(nombreCuenta);
        cbDe.getSelectionModel().selectFirst();

    }

    private ObservableList<String> cargaCombo() {
        for (int i = 0; i < getINSTANCE().olistaCuentas.size(); i++) {
            nombreCuenta.add(getINSTANCE().olistaCuentas.get(i).getEmail());
            usuario = getINSTANCE().olistaCuentas.get(i).getEmail();
            password = getINSTANCE().olistaCuentas.get(i).getPassword();
        }
        return nombreCuenta;
    }


    @FXML
    void enviarEmail(ActionEvent event) {
        nuevoMensaje();
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    public void setMensajeReenviar(Message mensajeReenviar) throws IOException, MessagingException {
        if (mensajeReenviar!=null){
            tfAsunto.setText("RE: "+mensajeReenviar.getSubject());
            htmlMensaje.setHtmlText(cuerpoMail= GestionCuenta.getINSTANCE().leerMensaje(mensajeReenviar));
        }

    }

    public void setMensajeResponder (Message mensajeResponder) throws MessagingException {
        if (mensajeResponder!=null){
            tfPara.setText(Arrays.toString(mensajeResponder.getFrom()));
            tfAsunto.setText("RE: "+mensajeResponder.getSubject());
            htmlMensaje.setHtmlText(cuerpoMail= GestionCuenta.getINSTANCE().leerMensaje(mensajeResponder));
        }

    }


    private void nuevoMensaje(){
         cuerpoMail = pasaTexto(htmlMensaje.getHtmlText());
         para= tfPara.getText().split(Pattern.quote(","));
        if (!tfCC.getText().equals(""))
            cC = tfCC.getText().split(Pattern.quote(","));
        if (!tfCCO.getText().equals(""))
            cCO = tfCCO.getText().split(Pattern.quote(","));
        GestionCuenta.getINSTANCE().emailSet
                (usuario, password, cbDe.getSelectionModel().getSelectedItem(), para, cC, cCO,
                        tfAsunto.getText(), cuerpoMail);

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
