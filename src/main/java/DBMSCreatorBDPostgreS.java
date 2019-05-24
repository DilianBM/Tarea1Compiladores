import java.sql.Connection;
import java.sql.PreparedStatement;

//Toma la lista de sentencia y las envia una a una a la base de datos de PostgreSQL para generar las tablas en la base de datos
public class DBMSCreatorBDPostgreS extends TablesCreatorBD {

    //Metodo encargado de enviar las sentencias a la base para generar las tablas
    @Override
    public void createTables(Connection c) {
        for (int i = 0; i < sentences.size(); i++) {


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
