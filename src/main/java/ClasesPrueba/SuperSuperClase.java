package ClasesPrueba;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SuperSuperClase {
    @Column
    int newVal;
}
