package ClasesPrueba;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;

@Entity
public class C {

    @Id
    String primaryKey;

    @OneToOne(mappedBy = "prueba")
    private B instanciaB;

}
