

import Relaciones.ManytoManyClass;
import Relaciones.ManytoOneClass;
import Relaciones.OnetoManyClass;
import Relaciones.OnetoOneClass;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
    List<Columna> columns = new ArrayList<Columna>();
    Columna primaryKey=null;
    String nombTable;
    public List<OnetoOneClass> listaOneToOne = new ArrayList<OnetoOneClass>();
    public List<OnetoManyClass> ListaOneToMany=new ArrayList<>();
    public List<ManytoOneClass> listaManyToOne;
    public List<ManytoManyClass> ListaManyToMany;

    public List<OnetoOneClass> getLista1() {

        return listaOneToOne;
    }
    public List<OnetoManyClass> getListaOneToMany() {

        return ListaOneToMany;
    }

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

    public List<Columna> getColumns() {
        return columns;
    }


    public String imprimecolumns() {
        String h = "";
        for (int i = 0; i < columns.size(); i++) {

            h = h + " NomColumna " + columns.get(i).getName()+" NomTipo "+columns.get(i).getNombreTipo()+"\n";

        }
        return h;
    }


}
