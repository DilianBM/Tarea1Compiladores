package Relaciones;

public class OnetoManyClass extends Relaciones {
    String nameJoinColumn="";
    String pk="";

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getNameJoinColumn() {
        return nameJoinColumn;
    }

    public void setNameJoinColumn(String nameJoinColumn) {
        this.nameJoinColumn = nameJoinColumn;
    }
}
