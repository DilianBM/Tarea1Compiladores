import java.util.LinkedList;
import java.util.List;
import java.lang.*;

public class RelationalObjectMapper {

    public static void main(String[] args) {

        ClassReader reader = new ClassReader();
        Analyzer analyzer = new Analyzer();
        ScriptGenerator scriptGenerator=new ScriptGenerator();
        List<String> resul = new LinkedList<String>();
        Package[] packages = Package.getPackages();
        for (Package pack : packages) {
            resul.add(pack.getName());
            String scanPackage = pack.getName();
            reader.proccesClass(scanPackage);

        }
        analyzer.procesaEntidades(reader.getClases());
        analyzer.validationsCicles();


        MySQLSentences mySQLSentences=new MySQLSentences();

       mySQLSentences.imprimeScript( mySQLSentences.generateSentences(analyzer.ir.ListaDeEntidades));
    }
}




