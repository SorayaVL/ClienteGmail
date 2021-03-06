package es.soraya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;


public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/VentanaPrincipal.fxml"));
        stage.setTitle("Gestor Gmail");
        stage.setScene(new Scene(root));
        stage.setMaximized(false);
        stage.show();    }

    public static void main(String[] args) throws MessagingException, IOException {
        launch(args);
    }
}
