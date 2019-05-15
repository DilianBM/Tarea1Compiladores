package Relaciones;

public class OnetoOneClass extends Relaciones {
    String pk;
    String nameJoinColumn;


    public OnetoOneClass(String targetEnt, String b) {
        targetEntity = targetEnt;
        pk = b;
    }
    public OnetoOneClass() {

    }

    public String getPk() {
        return pk;
    }

    public String getNameJoinColumn() {
        return nameJoinColumn;
    }

    public void setNameJoinColumn(String nameJoinColumn) {
        this.nameJoinColumn = nameJoinColumn;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

}
