import java.sql.Connection;
import java.util.List;

//Clase abstracta de la cual heredan las clases que generan las tablas en la base
public abstract class TablesCreatorBD extends ConfigurationStorage {

    public void createTables(Connection c) {
    }
}
