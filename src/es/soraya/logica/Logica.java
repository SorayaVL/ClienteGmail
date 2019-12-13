package es.soraya.logica;

import es.soraya.models.CuentaCorreo;
import es.soraya.models.Emails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logica implements Serializable {

    private static Logica INSTANCE = null;
    private List<CuentaCorreo> listaCuentasList = new ArrayList<CuentaCorreo>();
    private File file = new File("cuentas.dat");

    public Logica() {
    }

    public static Logica getINSTANCE() {

        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Emails> listaCorreo = FXCollections.observableArrayList();

    public ObservableList<CuentaCorreo> getListaCuentas() {
        return olistaCuentas;
    }

    public ObservableList<CuentaCorreo> olistaCuentas = FXCollections.observableArrayList();

    public ObservableList<Emails> getListaCorreo() {
        return listaCorreo;
    }

    public void cargarCorreo(Emails correo) {
        listaCorreo.add(correo);
    }

    public void aniadirCuenta(CuentaCorreo cuentaCorreo) {
        olistaCuentas.add(cuentaCorreo);
    }


    public void guardarFichero() {
        listaCuentasList = new ArrayList<CuentaCorreo>(olistaCuentas);
        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(file));
            fichero.writeObject(listaCuentasList);
            fichero.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abreFichero() {
        try {
            if (file.length() > 0) {
                ObjectInputStream cargaFichero = new ObjectInputStream(new FileInputStream(file));
                listaCuentasList = (ArrayList) cargaFichero.readObject();
                cargaFichero.close();
                for (CuentaCorreo cuentaCorreo : listaCuentasList) {
                    aniadirCuenta(cuentaCorreo);
                }
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
