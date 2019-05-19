package ClasesPrueba;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "A")
public class A extends C{
    @Id
    @Column
    public boolean basura;
}
