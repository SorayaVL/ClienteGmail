package es.soraya.logica;

import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class CarpetasService extends Service <String> {

    public CarpetasService() {
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {

                return "";
            }
        };
    }
}
