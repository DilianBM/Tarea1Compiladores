package ClasesPrueba;

import javax.persistence.*;

@Entity

public class C {

    @Id
    @Column
    String primaryKey;

    @OneToOne()
    @JoinColumn()
    @Column
    public B instanciaB;

    @Column
    @OneToMany()
    @JoinColumn()
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
