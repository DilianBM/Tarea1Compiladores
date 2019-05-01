import javax.persistence.Id;
import java.lang.reflect.Field;

public class Validations {
    public void validadExistenciasPK(Entidad entidad) {
        if (entidad.getPrimaryKey() == null) {
            System.out.println("Error no existe PK para la entidad " + entidad.getNombTable());
        }
    }
}
