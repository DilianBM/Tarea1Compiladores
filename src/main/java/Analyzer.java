import Relaciones.OnetoOneClass;
import Relaciones.Relaciones;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Analyzer {
    List<Entidad> ListaDeEntidades = new ArrayList<>();
    Validations validations = new Validations();


    public void procesaEntidades(Class<?> cl) {
        Entidad entidad = new Entidad();
        ProcessClassAnnotations(cl, entidad);
        ListaDeEntidades.add(entidad);
        validations.validadExistenciasPK(entidad);
        System.out.println(entidad.getNombTable() + " " + entidad.imprimecolumns());
    }

    public void ProcessClassAnnotations(Class<?> cl, Entidad entidad) {
        Class<?> clase = null;
        Annotation[] anotations = cl.getAnnotations();
        if (anotations.length != 0) {
            if (cl.isAnnotationPresent(Table.class)) {
                Table tabla = cl.getAnnotation(Table.class);
                entidad.setNombTable(tabla.name());
                readMembers(cl, entidad);
            } else {
                entidad.setNombTable(cl.getSimpleName());
                readMembers(cl, entidad);
            }
            if (cl.getSuperclass().isAnnotationPresent(MappedSuperclass.class)) {
                clase = cl.getSuperclass();
                MapeaSuperClass(clase, entidad);
            }
            if (cl.isAnnotationPresent(Inheritance.class)) {
                MapeaHerencia(cl);
            }
        } else {
            out.println("Annotations are not present...");
        }
    }


    public void readMembers(Class<?> cl, Entidad entidad) {
        Field[] fields = cl.getDeclaredFields();
        for (Field values : fields) {
            Annotation[] anot = values.getAnnotations();
            if (anot.length != 0) {
                if (values.isAnnotationPresent(Column.class)) {
                    Columna columna = new Columna();
                    columna = defirnirColumna(values, columna);
                    entidad.setColumns(columna);
                    if (values.isAnnotationPresent(Id.class)) {
                        entidad.setPrimaryKey(columna);
                    }
                }
                if (values.isAnnotationPresent(OneToOne.class)) {
                    if (values.isAnnotationPresent(JoinColumn.class)) {

                        JoinColumn jc = values.getAnnotation(JoinColumn.class);
                        OneToOne oto = values.getAnnotation(OneToOne.class);
<<<<<<< HEAD

                        this.MapeaRelOneToOne(jc, oto, entidad);



=======

                        this.MapeaRelOneToOne(jc, oto, entidad);
>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5
                    }
                }
            }
        }

    }

    public Columna defirnirColumna(Field field, Columna columna) {
        //  Columna columna = new Columna();
        Column column = field.getAnnotation(Column.class);
        if (column.name().compareToIgnoreCase("") == 0) {
            columna.setName(field.getName());

        } else {
            columna.setName(column.name());
        }
        if (field.isAnnotationPresent(Lob.class)) {
            if (field.getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                columna.setLob(true);
            } else {
                columna.setLob(false);
            }
        }
        if (field.isAnnotationPresent(Enumerated.class)) {
            getEnumeracion(field, columna);

        }

        if (field.getType().getSimpleName().compareToIgnoreCase("String") == 0) {
            columna.setNombreTipo("VARCHAR ()");
        } else {
            if (field.getType().getSimpleName().compareToIgnoreCase("int") == 0) {
                columna.setNombreTipo("INT");
            } else {
                if (field.getType().getSimpleName().compareToIgnoreCase("float") == 0) {
                    columna.setNombreTipo("FLOAT");
                    columna.setPrecision(column.precision());
                    columna.setScale(column.scale());
                } else {
                    if (field.getType().getSimpleName().compareToIgnoreCase("Double") == 0) {
                        columna.setNombreTipo("DOUBLE");
                        columna.setPrecision(column.precision());
                        columna.setScale(column.scale());
                    } else {
                        if (field.getType().getSimpleName().compareToIgnoreCase("Boolean") == 0) {
                            columna.setNombreTipo("BOOLEAN");
                        }
                    }
                }
            }
        }

        if (column.nullable() == false) {
            columna.setNullable(false);
        } else {
            columna.setNullable(true);
        }
        return columna;

    }

    public void getEnumeracion(Field field, Columna columna) {
        Enumerated enumeracion = field.getAnnotation(Enumerated.class);
        if (field.getType().isEnum()) {
            if (enumeracion.value().toString().compareToIgnoreCase("STRING") == 0) {
                columna.setNombreTipo("VARCHAR ()");
            } else {
                columna.setNombreTipo("INT");
            }
        }
    }

    public void MapeaSuperClass(Class<?> clase, Entidad entidad) {
        boolean seguir = false;
        while (clase.getSimpleName().compareToIgnoreCase("Object") != 0 && seguir == false) {
            if (clase.isAnnotationPresent(MappedSuperclass.class)) {
                readMembers(clase, entidad);
                clase = clase.getSuperclass();

            } else {
                seguir = true;
            }
        }

    }

    public void MapeaHerencia(Class<?> cl) {

        Inheritance ih =cl.getAnnotation(Inheritance.class);

        if(ih.strategy().toString() == "TABLE_PER_CLASS"){




        } else {

        }






<<<<<<< HEAD
    public void MapeaRelOneToOne(JoinColumn jc, OneToOne oto, Entidad entidad) {

        OnetoOneClass relacionNueva = new OnetoOneClass(oto.mappedBy(), jc.name(), jc.referencedColumnName());

=======
    }

    public void MapeaRelOneToOne(JoinColumn jc, OneToOne oto, Entidad entidad) {

        OnetoOneClass relacionNueva = new OnetoOneClass(oto.mappedBy(), jc.name(), jc.referencedColumnName());

>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5
        System.out.println("Relacion con: " + relacionNueva.getrelatedEntity()+ " (clase que posee la primary key)");
        System.out.println("Llave primaria: " + relacionNueva.getPk());
        System.out.println("Llave foranea(de la clase actual): " + relacionNueva.getMyForeignKey());

        if (validations.validarRelacionOTO(relacionNueva) == true) {
            System.out.println("Se encontro la clase relacionada.");
            entidad.relations.listaOneToOne.add(relacionNueva);
        } else {
            System.out.println("No se encontro la clase relacionada.");
        }
    }
}
