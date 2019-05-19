package ClasesPrueba;

import javax.persistence.*;

@Entity
public class F extends E {

    @Column
    String valor;

    @JoinColumn
    @OneToOne
    @Column
    public A awsss;

    @JoinColumn
    @OneToMany
    @Column
    public A a2;

    @Column
    @ManyToMany
    @JoinTable(name="Tabla F_E", joinColumns=@JoinColumn(name="F_id"), inverseJoinColumns=@JoinColumn(name="E_id"))
    public  E instanciaE;

}
