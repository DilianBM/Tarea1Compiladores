import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

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
            } catch (Exception e) {
                System.err.println("Got exception: " + e.getMessage());
            }
        }

    }

    public void readClass(Class<?> cl) {

        Entity findable = cl.getAnnotation(Entity.class);//ve a ver si la clase esta anotada
        //  System.out.println("ddd" + cl.getSuperclass().getAnnotation(Inheritance.class).strategy().name());
        Annotation[] a = cl.getAnnotations();
        if (a.length != 0) {
            for (Annotation val : a) {//Recupera las anotaciones de esa clase
                //Hacer un switch para cada caso de las posibles anotaciones que tiene la clase
                System.out.println(val.annotationType().getSimpleName());
            }
        } else {
            System.out.println("Annotations is not present...");
        }
        System.out.printf("Found class: %s, with meta name: %s%n",
                cl.getSimpleName(), findable.name());

    }

    public void readMembers(Field[] fields) {


    }


}
