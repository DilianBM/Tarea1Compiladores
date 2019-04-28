import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Entidad {
    List<Columna> columns = new ArrayList<Columna>();
    Columna primaryKey;
    String nombTable;

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

    public String imprimecolumns() {
        String h = "";
        for (int i = 0; i < columns.size(); i++) {

            h = h +" "+ columns.get(i).getName();
        }
        return h;
    }
}
