import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

//Clase encargada de generar un archivo con el script correspondiente a la sintaxis de PostgreSQL
public  class ScriptFileGeneratorPostgre extends ScriptGenerator {

    //Recibe un string con la ruta donde se quiere generar el archivo que contiene el script
    @Override
    public void createScript(String ruta) {
        try {

            File file = new File(ruta);

            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);

            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < sentences.size(); i++) {
                System.out.println(sentences.get(i));
                bw.write(sentences.get(i)+"\n");
            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


