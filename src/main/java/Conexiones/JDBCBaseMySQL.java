package Conexiones;

import javax.swing.*;
import java.sql.*;

//Clase encargada de tomar los credenciales y crear la conexion para MySQL
public class JDBCBaseMySQL extends ConfigurationJDBCConection{


  public Connection c;
    public JDBCBaseMySQL() {

    }

    public Connection getC() {
        return c;
    }

    //Crea la conexiona partir de un URL, un usuario y una contrasena
    @Override
    public void createConection() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(getURL()+"?autoReconnect=true&amp;useSSL=false&allowPublicKeyRetrieval=true", getUser(), getPassword());//Crea la conexión con la BD
            System.out.println("Conexión realizada");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }
}
