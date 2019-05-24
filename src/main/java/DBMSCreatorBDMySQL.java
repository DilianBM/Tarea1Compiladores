import Conexiones.JDBCBaseMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

//Toma la lista de sentencia y las envia una a una a la base de datos de MySQL para generar las tablas en la base de datos
public class DBMSCreatorBDMySQL extends TablesCreatorBD {
    JDBCBaseMySQL jdbcBaseMySQL = new JDBCBaseMySQL();

    //Metodo encargado de enviar las sentencias a la base para generar las tablas
    @Override
    public void createTables(Connection c) {
        for (int i = 0; i < sentences.size(); i++) {
            System.out.println(sentences.get(i));
            try {
                PreparedStatement stmt = c.prepareStatement(sentences.get(i));
                stmt.execute();
                System.out.println(sentences.get(i));

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }
}
