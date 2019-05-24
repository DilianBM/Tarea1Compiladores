package Relaciones;
//Representacion de una relacion ManyToOne
public class ManytoOneClass extends Relaciones{
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
