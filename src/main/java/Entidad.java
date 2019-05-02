import Relaciones.Relaciones;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
    List<Columna> columns = new ArrayList<Columna>();
    Columna primaryKey;
    String nombTable;

    Relaciones relations = new Relaciones();



    public void setNombTable(String nombreTable) {
        nombTable = nombreTable;
    }

    public String getNombTable() {
        return nombTable;
    }

    public void setColumns(Columna column) {
        columns.add(column);
    }

    public void setPrimaryKey(Columna primarykey) {
        primaryKey = primarykey;
    }

    public Columna getPrimaryKey() {
        return primaryKey;
    }

    public String imprimecolumns() {
        String h = "";
        for (int i = 0; i < columns.size(); i++) {

            h = h +" "+ columns.get(i).getName();
        }
        return h;
    }


}
