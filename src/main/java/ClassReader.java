import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class ClassReader {
    List<Entidad> ListaDeEntidades = new ArrayList<>();
Analyzer analyzer=new Analyzer();

    public void proccesClass(String Package) {
       // Entidad entidad;
        String scanPackage = Package;
        ClassPathScanningCandidateComponentProvider provide = new ClassPathScanningCandidateComponentProvider(false);
        provide.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) Entity.class));
        ClassPathScanningCandidateComponentProvider provider = provide;
        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            try {
                Class<?> cl = Class.forName(beanDef.getBeanClassName());//obtengo un Class de ese paquete
                if (cl.isAnnotationPresent(Entity.class)) {
                    analyzer.procesaEntidades(cl);
                }
            } catch (Exception e) {
                System.err.println("Got exception: " + e.getMessage());
            }
        }

    }
}
