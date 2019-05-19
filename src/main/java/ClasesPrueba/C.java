package ClasesPrueba;

import javax.persistence.*;

@Entity
public class C {

    @Id
    @Column(length = 244)
    String primaryK;



    @Column(length = 45)
    @OneToMany()
    @JoinColumn()
    public B GInstanciaB;

    @Column
    @ManyToOne
    @JoinColumn(name = "B_id")
    public B InstanciaB2;





}
