package ClasesPrueba;

import javax.persistence.*;

@Entity
public class F extends E {

    @Column
    String valor;

    @JoinColumn
    @OneToOne
    @Column
    public B awss;

    @JoinColumn
    @OneToMany
    @Column
    public B a2;



}
