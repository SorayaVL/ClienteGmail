package es.soraya.views;

import javafx.application.Application;
import javafx.stage.Stage;
import org.docgene.help.JavaHelpFactory;
import org.docgene.help.gui.jfx.JFXHelpContentViewer;

import java.net.URL;

public class Ayuda extends Application {

    private JFXHelpContentViewer viewer;
    private void initializeHelp(Stage stage)
    {
        try {
            URL url = this.getClass().getResource("/help/articles.zip");
            JavaHelpFactory factory = new JavaHelpFactory(url);
            factory.create();
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);
            viewer.getHelpWindow(stage, "Ventana Ayuda", 900, 900);
        }catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        initializeHelp(stage);

    }
}
