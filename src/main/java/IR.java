import java.util.ArrayList;
import java.util.List;

public class IR {
    List<Entidad> ListaDeEntidades = new ArrayList<>();
    EntidadesHerencia entidadesHerencia =new EntidadesHerencia();
    List<EntidadesHerencia> ListaDeEntidadesInheritance = new ArrayList<>();
    static class EntidadesHerencia
    {
        public Entidad entidad=new Entidad();
        public String DiscriminatorColumn="";
        public String estrategia="";

        public Entidad getEntidad() {
            return entidad;
        }

        public String getDiscriminatorColumn() {
            return DiscriminatorColumn;
        }

        public String getEstrategia() {
            return estrategia;
        }

        public void setDiscriminatorColumn(String discriminatorColumn) {
            DiscriminatorColumn = discriminatorColumn;
        }

        public void setEntidad(Entidad entidad) {
            this.entidad = entidad;
        }

        public void setEstrategia(String estrategia) {
            this.estrategia = estrategia;
        }
    };

}
