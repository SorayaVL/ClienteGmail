package es.soraya.logica;

import es.soraya.models.Emails;
import es.soraya.models.CuentaCorreo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logica implements Serializable{

    private static Logica INSTANCE = null;
    private List<CuentaCorreo> listaCuentasList = new ArrayList<CuentaCorreo>();

    public Logica() {
    }

    public static Logica getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Emails> listaCorreo = FXCollections.observableArrayList();

    public ObservableList<CuentaCorreo> getListaCuentas() {
        return listaCuentas;
    }

    public ObservableList<CuentaCorreo> listaCuentas = FXCollections.observableArrayList();

    public ObservableList<Emails> getListaCorreo() {
        return listaCorreo;
    }

    public void cargarCorreo(Emails correo) {
        listaCorreo.add(correo);
    }

    public void aniadirCuenta(CuentaCorreo cuentaCorreo) {
        listaCuentas.add(cuentaCorreo);
    }


    public void guardarFichero() {
        listaCuentasList =new ArrayList<CuentaCorreo>(listaCuentas);

        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("cuentas.txt"));
            fichero.writeObject(listaCuentasList);
            System.out.println("salvada cuenta");
            fichero.close();
        } catch (IOException e) {

        }
    }

    public void abreFichero() {
        try {
            System.out.println("Estoy en abre fichero");
            ObjectInputStream cargaFichero = new ObjectInputStream(new FileInputStream("cuentas.txt"));
            listaCuentasList = (ArrayList) cargaFichero.readObject();
            cargaFichero.close();
            for (CuentaCorreo cuentaCorreo : listaCuentasList){
                aniadirCuenta(cuentaCorreo);
                System.out.println("añadida cuenta");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
