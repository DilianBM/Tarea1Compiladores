package Conexiones;

import java.sql.*;

public class JDBCBasePostgres  extends ConfigurationJDBCConection{



    public JDBCBasePostgres() {

    }

    @Override
    public void createConection() {
        Connection connection;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            // Database connect
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(URL, user, password);
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
}
