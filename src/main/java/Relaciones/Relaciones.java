package Relaciones;

import java.util.ArrayList;
import java.util.List;
//Clase abstracta y padre de las clases que representan los 4 tipos de relaciones
public abstract class Relaciones {

    String targetEntity = "";//jalar el nombre del field
    String mappedBy = "";

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public void setMappedBy(String mappedBy) {
        this.mappedBy = mappedBy;
    }

    public String getMappedBy() {
        return mappedBy;
    }
}
