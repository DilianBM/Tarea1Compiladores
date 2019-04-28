import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Superclase extends SuperSuperClase {
    @Id
    @Column
    String valor;
}
