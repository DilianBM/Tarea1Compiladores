package Relaciones;

public class ManytoOneClass {
    String nameJoinColumn;
    String pk;

    public void setNameJoinColumn(String nameJoinColumn) {
        this.nameJoinColumn = nameJoinColumn;
    }

    public String getNameJoinColumn() {
        return nameJoinColumn;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPk() {
        return pk;
    }
}
