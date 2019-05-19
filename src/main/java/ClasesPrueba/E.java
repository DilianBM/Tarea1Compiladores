package ClasesPrueba;

import javax.persistence.*;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "basura")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "gg")
public class E {

    @Id
    @Column
    String clav;

    @JoinColumn
    @OneToOne
    @Column
    public A yyyy;


    @JoinColumn
    @OneToOne
    @Column
    public A assS;


}
