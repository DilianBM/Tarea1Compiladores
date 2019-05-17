package ClasesPrueba;

import javax.persistence.*;
import java.beans.ConstructorProperties;

@Entity
@Inheritance(strategy =InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "columnadiscriminadora")
public class E {

    @JoinColumn
    @OneToOne
    @Column
    public A a;

}
