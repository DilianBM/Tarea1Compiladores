public class EntidadHerencia {

        public Entidad entidad;
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

}
