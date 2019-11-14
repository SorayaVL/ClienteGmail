package es.soraya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.mail.*;
import java.io.IOException;


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
        launch(args);
    }
}
