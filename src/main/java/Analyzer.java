import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Analyzer {
    List<Entidad> ListaDeEntidades = new ArrayList<>();
    Validations validations=new Validations();


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
            }
            if (cl.getSuperclass().isAnnotationPresent(MappedSuperclass.class)) {
                clase = cl.getSuperclass();
                MapeaSuperClass(clase, entidad);

            }
            if (cl.isAnnotationPresent(Inheritance.class)) {
                MapeaHerencia(cl, entidad);

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
                    columna = defirnirColumna(values,columna);
                    entidad.setColumns(columna);
                    if (values.isAnnotationPresent(Id.class)) {
                        entidad.setPrimaryKey(columna);
                    }


                }
                if (values.isAnnotationPresent(OneToOne.class)) {
                    if (values.isAnnotationPresent(JoinColumn.class)) {


                    }

                }
            }
        }

    }

    public Columna defirnirColumna(Field field,Columna columna) {
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

    public void MapeaHerencia(Class<?> cl, Entidad entidad) {

    }

    public void MapeaRelOneToOne() {
    }

}
