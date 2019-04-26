import javax.persistence.*;
@Table(name = "NOMBRE DE TABLA 1")
@Inheritance
@Entity(name = "ENTIDAD A")
public class A {
    public String toString() {
        return "esto es una A";
    }
   @Column
    public  String val;





}
