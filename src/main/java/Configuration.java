import Conexiones.JDBCBaseMySQL;
import Conexiones.JDBCBasePostgres;
import javax.swing.*;
import java.sql.*;

//Clase encargada de definir los credenciales para crear la conexion ya sea con una base de datos en MySQL o PostgreSQL
public class Configuration {
    JDBCBaseMySQL jdbcBaseMySQL = new JDBCBaseMySQL();
    JDBCBasePostgres jdbcBasePostgres = new JDBCBasePostgres();

    //Recibe un URL, usuario y contrasena, los cuales son necesarios para generar la conexion con la base de MySQL
    public Connection setCrendentialsMySQL(String URL, String user, String password) {

        jdbcBaseMySQL.setPassword(password);
        jdbcBaseMySQL.setURL(URL);
        jdbcBaseMySQL.setUser(user);
        jdbcBaseMySQL.createConection();
        return jdbcBaseMySQL.getC();
    }

    //Metodo analogo al anterior, con la diferencia que genera la conexion con una base de PostgreSQL
    public Connection setCrendentialsPostgre(String URL, String user, String password) {

        jdbcBasePostgres.setPassword(password);
        jdbcBasePostgres.setURL(URL);
        jdbcBasePostgres.setUser(user);
        jdbcBasePostgres.createConection();
        jdbcBasePostgres.createConection();
        return  jdbcBasePostgres.getC();
    }
}
