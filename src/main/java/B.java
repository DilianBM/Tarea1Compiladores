import javax.persistence.*;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Map;

enum MyEnum {
    FOO('F'),
    BAR('B');

    private char id;
    private static Map<Character, MyEnum> myEnumById = new HashMap<Character, MyEnum>();

    static {
        for (MyEnum myEnum : values()) {
            myEnumById.put(myEnum.getId(), myEnum);
        }
    }

    private MyEnum(char id) {
        this.id = id;
    }

    public static MyEnum findById(char id) {
        return myEnumById.get(id);
    }

    public char getId() {
        return id;
    }
}

@Table(name = "NOMBRE DE TABLA 2")
@Entity()
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class B {
    @Lob
    public String propiedad1;
    @Lob
    @Column(name = "PropiedadNum2", nullable = true)
    public String propiedad2;

    public String toString() {
        return "esto es una B";
    }

    @Enumerated(value = EnumType.STRING)
   MyEnum myEnum;


}