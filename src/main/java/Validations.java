import Relaciones.OnetoOneClass;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.Id;
=======
import javax.persistence.*;
>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Validations {
    public void validadExistenciasPK(Entidad entidad) {
        if (entidad.getPrimaryKey() == null) {
            System.out.println("Error no existe PK para la entidad " + entidad.getNombTable());
        }
    }

    public boolean validarRelacionOTO(OnetoOneClass c) {

        String entidadRelacionada = c.getrelatedEntity();
        String llaveP = c.getPk();

        List<String> resul = new LinkedList<String>();
        Package[] packages = Package.getPackages();
        for (Package pack : packages) {
            resul.add(pack.getName());
            String scanPackage = pack.getName();
            ClassPathScanningCandidateComponentProvider provide = new ClassPathScanningCandidateComponentProvider(false);
            provide.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) Entity.class));
            ClassPathScanningCandidateComponentProvider provider = provide;
            for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
                try {
                    Class<?> cl = Class.forName(beanDef.getBeanClassName());//obtengo un Class de ese paquete
                    if (cl.isAnnotationPresent(Entity.class)) {

                        if (entidadRelacionada.equalsIgnoreCase(cl.getSimpleName())) {//valida que exista la clase relacionada
<<<<<<< HEAD
                            if(true){//aqui se puede implementar la validacion de que la PK coincida.
                                return true;
                            }

=======

                            Field[] fields = cl.getDeclaredFields();
                            for (Field values : fields) {
                                if (values.isAnnotationPresent(Id.class)) {

                                    return true;

                                } else {
                                    System.out.println("Se encontro la clase pero no tiene ID");
                                    return false;
                                }
                            }
>>>>>>> 94c87705ea5d82a15f845f7d7bc0b9aad9fa7fb5
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("Got exception: " + ex.getMessage());
                }
            }
        }
        return false;
    }
}