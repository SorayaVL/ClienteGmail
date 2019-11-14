package es.soraya.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {
    Parent root;
    Stage stage;

    // private void cargar Stage (Parent root, int an)

      public BaseController cargarDialogo (String fxml, boolean maximizado, int anchura, int altura) {

        try {
            //carga el fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            root = fxmlLoader.load();
            BaseController controller = fxmlLoader.getController();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            if (maximizado) stage.setMaximized(true);
            else stage.setScene(new Scene(root, anchura, altura));
            return controller;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    //ctrl+q para abrir la ayuda de los comentarios
    protected void abrirDialogo (boolean showAndWait){
        if (showAndWait) stage.showAndWait();
        else stage.show();
    }

}
