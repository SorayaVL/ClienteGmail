package es.soraya.models;

import javax.mail.Message;
import javax.mail.Store;


public class CuentaCorreo {

    private String email;
    private String password;
    private Store store;
    private Message message;


    public CuentaCorreo() {
    }


    public CuentaCorreo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CuentaCorreo(String email, String password, Store store, Message message) {
        this.email = email;
        this.password = password;
        this.store = store;
        this.message = message;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
