import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

//Clase encargada de recibir el paquete en el cual se encuentran las clases que se quieren mapear a una base de datos relacional
public class ClassReader {
    Analyzer analyzer = new Analyzer();
    List<Class<?>> clases = new ArrayList<>();

    public List<Class<?>> getClases() {
        return clases;
    }

    //Recibe el nombre literal del paquete y extrae de el las clases que tengan la anotacion @Entity
    public void proccesClass(String Package) {

        String scanPackage = Package;
        ClassPathScanningCandidateComponentProvider provide = new ClassPathScanningCandidateComponentProvider(false);
        provide.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) Entity.class));
        ClassPathScanningCandidateComponentProvider provider = provide;
        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            try {
                Class<?> cl = Class.forName(beanDef.getBeanClassName());//obtengo un Class de ese paquete
                if (cl.isAnnotationPresent(Entity.class)) {
                    clases.add(cl);
                    //analyzer.procesaEntidades(cl);
                }
            } catch (Exception e) {
                System.err.println("Got exception: " + e.getMessage());
            }
        }


    }
}
