package Relaciones;
public class OnetoMany {
String mappedBy="";
String relatedEntity="";

    public String getMappedBy() {
        return mappedBy;
    }

    public String getRelatedEntity() {
        return relatedEntity;
    }

    public void setMappedBy(String mappedBy) {
        this.mappedBy = mappedBy;
    }

    public void setRelatedEntity(String relatedEntity) {
        this.relatedEntity = relatedEntity;
    }
}
