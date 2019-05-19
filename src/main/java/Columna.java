import java.util.ArrayList;
import java.util.List;

public class Columna {
    Boolean nullable=true;
    Boolean updatable;
    Boolean Lob=false;
    String Name;
    String nombreTipo;
    int length;
    int precision;
    int scale;
    boolean esdeRelacion=false;

    public String getNombreTipo() {
        return nombreTipo;
    }


    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public void setUpdatable(Boolean updatable) {
        this.updatable = updatable;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setLob(Boolean lob) {
        Lob = lob;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public Boolean getLob() {
        return Lob;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public int getLength() {
        return length;
    }

    public Boolean getUpdatable() {
        return updatable;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public String getName() {
        return Name;
    }


}
