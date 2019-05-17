package ClasesPrueba;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

public class F extends E{

    @Column
    String valor;
}
