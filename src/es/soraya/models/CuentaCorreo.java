package es.soraya.models;

import java.io.Serializable;


public class CuentaCorreo implements Serializable {
    private static final long serialVersionUID = -5205807225694005213L;

    private String email;
    private String password;


    public CuentaCorreo() {
    }


    public CuentaCorreo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
