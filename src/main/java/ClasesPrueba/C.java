package ClasesPrueba;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Table (name = "TABLA DE PRUEBA")
public class C {

    @Id
    @Column
    String primaryKey;

    @OneToOne(mappedBy = "cPequeño")
    @Column
    public B instanciaB;

    @Column
    @OneToMany()
    @JoinColumn(name = "InstB")
    public B GInstanciaB;

    @Column
    @ManyToOne
   @JoinColumn(name = "B_id")
    public B InstanciaB2;


    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }
}
