package es.soraya.models;

import javax.mail.Message;
import javax.mail.Store;
import java.io.Serializable;


public class CuentaCorreo implements Serializable {

    private String email;
    private String password;




    public CuentaCorreo() {
    }


    public CuentaCorreo(String email, String password) {
        this.email = email;
        this.password = password;
    }

  /*  public CuentaCorreo(String email, String password, Store store, Message message) {
        this.email = email;
        this.password = password;
        this.store = store;
        this.message = message;
    }*/



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
