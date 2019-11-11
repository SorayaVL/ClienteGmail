package es.soraya;

import es.soraya.logica.GestionCuenta;
import es.soraya.logica.Logica;
import es.soraya.models.CuentaCorreo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.*;
import java.io.IOException;
import java.util.concurrent.Callable;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("views/VentanaPrincipal.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) throws MessagingException, IOException {
        CuentaCorreo cuenta1 = new CuentaCorreo("sorayadamdi@gmail.com", "Estrella3.");
        GestionCuenta.getINSTANCE().abrirCuenta(cuenta1);
        launch(args);
    }
}
