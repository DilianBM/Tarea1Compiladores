import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class LectorRef {
    List<Entidad> ListaDeEntidades = new ArrayList<>();


    public void proccesAnnotations(String Package) {
        Entidad entidad;
        String scanPackage = Package;
        ClassPathScanningCandidateComponentProvider provide = new ClassPathScanningCandidateComponentProvider(false);
        provide.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) Entity.class));
        ClassPathScanningCandidateComponentProvider provider = provide;
        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            try {
                Class<?> cl = Class.forName(beanDef.getBeanClassName());//obtengo un Class de ese paquete
                if (cl.isAnnotationPresent(Entity.class)) {
                    entidad = new Entidad();
                    readClass(cl, entidad);
                    ListaDeEntidades.add(entidad);
                    System.out.println(entidad.imprimecolumns());

                }

                //    Entity findable = cl.getAnnotation(Entity.class);//ve a ver si la clase esta anotada


            } catch (Exception e) {
                System.err.println("Got exception: " + e.getMessage());
            }
        }

    }

    public void readClass(Class<?> cl, Entidad entidad) {
        Class<?> clase = null;
        Annotation[] anotations = cl.getAnnotations();
        if (anotations.length != 0) {
            // System.out.println(anota.annotationType().getSimpleName());
            if (cl.isAnnotationPresent(Table.class)) {
                Table tabla = cl.getAnnotation(Table.class);
                entidad.setNombTable(tabla.name());
                System.out.println(entidad.getNombTable());
                readMembers(cl, entidad);
            }
            if (cl.getSuperclass().isAnnotationPresent(MappedSuperclass.class)) {
                clase = cl.getSuperclass();
                while (clase.getSimpleName().compareToIgnoreCase("Object") != 0) {
                   // readMembers(clase,entidad);
                    clase = clase.getSuperclass();

                }

            }


        } else {
            out.println("Annotations are not present...");
        }


    }


    public String metodoTabla(Class<?> cl) {

        Table tabla = cl.getAnnotation(Table.class);

        return "CREATE TABLE " + tabla.name() + "(";
    }


    public void readMembers(Class<?> cl, Entidad entidad) {
        Field[] fields = cl.getDeclaredFields();
        for (Field values : fields) {
            Annotation[] anot = values.getAnnotations();
            if (anot.length != 0) {
               // for (Annotation val : anot) {
                    if (values.isAnnotationPresent(Column.class)) {
                        Columna columna = new Columna();
                        columna = defirnirColumna(values);

                        entidad.setColumns(columna);
                       /* for (int i = 0; i < entidad.columns.size(); i++) {

                            if (entidad.columns.get(i).getName().equals(columna.getName())) {
                                System.out.println("Este perfil existe: ");
                            } else {
                                entidad.setColumns(columna);

                            }
                        }*/
                        if (values.isAnnotationPresent(Id.class)) {
                            entidad.setPrimaryKey(columna);
                        }
                    }
                    if (values.isAnnotationPresent(Enumerated.class)) {

                        getEnumeracion(values);

                    }
                }
           // }
        }

    }

    public Columna defirnirColumna(Field field) {
        Columna columna = new Columna();
        Column column = field.getAnnotation(Column.class);
        if (column.name().compareToIgnoreCase("")== 0) {
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
//System.out.println(columna.getName()+"  "+"Tipo de columna "+columna.getNombreTipo());
        return columna;

    }

    public String getEnumeracion(Field field) {
        Enumerated enumeracion = field.getAnnotation(Enumerated.class);
        // System.out.println("val" + enumeracion.value().name());
        if (enumeracion.value().toString().compareToIgnoreCase("STRING") == 0) {
        } else {


        }
        return "";
    }

    public void MapeaSuperClass(Class<?> cl) {
        if (cl.getSimpleName().compareToIgnoreCase("Object") == 0) {
            // readMembers(cl, entidad);


        } else {
            if (cl.getSuperclass().isAnnotationPresent(MappedSuperclass.class)) {
             //   readMembers(cl, entidad);
             //   System.out.println(entidad.imprimecolumns());
                MapeaSuperClass(cl.getSuperclass());

            }

        }

       /* clase = cl.getSuperclass();
        while (clase.getSimpleName().compareToIgnoreCase("Object") != 0) {

            clase = clase.getSuperclass();

        }*/

    }

}
