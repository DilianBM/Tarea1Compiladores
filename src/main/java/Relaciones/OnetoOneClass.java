package Relaciones;

public class OnetoOneClass {

    String relatedEntity;
<<<<<<< HEAD
    String pk;
=======
    String relatedEntityPK;
>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5
    String myForeignKey;

    public OnetoOneClass(String a, String b, String c) {

        relatedEntity = a;
<<<<<<< HEAD
        pk = b;
=======
        relatedEntityPK = b;
>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5
        myForeignKey = c;

    }

    public String getPk() {
<<<<<<< HEAD
        return pk;
=======
        return relatedEntityPK;
>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5
    }

    public String getrelatedEntity() {
        return relatedEntity;

    }

    public String getMyForeignKey() {

        return myForeignKey;
    }

}
