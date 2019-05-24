import java.util.List;

//Clase abstracta de la cual heredan las clases encargadas de generar los scripts para MySQL y PostgreSQL
public abstract class ScriptGenerator extends  ConfigurationStorage {

    public abstract void createScript(String ruta);


}
