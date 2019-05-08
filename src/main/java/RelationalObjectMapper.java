import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
import java.util.*;
import java.lang.*;

public class RelationalObjectMapper {

    public static void main(String[] args) {

        ClassReader reader = new ClassReader();
        Analyzer analyzer = new Analyzer();
        List<String> resul = new LinkedList<String>();
        Package[] packages = Package.getPackages();
        for (Package pack : packages) {
            resul.add(pack.getName());
            String scanPackage = pack.getName();
            analyzer.procesaEntidades(reader.proccesClass(scanPackage));
        }

    }
}




