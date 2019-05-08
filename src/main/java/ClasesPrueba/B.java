package ClasesPrueba;

import javax.persistence.*;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
<<<<<<< HEAD
public class B {
=======
public class B extends Superclase{
>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5

    String foreignKey;

    @OneToOne(mappedBy = "C")
    @JoinColumn(name = "primaryKey", referencedColumnName = "foreignKey")

    @Lob
    public String propiedad1;
    @Lob
    @Column(name = "PropiedadNum2", nullable = true)
    public String propiedad2;

 //   @Enumerated(value = EnumType.STRING)
    @Column
    @Enumerated(EnumType.ORDINAL)
   MyEnum myEnum;


    public static class C {
    }
}