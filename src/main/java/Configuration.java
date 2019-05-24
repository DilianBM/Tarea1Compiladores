import Conexiones.JDBCBaseMySQL;
import Conexiones.JDBCBasePostgres;
import javax.swing.*;
import java.sql.*;
public class Configuration {
    JDBCBaseMySQL jdbcBaseMySQL = new JDBCBaseMySQL();
    JDBCBasePostgres jdbcBasePostgres = new JDBCBasePostgres();

    public Connection setCrendentialsMySQL(String URL, String user, String password) {
        jdbcBaseMySQL.setPassword(password);
        jdbcBaseMySQL.setURL(URL);
        jdbcBaseMySQL.setUser(user);
        jdbcBaseMySQL.createConection();
        return jdbcBaseMySQL.getC();
    }


    public Connection setCrendentialsPostgre(String URL, String user, String password) {
        jdbcBasePostgres.setPassword(password);
        jdbcBasePostgres.setURL(URL);
        jdbcBasePostgres.setUser(user);
        jdbcBasePostgres.createConection();
        jdbcBasePostgres.createConection();
        return  jdbcBasePostgres.getC();
    }
}
