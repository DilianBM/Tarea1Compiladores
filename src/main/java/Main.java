import javax.persistence.Entity;
import java.lang.annotation.Annotation;

import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.*;
import java.util.*;
import java.lang.*;


public class Main {
    /*public static void main(String[] args) throws ClassNotFoundException, IOException {
        List<String> resul = new LinkedList<String>();
        Package[] packages = Package.getPackages();

        for (Package pack : packages) {
            resul.add(pack.getName());
            String scanPackage = pack.getName();
            ClassReader lectorRef = new ClassReader();
            lectorRef.proccesClass(scanPackage);
           /* ClassPathScanningCandidateComponentProvider provide = new ClassPathScanningCandidateComponentProvider(false);
            provide.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) Entity.class));

            ClassPathScanningCandidateComponentProvider provider = provide;
            for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
                try {
                    Class<?> cl = Class.forName(beanDef.getBeanClassName());

                    Entity findable = cl.getAnnotation(Entity.class);
                   System.out.println( "ddd"+cl.getSuperclass().getAnnotation(Inheritance.class).strategy().name());
                    Annotation[] a = cl.getAnnotations();
                    if (a.length != 0) {
                        for (Annotation val : a) {
                            System.out.println(val.annotationType().getSimpleName());
                        }
                    } else {
                        System.out.println("Annotations is not present...");
                    }
                    System.out.printf("Found class: %s, with meta name: %s%n",
                            cl.getSimpleName(), findable.name());
                } catch (Exception e) {
                    System.err.println("Got exception: " + e.getMessage());
                }
            }

        }
    }*/
}

