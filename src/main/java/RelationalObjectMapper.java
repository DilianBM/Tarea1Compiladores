import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.*;

public class RelationalObjectMapper {
    public ClassReader reader = new ClassReader();
    public Analyzer analyzer = new Analyzer();

    public void configuracionPrevia() {
        List<String> resul = new LinkedList<String>();
        Package[] packages = Package.getPackages();
        for (Package pack : packages) {
            resul.add(pack.getName());
            String scanPackage = pack.getName();
            reader.proccesClass(scanPackage);
        }
        analyzer.procesaEntidades(reader.getClases());
        analyzer.validationsCicles();
        // mySQLSentences.imprimeScript( mySQLSentences.generateSentences(analyzer.ir.ListaDeEntidades));
        Grafo grafo = new Grafo(5);
        grafo.pruebagrafo(analyzer.ir.ListaDeEntidades);
        grafo.ordenDeEjecucionTablas(analyzer.ir.ListaDeEntidades, grafo.DFS());


    }

    public void ejecutaTablasBDMySQL(String url, String password, String user) {
        List<String> sentences = new ArrayList<>();
        MySQLSentences mySQLSentences = new MySQLSentences();
        sentences = mySQLSentences.generateSentences(analyzer.ir.ListaDeEntidades);

        Configuration configuration1 = new Configuration();
        Connection c = configuration1.setCrendentialsMySQL(url, user, password);
        DBMSCreatorBDMySQL dbmsCreatorBDMySQL = new DBMSCreatorBDMySQL();
        dbmsCreatorBDMySQL.sentences = sentences;
        dbmsCreatorBDMySQL.createTables(c);

        //  Connection c = configuration1.setCrendentialsMySQL("jdbc:mysql://localhost:3306/lab1", "root", "Ian24/02/95");
        // Connection c2 = configuration1.setCrendentialsPostgre("jdbc:postgresql://localhost/BaseDePrueba", "postgres", "Ian24/02/95");

    }

    public void ejecutaTablasBDPostgre(String url, String password, String user) {
        List<String> sentences = new ArrayList<>();
        PostgresSentences postgresSentences = new PostgresSentences();
        sentences = postgresSentences.generateSentences(analyzer.ir.ListaDeEntidades);

        Configuration configuration1 = new Configuration();
        Connection c2 = configuration1.setCrendentialsPostgre(url, user, password);
        DBMSCreatorBDPostgreS dbmsCreatorBDPostgreS = new DBMSCreatorBDPostgreS();
        dbmsCreatorBDPostgreS.sentences = sentences;
        dbmsCreatorBDPostgreS.createTables(c2);

    }

    public void creaScriptMySQL(String ruta) {

        MySQLSentences mySQLSentences = new MySQLSentences();
        List<String> sentences = new ArrayList<>();
        sentences = mySQLSentences.generateSentences(analyzer.ir.ListaDeEntidades);
        ScriptFileGeneratorMySQL scriptFileGeneratorMySQL = new ScriptFileGeneratorMySQL();
        scriptFileGeneratorMySQL.sentences = sentences;
        scriptFileGeneratorMySQL.createScript(ruta);

        //scriptFileGeneratorMySQL.createScript("C:\\Users\\Dilian\\Desktop\\Semestre 1 2019\\Automatas\\ProyectoCompiladores\\Tarea1Compiladores\\prueba.sql");

    }

    public void creaScriptPosgre(String ruta) {
        List<String> sentences = new ArrayList<>();
        PostgresSentences postgresSentences = new PostgresSentences();
        sentences = postgresSentences.generateSentences(analyzer.ir.ListaDeEntidades);
        ScriptFileGeneratorPostgre scriptFileGeneratorPostgres = new ScriptFileGeneratorPostgre();
        scriptFileGeneratorPostgres.sentences = sentences;
        scriptFileGeneratorPostgres.createScript(ruta);

        // scriptFileGeneratorPostgres.createScript("C:\\Users\\Dilian\\Desktop\\Semestre 1 2019\\Automatas\\ProyectoCompiladores\\Tarea1Compiladores\\prueba.sql");
    }

    /*public void main(String[] args) {
        // ScriptGenerator scriptGenerator=new ScriptGenerator();
        configuracionPrevia();
        ejecutaTablasBDMySQL("jdbc:mysql://localhost:3306/lab1", "root", "Ian24/02/95");
        ejecutaTablasBDPostgre("jdbc:postgresql://localhost/BaseDePrueba", "postgres", "Ian24/02/95");
        creaScriptMySQL("C:\\Users\\Dilian\\Desktop\\Semestre 1 2019\\Automatas\\ProyectoCompiladores\\Tarea1Compiladores\\prueba.sql");
        creaScriptPosgre("C:\\Users\\Dilian\\Desktop\\Semestre 1 2019\\Automatas\\ProyectoCompiladores\\Tarea1Compiladores\\prueba.sql");
        //  Connection c = configuration1.setCrendentialsMySQL("jdbc:mysql://localhost:3306/lab1", "root", "Ian24/02/95");
        // Connection c2 = configuration1.setCrendentialsPostgre("jdbc:postgresql://localhost/BaseDePrueba", "postgres", "Ian24/02/95");

    }*/
}




