import java.util.ArrayList;
import java.util.List;

//Clase abstracta de la cual heredan las clases encargadas de generar las sentencias con la sintaxis correspondiente a MySQL y PostgreSQL
public abstract class SentenceGenerator {
    public abstract List<String> generateSentences(List<Entidad> entidades);


}
