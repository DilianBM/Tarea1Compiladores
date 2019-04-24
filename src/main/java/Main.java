import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.persistence.*;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.*;
import javax.persistence.Entity;
import java.lang.*;


public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException {

/*
        Class cls = Class.forName("B");
        Field F[] = cls.getDeclaredFields();
        // iterating through all fields of  String class
        for (Field field : F)
        {
            Lob an = field.getAnnotation(Lob.class);
            System.out.println(field.getName()+"    "+field.getType().getSimpleName()+" "+an.annotationType().getSimpleName());
        }
        Method M[] = cls.getMethods();

        // iterating through all methods of Object class
        for (Method method : M)
        {

            System.out.println(method.getName()+"   ");
        }



        Class<?>[] c = Class.class.getDeclaredClasses();

        // returns the ClassLoader object
        ClassLoader cLoader = cls.getClassLoader();


        Class cls2 = Class.forName("java.lang.Thread", true, cLoader);

        // returns the name of the class
      //  System.out.println("Class = " + cls.getName());
        //System.out.println("Class = " + cls2.getName());

    String val=System.getProperty("java.class.path");
        DataInputStream dstream = new DataInputStream(new FileInputStream(val));
        ClassFile cf =  new ClassFile(dstream);
        String className = cf.getName();
        AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
        AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
        for (javassist.bytecode.annotation.Annotation ann : visible.getAnnotations())
        {
            System.out.println("@" + ann.getTypeName());
        }
        */


        Reflections r = new Reflections("Tarea1Compiladores");

        Set<Class<? extends Annotation>> subTypes = r.getSubTypesOf(Annotation.class);
        //  System.out.println("hpña" + subTypes.getClass().getSimpleName());
        Set<Class<?>> annotated = r.getTypesAnnotatedWith(Annotation.class);
        // System.out.println("" + annotated.getClass().getSimpleName());

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        String packageName = System.getProperty("java.class.path");
        // System.out.println("classpath=" + System.getProperty("java.class.path"));
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new FieldAnnotationsScanner())
                .setUrls(ClasspathHelper.forJavaClassPath())
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("java.class.path"))));
        Set<Class<? extends Object>> subType = reflections.getSubTypesOf(Object.class);
        //System.out.println(subType.getClass().getSimpleName() + subType.size());


        //scan urls that contain 'my.package', include inputs starting with 'my.package', use the default scanners
        /*Reflections reflec = new Reflections("java.class.path");

        Set<Class<? extends  Object>> subType = reflec.getSubTypesOf(Object.class);
        System.out.println( "NombClass "+subType.getClass().getSimpleName()+" "+subType.size());

        for (Class<? extends  Object> clazz : subType)
        {

            System.out.println(clazz.getSimpleName());
        }*/

        //System.out.println(" " + subTypes.getClass().getSimpleName());
        //  Set<Field> val = reflections.getFieldsAnnotatedWith(Id.class);
        //System.out.println(" " + val.getClass().getName());

        Map<String, Class<?>> result = new HashMap<>();
        Reflections reflection = new Reflections("java.class.path");
        Set<Class<?>> annotated1 = reflection.getTypesAnnotatedWith(Entity.class);
        // System.out.println("valor"+annotated1.getClass());
        for (Class<?> clazz : annotated1) {
            Entity entity = clazz.getAnnotation(Entity.class);
            result.put(entity.name(), clazz);
            //  System.out.println(entity.getClass().getSimpleName());
        }

        class Obj {
            String nombClass;
            List<String> values;

        }
        Map<String, String> mapa = new HashMap<String, String>();
        Map<Obj, Map<String, String>> mp = new HashMap<Obj, Map<String, String>>();


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


    }


}

