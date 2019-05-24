import Conexiones.JDBCBaseMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DBMSCreatorBDMySQL extends TablesCreatorBD {
    JDBCBaseMySQL jdbcBaseMySQL = new JDBCBaseMySQL();

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
