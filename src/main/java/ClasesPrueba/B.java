package ClasesPrueba;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity()
@Table(name = "NOMBREDETABLA2")
public class B  {
    @Id
    @Column
    String llave;

    @Column(length = 244)
    private String variable;

    @Column
    @OneToOne()
    @JoinColumn(name = "C_FK")
    public  C cPequeño;


    public String propiedad1;


    @Lob
    @Column(name = "PropiedadNum2", nullable = true)
    public Float propiedad2;

 //   @Enumerated(value = EnumType.STRING)





}