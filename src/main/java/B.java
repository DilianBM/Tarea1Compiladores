import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class B {
    @Lob
    public String propiedad1;
    @Column(name = "PropiedadNum2",nullable = true)
    public String propiedad2;

    public String toString() {
        return "esto es una B";
    }

    @Column(name = "Basura")
    public String basura() {
        return "esto es una Basura";
    }


}