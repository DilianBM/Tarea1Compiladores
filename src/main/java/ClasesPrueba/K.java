package ClasesPrueba;

import javax.persistence.*;

@Entity
public class K {
    @Id
    @Column(name = "PK")
    String pkK;

    @JoinColumn
    @OneToMany
    @Column
    public  H h;
}
