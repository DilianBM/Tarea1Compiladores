import Relaciones.OnetoManyClass;
import Relaciones.OnetoOneClass;
import net.bytebuddy.asm.Advice;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Validations {
    DirectedGraph<String, DefaultEdge> g;

    public Validations() {

        g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    }

    public void validadExistenciasPK(List<Entidad> entidads) {
        for (int i = 0; i < entidads.size(); i++) {
            if (entidads.get(i).getPrimaryKey() == null) {
                System.out.println("Error no existe PK para la entidad " + entidads.get(i).getNombTable());
            }

        }

    }


    public String[] validarRelacionOTO(OnetoOneClass c, String entidadRel, List<Class<?>> cls) {
        String vect[] = new String[5];
        String entidadRelacionada = entidadRel;
        String llaveP = c.getPk();
        boolean existeclass = false;
        List<String> resul = new LinkedList<String>();
        Package[] packages = Package.getPackages();
        for (int i = 0; i < cls.size(); i++) {
            try {
                //  Class<?> cl = Class.forName(beanDef.getBeanClassName());//obtengo un Class de ese paquete
                Class<?> cl = cls.get(i);
                if (cl.isAnnotationPresent(Entity.class)) {
                    if (entidadRelacionada.equalsIgnoreCase(cl.getSimpleName())) {//valida que exista la clase relacionada
                        vect[0] = "true";
                        existeclass = true;
                        if (cl.isAnnotationPresent(Entity.class)) {
                            if (cl.isAnnotationPresent(Table.class)) {
                                Table tabla = cl.getAnnotation(Table.class);
                                vect[1] = tabla.name();

                            } else {
                                vect[1] = cl.getSimpleName();


                            }
                        }

                        Field[] fields = cl.getDeclaredFields();
                        for (Field values : fields) {
                            if (values.isAnnotationPresent(Id.class)) {
                                Column column=values.getAnnotation(Column.class);
                                if (column.name().compareToIgnoreCase("") != 0) {
                                    vect[2]=column.name();

                                } else {
                                    vect[2] = values.getName();
                                }

                                vect[3] = values.getType().getSimpleName();

                            }
                            if (values.isAnnotationPresent(OneToOne.class)) {
                                vect[4] = values.getName();

                            }
                        }

                        return vect;
                    } else {
                        existeclass = false;
                        vect[0] = "false";
                    }
                }
            } catch (Exception ex) {
                System.err.println("Got exception: " + ex.getMessage());
            }
        }
        if (existeclass == true) {

            vect[0] = "true";

        } else {

            vect[0] = "false";
        }

        return vect;
    }


    public String[] validarRelacionOTM(OnetoManyClass c, String entidadRel, List<Class<?>> cls) {
        String vect[] = new String[5];
        String entidadRelacionada = entidadRel;
        for (int i = 0; i < cls.size(); i++) {
            try {
                Class<?> cl = cls.get(i);
                if (cl.isAnnotationPresent(Entity.class)) {
                    if (entidadRelacionada.equalsIgnoreCase(cl.getSimpleName())) {//valida que exista la clase relacionada
                        vect[0] = "true";
                        if (cl.isAnnotationPresent(Entity.class)) {
                            if (cl.isAnnotationPresent(Table.class)) {
                                Table tabla = cl.getAnnotation(Table.class);
                                vect[1] = tabla.name();
                            } else {
                                vect[1] = cl.getSimpleName();

                            }
                        }

                        Field[] fields = cl.getDeclaredFields();
                        for (Field values : fields) {
                            if (values.isAnnotationPresent(Id.class)) {
                                Column column=values.getAnnotation(Column.class);
                                if (column.name().compareToIgnoreCase("") != 0) {
                                    vect[2]=column.name();

                                } else {
                                    vect[2] = values.getName();
                                }
                                vect[3] = values.getType().getSimpleName();

                            }
                            if (values.isAnnotationPresent(OneToMany.class)) {
                                vect[4] = values.getName();
                            }
                        }

                        return vect;

                    } else {

                        vect[0] = "false";
                    }
                }
            } catch (Exception ex) {
                System.err.println("Got exception: " + ex.getMessage());
            }
        }


        return vect;
    }


    public String[] validarRelacionMTM(String nombreTargetEntity, List<Class<?>> cls) {
        String vect[] = new String[4]; //i=0 existe, i=1 nombre de la tabla, i=2 nombre de la pk, i=3 tipo de la pk
        vect[0] = "false";
        for (int i = 0; i < cls.size(); i++) {
            try {
                Class<?> cl = cls.get(i);
                if (cl.isAnnotationPresent(Entity.class)) {
                    if (nombreTargetEntity.equalsIgnoreCase(cl.getSimpleName())) {//valida que exista la clase relacionada

                        vect[0] = "true";

                        if (cl.isAnnotationPresent(Table.class)) {
                            Table tabla = cl.getAnnotation(Table.class);
                            vect[1] = tabla.name();

                        } else {
                            vect[1] = cl.getSimpleName();
                        }

                        Field[] fields = cl.getDeclaredFields();
                        for (Field values : fields) {
                            if (values.isAnnotationPresent(Id.class)) {

                                //  System.out.println("Llave primaria mtm: " + values.getName() + " Tipo: " + values.getType().getSimpleName());
                                Column column=values.getAnnotation(Column.class);
                                if (column.name().compareToIgnoreCase("") != 0) {
                                    vect[2]=column.name();

                                } else {
                                    vect[2] = values.getName();
                                }
                                vect[3] = values.getType().getSimpleName();
                            }
                        }
                        return vect;
                    }
                }
            } catch (Exception ex) {
                System.err.println("Got exception: " + ex.getMessage());
            }
        }
        if (vect[0].compareToIgnoreCase("false") == 0) {
            System.out.println("No existe la clase relacionada " + nombreTargetEntity);
            System.exit(0);
        }

        return vect;
    }


    public String[] validarRelacionMTO(String nombreTargetEntity, List<Class<?>> cls) {
        String vect[] = new String[4]; //i=1 existe, i=2 nombre de la tabla, i=3 nombre de la pk, i=4 tipo de la pk
        vect[0] = "false";
        for (int i = 0; i < cls.size(); i++) {
            try {
                Class<?> cl = cls.get(i);
                if (cl.isAnnotationPresent(Entity.class)) {
                    if (nombreTargetEntity.equalsIgnoreCase(cl.getSimpleName())) {//valida que exista la clase relacionada

                        // System.out.println("Encontro la entidad " + nombreTargetEntity);

                        vect[0] = "true";

                        if (cl.isAnnotationPresent(Table.class)) {
                            Table tabla = cl.getAnnotation(Table.class);
                            vect[1] = tabla.name();

                        } else {
                            vect[1] = cl.getSimpleName();
                        }


                        Field[] fields = cl.getDeclaredFields();
                        for (Field values : fields) {
                            if (values.isAnnotationPresent(Id.class)) {
                                Column column=values.getAnnotation(Column.class);
                                if (column.name().compareToIgnoreCase("") != 0) {
                                    vect[2]=column.name();

                                } else {
                                    vect[2] = values.getName();
                                }
                                vect[3] = values.getType().getSimpleName();

                            }
                        }


                        return vect;
                    }
                }
            } catch (Exception ex) {
                System.err.println("Got exception: " + ex.getMessage());
            }
        }

        return vect;
    }


    public Graph<String, DefaultEdge> getDirectedGraph(List<Entidad> entidads) {

        for (int i = 0; i < entidads.size(); i++) {
            g.addVertex(entidads.get(i).nombTable);

        }
        for (int i = 0; i < entidads.size(); i++) {

            for (int j = 0; j < entidads.get(i).listaOneToOne.size(); j++) {
                g.addEdge(entidads.get(i).nombTable, entidads.get(i).getLista1().get(j).getTargetEntity());
            }
            for (int j = 0; j < entidads.get(i).getListaOneToMany().size(); j++) {

                g.addEdge(entidads.get(i).nombTable, entidads.get(i).getListaOneToMany().get(j).getTargetEntity());
            }
            for (int j = 0; j < entidads.get(i).listaManyToOne.size(); j++) {
                g.addEdge(entidads.get(i).nombTable, entidads.get(i).listaManyToOne.get(j).getTargetEntity());

            }
        }


        return g;
    }

    public void whenCheckCycles_thenDetectCycles(Graph<String, DefaultEdge> gr) {
        CycleDetector<String, DefaultEdge> cycleDetector
                = new CycleDetector<String, DefaultEdge>(g);
        boolean ciclo = cycleDetector.detectCycles();
        if (ciclo) {
            System.out.println("SI hay ciclos");
            System.exit(0);
        } else {
            System.out.println("NO hay ciclos");

        }
        // Set<String> cycleVertices = cycleDetector.findCycles();


    }




}
