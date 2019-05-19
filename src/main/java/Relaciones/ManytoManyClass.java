package Relaciones;

public class ManytoManyClass {

    String tableName;

    String mypk;
    String mypkType;

    String targetEntity;
    String targetEntityPK;
    String targetEntityPKType;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setMypk(String mypk) {
        this.mypk = mypk;
    }

    public String getMypk() {
        return mypk;
    }

    public void setMypkType(String mypkType) {
        this.mypkType = mypkType;
    }

    public String getMypkType() {
        return mypkType;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntityPK(String targetEntityPK) {
        this.targetEntityPK = targetEntityPK;
    }

    public String getTargetEntityPK() {
        return targetEntityPK;
    }

    public void setTargetEntityPKType(String targetEntityPKType) {
        this.targetEntityPKType = targetEntityPKType;
    }

    public String getTargetEntityPKType() {
        return targetEntityPKType;
    }
}
