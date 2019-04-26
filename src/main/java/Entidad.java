import java.util.Vector;

public class Entidad {
 Vector<Columna> columns;
 Columna PrimaryKey;
 String NombTable;

    public void setNombTable(String nombTable) {
        NombTable = nombTable;
    }

    public String getNombTable() {
        return NombTable;
    }
}
