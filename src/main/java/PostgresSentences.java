import java.util.ArrayList;
import java.util.List;

public class PostgresSentences extends SentenceGenerator {

    @Override
    public List<String> generateSentences(List<Entidad> entidades) {
        List<String> sentences = new ArrayList();
        String values;
        for (int i = 0; i < entidades.size(); i++) {
            values = "CREATE TABLE " + entidades.get(i).getNombTable() + " ( " + "\n";
            for (int j = 0; j < entidades.get(i).getColumns().size(); j++) {
                if (entidades.get(i).getColumns().get(j).esdeRelacion == false) {
                    values += entidades.get(i).getColumns().get(j).Name;
                }

            }
        }
        return sentences;

    }
}
