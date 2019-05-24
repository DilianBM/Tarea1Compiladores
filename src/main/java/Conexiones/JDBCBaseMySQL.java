package Conexiones;

import javax.swing.*;
import java.sql.*;

public class JDBCBaseMySQL extends ConfigurationJDBCConection{


  public Connection c;
    public JDBCBaseMySQL() {

    }

    public Connection getC() {
        return c;
    }

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
