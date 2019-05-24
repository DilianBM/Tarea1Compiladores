import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.*;

public class RelationalObjectMapper {

    public static void main(String[] args) {

        ClassReader reader = new ClassReader();
        Analyzer analyzer = new Analyzer();
        // ScriptGenerator scriptGenerator=new ScriptGenerator();
        List<String> resul = new LinkedList<String>();
        Package[] packages = Package.getPackages();
        for (Package pack : packages) {
            resul.add(pack.getName());
            String scanPackage = pack.getName();
            reader.proccesClass(scanPackage);

        }
        analyzer.procesaEntidades(reader.getClases());

        analyzer.validationsCicles();


        MySQLSentences mySQLSentences = new MySQLSentences();
        // mySQLSentences.imprimeScript( mySQLSentences.generateSentences(analyzer.ir.ListaDeEntidades));

        Grafo grafo = new Grafo(5);
        grafo.pruebagrafo(analyzer.ir.ListaDeEntidades);
        grafo.ordenDeEjecucionTablas(analyzer.ir.ListaDeEntidades, grafo.DFS());

        Configuration configuration1 = new Configuration();

       Connection c= configuration1.setCrendentialsMySQL("jdbc:mysql://localhost:3306/Lab1", "root", "Ian24/02/95");

        List<String> sentences=new ArrayList<>();
        sentences = mySQLSentences.generateSentences(analyzer.ir.ListaDeEntidades);
        ScriptFileGeneratorMySQL scriptFileGeneratorMySQL = new ScriptFileGeneratorMySQL();
        scriptFileGeneratorMySQL.sentences=sentences;

        DBMSCreatorBDMySQL dbmsCreatorBDMySQL = new DBMSCreatorBDMySQL();
        dbmsCreatorBDMySQL.sentences= sentences;
        dbmsCreatorBDMySQL.createTables(c);


        scriptFileGeneratorMySQL.createScript("C:\\Users\\Dilian\\Desktop\\Semestre 1 2019\\Automatas\\ProyectoCompiladores\\Tarea1Compiladores\\prueba.sql");


        System.out.println();

    }
}




