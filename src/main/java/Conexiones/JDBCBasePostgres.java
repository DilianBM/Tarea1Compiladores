package Conexiones;

import java.sql.*;

public class JDBCBasePostgres implements IConection {
    String url;
    String user;
    String password;
    String BD;
    Connection connection;

    public JDBCBasePostgres() {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            // Database connect
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(url, user, password);
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }

    public void setAtributes(String URL, String usuario, String contraseña) {
        url = URL;
        user = usuario;
        password = contraseña;
    }
}
