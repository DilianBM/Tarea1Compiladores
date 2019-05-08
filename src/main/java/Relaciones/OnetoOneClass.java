package Relaciones;

public class OnetoOneClass {

    String relatedEntity;
    String pk;
    String myForeignKey;

    public OnetoOneClass(String a, String b, String c) {

        relatedEntity = a;
        pk = b;
        myForeignKey = c;

    }

    public String getPk() {
        return pk;
    }

    public String getrelatedEntity() {
        return relatedEntity;

    }

    public String getMyForeignKey() {

        return myForeignKey;
    }

}
