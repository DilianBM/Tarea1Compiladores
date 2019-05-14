package ClasesPrueba;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;




@Entity()
@Table(name = "NOMBRE DE TABLA 2")
public class B  {
    @Id
    @Column
    String llave;

    @Column
    private String variable;

    @Column
    @OneToOne()
    @JoinColumn(name = "C_FK")
    public  C cPequeño;


    public String propiedad1;


    @Lob
    @Column(name = "PropiedadNum2", nullable = true)
    public String propiedad2;

 //   @Enumerated(value = EnumType.STRING)





}