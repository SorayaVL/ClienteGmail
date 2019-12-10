package es.soraya.logica;

import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class CarpetasService extends Service <Integer> {

    public CarpetasService() {
    }

    @Override
    public Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i;
                for (i=0; i<500;i++){
                    updateProgress(i, 500);
                    Thread.sleep(2);
                }

                return i;
            }
        };
    }
}
