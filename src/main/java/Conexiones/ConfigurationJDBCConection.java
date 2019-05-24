package Conexiones;

//Clase abstracta de la cual heredan las clases encargadas de generar la configuracion para crear la conexion con las respectivas
//bases de datos
public abstract class ConfigurationJDBCConection {
    String URL;
    String user;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void createConection(){}
}
