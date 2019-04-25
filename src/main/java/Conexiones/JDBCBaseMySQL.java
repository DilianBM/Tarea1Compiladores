package Conexiones;

import javax.swing.*;
import java.sql.*;

public class JDBCBaseMySQL {
    String url;
    String user;
    String password;
    String BD;
    Connection c;

    public JDBCBaseMySQL() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab1?autoReconnect=true&amp;useSSL=false&allowPublicKeyRetrieval=true", "root", "Ian24/02/95");//Crea la conexión con la BD
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

    public void setAtributes(String URL, String usuario, String contraseña) {
        url = URL;
        user = usuario;
        password = contraseña;
    }
}
