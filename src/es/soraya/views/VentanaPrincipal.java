package es.soraya.views;

import es.soraya.logica.CarpetasService;
import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.EmailInforme;
import es.soraya.models.EmailTreeItem;
import es.soraya.models.Emails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.docgene.help.JavaHelpFactory;
import org.docgene.help.gui.jfx.JFXHelpContentViewer;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class VentanaPrincipal extends BaseController implements Initializable {

    @FXML
    private WebView wvMensaje;
    @FXML
    private TreeView<String> treeFolders;
    @FXML
    private Button btnEliminar;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TableView<Emails> tvMensajes;

    @FXML
    private Button btnAyunda;

    private JFXHelpContentViewer viewer;

    private Message mensaje;

    public Folder getFolder() {
        return folder;
    }

    private Folder folder;
    private Emails emailSelected;
    private  WebEngine webEngine;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Logica.getINSTANCE().abreFichero();
        if (Logica.getINSTANCE().olistaCuentas.isEmpty()) {// si no tenemos ninguna cuenta almacenada nos carga la ventana de añadir una cuenta,
            // si no directamente nos cargará la ventana principal.
            cargarDialogo("VentanaLogin.fxml", 400, 250, "Login");
            abrirDialogo(true, false);
        }

        try {
            progressIndicator.setVisible(false);
            treeFolders.setRoot(GestionCuenta.getINSTANCE().actualizarTree());
            treeFolders.setShowRoot(false);
            treeFolders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> oldValue, TreeItem<String> newValue) {
                    try {
                        folder = ((EmailTreeItem) newValue).getFolder();
                        if (folder!= null) {
                            CarpetasService carpetasService = new CarpetasService();// crea el servicio para cargar el Thread
                            carpetasService.start();
                            carpetasService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent workerStateEvent) {
                                    GestionCuenta.getINSTANCE().listaEmails(folder);
                                    progressIndicator.setVisible(false);
                                }
                            });
                            carpetasService.setOnRunning(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent workerStateEvent) {
                                    progressIndicator.setVisible(true);
                                    progressIndicator.progressProperty().bind(carpetasService.progressProperty());
                                }
                            });

                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

           tvMensajes.setItems(Logica.getINSTANCE().getListaCorreo());
            tvMensajes.setRowFactory(new Callback<TableView<Emails>, TableRow<Emails>>() {
                @Override
                public TableRow<Emails> call(TableView<Emails> emailsTableView) { //Método que nos marcará los mensajes no leídos en negrita
                    return new TableRow<>() {
                        @Override
                        protected void updateItem(Emails emails, boolean b) {
                            super.updateItem(emails, b);
                            if (emails != null) {
                                if (!emails.isRead())
                                    setStyle("-fx-font-weight:bold");
                            } else
                                setStyle("");
                        }
                    };
                }
            });

            webEngine = wvMensaje.getEngine();

            tvMensajes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Emails>() {
                @Override
                public void changed(ObservableValue<? extends Emails> observableValue, Emails emails, Emails email) {
                    try {
                        if (email != null) {
                            String contenido = GestionCuenta.getINSTANCE().leerMensaje(email.getMensaje());
                             emailSelected= email;
                            webEngine.loadContent(contenido);
                            mensaje = email.getMensaje();
                        } else
                            webEngine.loadContent("");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });



            btnEliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GestionCuenta.getINSTANCE().eliminarMensaje(mensaje);
                    GestionCuenta.getINSTANCE().actualizarTabla(folder);
                }
            });

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gestionCuenta(ActionEvent event) {
        cargarDialogo("ModificaCuenta.fxml", 500, 300, "Gestión Cuentas");
        abrirDialogo(true, false);

    }

    @FXML
    void nuevoEmail(ActionEvent event) {
        cargarDialogo("EscribirEmail.fxml", 1000, 800, "Nuevo Mensaje");
        abrirDialogo(true, true);

    }

    @FXML
    void reenviaMensaje(ActionEvent event) throws IOException, MessagingException {
        cargarDialogo("EscribirEmail.fxml", 1000, 800, "Reenviar Mensaje");
        EscribirEmail controller = (EscribirEmail) cargarDialogo("EscribirEmail.fxml", 1000, 800, "Reenviar Mensaje");
        controller.setMensajeReenviar(mensaje);
        abrirDialogo(true, true);

    }

    @FXML
    void respondeMail(ActionEvent event) throws MessagingException {
        cargarDialogo("EscribirEmail.fxml", 1000, 800, "Responder Mensaje");
        EscribirEmail controller = (EscribirEmail) cargarDialogo("EscribirEmail.fxml", 1000, 800, "Responder Mensaje");
        controller.setMensajeResponder(mensaje);
        abrirDialogo(true, true);
    }

    @FXML
    void cambiaVista(ActionEvent event) {
        cargarDialogo("CambiaTema.fxml", 300, 200, "Cambiar Vista");
        abrirDialogo(true, false);

    }

    @FXML
    void cargarInbox(ActionEvent event) {
        cargarDialogo("CargaInbox.fxml", 500, 200, "Cargar Inbox");
        abrirDialogo(true, false);


    }


    @FXML
    void abreAgenda(ActionEvent event) {
        cargarDialogo("Agenda.fxml", 600,400, "Agenda");
        stage.initModality(Modality.NONE);
        abrirDialogo(false, false);


    }

    @FXML
    void abreInformes(ActionEvent event) {

        cargarDialogo("VentanaInformes.fxml", 600, 400 , "Informes");
        abrirDialogo(true, false);

    }
    @FXML
    void listamails(ActionEvent event) throws IOException, MessagingException {
        if (emailSelected!=null){
            Logica.getINSTANCE().limpiarEmailsList();
            EmailInforme emailInforme = new EmailInforme (emailSelected.getFrom(), emailSelected.getSubject(), Logica.getINSTANCE().convertirMessage(emailSelected.getMensaje()));
            Logica.getINSTANCE().addEmailtoReport(emailInforme);
            JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(Logica.getINSTANCE().getEmailsList());
            try {
                JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/soraya/jasper/emailGmail.jasper"),
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
                    emailSelected=null;
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

    }
    private void initializeHelp(Stage stage)
    {
        try {
            URL url = this.getClass().getResource("/help/articles.zip");
            JavaHelpFactory factory = new JavaHelpFactory(url);
            factory.create();
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);
            viewer.getHelpWindow(stage, "Ventana Ayuda", 1300, 800);
        }catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void abreAyuda(ActionEvent event) {
        initializeHelp(stage);
        viewer.showHelpDialog(btnAyunda);

    }

    }
