import javax.persistence.*;
@Table(name = "NOMBRE DE TABLA 1")
@Inheritance
@Entity(name = "ENTIDAD A")
public class A extends B {
    public String toString() {
        return "esto es una A";
    }

    @Column(name = "Basura")
    public String basura() {
        return "esto es una Basura";
    }


}
