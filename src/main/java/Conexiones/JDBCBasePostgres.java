package Conexiones;

import java.sql.*;

//Clase encargada de tomar los credenciales y crear la conexion para PostgreSQL
public class JDBCBasePostgres extends ConfigurationJDBCConection {


    public Connection connection;
    ;


    public Connection getC() {
        return connection;
    }


    //Crea la conexiona partir de un URL, un usuario y una contrasena
    @Override
    public void createConection() {

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
