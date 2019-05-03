package ClasesPrueba;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@MappedSuperclass
public class SuperSuperClase {
    @Column
    int newVal;
}
