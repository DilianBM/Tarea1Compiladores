import javax.persistence.*;
import java.lang.reflect.Type;
import java.sql.Clob;

@Entity(name = "ENTIDAD B")
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class B {
    @Lob
    public String propiedad1;
    @Lob
    @Column(name = "PropiedadNum2",nullable = true)
    public String propiedad2;

    public String toString() {
        return "esto es una B";
    }




}