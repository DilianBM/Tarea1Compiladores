package Relaciones;

import java.util.ArrayList;
import java.util.List;

public class Relaciones {

   public List<OnetoOneClass> listaOneToOne  = new ArrayList<OnetoOneClass>();
   public List<OnetoManyClass> ListaOneToMany;
   public List<ManytoOneClass> listaManyToOne;
   public List<ManytoManyClass> ListaManyToMany;

   public List<OnetoOneClass> getLista1(){

      return listaOneToOne;
   }
}
