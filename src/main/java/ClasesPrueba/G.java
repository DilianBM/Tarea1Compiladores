package ClasesPrueba;

import javax.persistence.*;

@Entity
public class G {

    @Id
    @Column
    boolean llavePrimariaG;

    @Column
    @ManyToMany
    @JoinTable(name="TablaG_H", joinColumns=@JoinColumn(name="llavePrimariaG"), inverseJoinColumns=@JoinColumn(name="E_id"))
    public  H instanciaH;

}
