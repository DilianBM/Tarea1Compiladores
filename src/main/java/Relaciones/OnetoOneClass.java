package Relaciones;

public class OnetoOneClass {

    String relatedEntity;
    String relatedEntityPK;
    String myForeignKey;

    public OnetoOneClass(String a, String b, String c) {

        relatedEntity = a;
        relatedEntityPK = b;
        myForeignKey = c;

    }

    public String getPk() {
        return relatedEntityPK;
    }

    public String getrelatedEntity() {
        return relatedEntity;

    }

    public String getMyForeignKey() {

        return myForeignKey;
    }

}
