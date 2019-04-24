import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;


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
