import java.sql.Connection;
import java.sql.PreparedStatement;

public class BDMSCreatorBDPostgreS extends TablesCreatorBD {
    @Override
    public void createTables(Connection c) {
        for (int i = 0; i < sentences.size(); i++) {
            System.out.println(sentences.get(i));
            try {
                PreparedStatement stmt = c.prepareStatement(sentences.get(i));
                stmt.execute();
                System.out.println(sentences.get(i));

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }
}
