import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static java.lang.System.out;

public class LectorRef {
    public void proccesAnnotations(String Package) {
        String scanPackage = Package;
        ClassPathScanningCandidateComponentProvider provide = new ClassPathScanningCandidateComponentProvider(false);
        provide.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) Entity.class));
        ClassPathScanningCandidateComponentProvider provider = provide;
        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            try {
                Class<?> cl = Class.forName(beanDef.getBeanClassName());//obtengo un Class de ese paquete
                readClass(cl);
                Field[] fields = cl.getFields();
                readMembers(cl);
            } catch (Exception e) {
                System.err.println("Got exception: " + e.getMessage());
            }
        }
    }

    public void readClass(Class<?> cl) {

        Entity findable = cl.getAnnotation(Entity.class);//ve a ver si la clase esta anotada

        out.printf("Found class: %s, with meta name: %s%n",
                cl.getSimpleName(), findable.name());

        Annotation[] anotations = cl.getAnnotations();

        if (anotations.length != 0) {
            String anot = "";

            if (cl.isAnnotationPresent(Table.class)) {
               System.out.println( metodoTabla(cl));
            }

        } else {
            out.println("Annotations are not present...");
        }


    }


    public String metodoTabla(Class<?> cl) {

        Table tabla = cl.getAnnotation(Table.class);

        return "CREATE TABLE " + tabla.name() +"(";
    }


    public void readMembers(Class<?> cl) {
        Field[] fields = cl.getFields();
        for (Field values : fields) {
            Annotation[] anot = values.getAnnotations();
            if (anot.length != 0) {
                for (Annotation val : anot) {
                    if (values.isAnnotationPresent(Column.class)) {
                        Column column = values.getAnnotation(Column.class);
                        out.println(column.name() + " " + column.nullable() + " " + column.updatable() + " " + column.length() + " " + column.precision());

                    } else {
                        continue; // no tiene anotacion
                    }
                }
            }
        }

    }




}
