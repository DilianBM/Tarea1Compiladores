package ClasesPrueba;

import javax.persistence.*;

@Entity
public class K {
    @Id
    @Column(name = "PK")
    String pkK;

    @JoinColumn
    @OneToOne
    @Column
    public  H h;
}
