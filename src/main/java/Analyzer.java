import ClasesPrueba.C;
import Relaciones.OnetoOneClass;
import Relaciones.Relaciones;
import Relaciones.OnetoManyClass;
import Relaciones.ManytoOneClass;
import Relaciones.ManytoManyClass;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

//Clase encargada de interpretar las clases y generar una representacion intermedia
public class Analyzer {
    //List<Entidad> ListaDeEntidades = new ArrayList<>();
    IR ir = new IR();
    Validations validations = new Validations();
    List<Class<?>> cls = new ArrayList<>();
    List<Entidad> ent = new ArrayList<Entidad>();


    //Recibe la lista de clases e itera sobre ella para generar las instancias de entidades
    public void procesaEntidades(List<Class<?>> cl) {
        cls = cl;
        for (int i = 0; i < cl.size(); i++) {
            Entidad entidad = new Entidad();
            ProcessClassAnnotations(cl.get(i), entidad);


        }
        for (int i = 0; i < ir.ListaDeEntidades.size(); i++) {
            for (int j = 0; j < ent.size(); j++) {
                if (ir.ListaDeEntidades.get(i).getNombTable().compareToIgnoreCase(ent.get(j).nombTable) == 0) {
                    ir.ListaDeEntidades.remove(i);
                }
            }
        }
        for (int i = 0; i < ir.ListaDeEntidades.size(); i++) {

        }

    }

    //Metodo encargado de de validar que no existan ciclos y de que exista una llave primaria para cada entidad
    public void validationsCicles() {
        validations.whenCheckCycles_thenDetectCycles(validations.getDirectedGraph(ir.ListaDeEntidades));

        validations.validadExistenciasPK(ir.ListaDeEntidades);
    }


    //Recibe una clase y una instancia de entidad en la cual va a guardar toda la informacion referente a las anotaciones de clase
    // Verifica la existencia de las anotaciones @Table, @Inheritance, @MappedSupperClass y @DiscriminatorColumn
    public void ProcessClassAnnotations(Class<?> cl, Entidad entidad) {
        Class<?> clase = null;
        Annotation[] anotations = cl.getAnnotations();
        if (anotations.length != 0) {
            if (cl.isAnnotationPresent(Table.class)) {
                Table tabla = cl.getAnnotation(Table.class);
                entidad.setNombTable(tabla.name());
                if (!cl.isAnnotationPresent(Inheritance.class)) {
                    readMembers(cl, entidad);
                    ir.ListaDeEntidades.add(entidad);
                }
            } else {
                entidad.setNombTable(cl.getSimpleName());
                if (!cl.isAnnotationPresent(Inheritance.class)) {
                    readMembers(cl, entidad);
                    ir.ListaDeEntidades.add(entidad);
                }
            }
            if (cl.getSuperclass().isAnnotationPresent(MappedSuperclass.class)) {
                clase = cl.getSuperclass();
                MapeaSuperClass(clase, entidad);
                ir.ListaDeEntidades.add(entidad);
            }
            if (cl.isAnnotationPresent(Inheritance.class)) {
                Inheritance inheritance = cl.getAnnotation(Inheritance.class);
                EntidadHerencia entidadHerencia = new EntidadHerencia();
                Entidad entidad1 = new Entidad();
                if (cl.isAnnotationPresent(Table.class)) {
                    Table tabla = cl.getAnnotation(Table.class);
                    entidad1.setNombTable(tabla.name());
                } else {
                    entidad1.setNombTable(cl.getSimpleName());

                }
                entidadHerencia.setEstrategia(inheritance.strategy().name());

                readMembers(cl, entidad1);


                if (cl.isAnnotationPresent(DiscriminatorColumn.class)) {
                    DiscriminatorColumn discriminatorColumn = cl.getAnnotation(DiscriminatorColumn.class);
                    entidadHerencia.setDiscriminatorColumn(discriminatorColumn.name());

                }
                entidadHerencia.setEntidad(entidad1);
                //  ir.ListaDeEntidadesInheritance.add(entidadHerencia);


                mapeaHerencia(cls, cl.getSimpleName(), entidadHerencia, cl);


            }
        } else {
            out.println("Annotations are not present...");
        }

    }

    //Recibe una clase y una instancia de entidad en la cual va a guardar toda la informacion referente a las anotaciones de los
    // atributos de la clase. Verifica la existencia de las anotaciones @Id, @Column, @OneToOne, @OneToMany, @ManyToOne, @ManyToMany
    public void readMembers(Class<?> cl, Entidad entidad) {
        Field[] fields = cl.getDeclaredFields();
        Columna columna2;
        Columna columna;
        String pkType = "";
        for (Field values : fields) {


            values.setAccessible(true);
            if (values.isAnnotationPresent(Id.class)) {
                columna2 = new Columna();
                columna2 = defirnirColumna(values, columna2);
                entidad.setPrimaryKey(columna2);
                pkType = values.getType().getSimpleName();

            }
            if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToOne.class) != true && values.isAnnotationPresent(OneToMany.class) != true && values.isAnnotationPresent(ManyToOne.class) != true && values.isAnnotationPresent(ManyToMany.class) != true && !values.isAnnotationPresent(Id.class)) {
                columna = new Columna();
                columna = defirnirColumna(values, columna);
                entidad.setColumns(columna);

            }
            if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToOne.class) == true && !values.isAnnotationPresent(Id.class)) {
                columna = new Columna();
                MapeaOneToOne(entidad, values, columna);


            }

            if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToMany.class) == true && !values.isAnnotationPresent(Id.class)) {
                Columna columna1 = new Columna();
                this.MapeaOneToMany(entidad, values, columna1);
            }

            if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(ManyToOne.class) == true && !values.isAnnotationPresent(Id.class)) {
                Columna columna1 = new Columna();
                this.MapeaManyToOne(entidad, values, columna1);
            }

            if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(ManyToMany.class) == true && !values.isAnnotationPresent(Id.class)) {
                this.MapeaManyToMany(entidad, values, pkType);
            }

        }
    }


    //Recibe un "field" o atributo de la clase, el cual mapea en una instancia de columna
    //Verifica la existencia de las anotaciones @Lob y @Enumarated,
    public Columna defirnirColumna(Field field, Columna columna) {
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

        columna.setLength(column.length());

        return columna;
    }


    //Recibe un "field" o campo que tiene la anotacion @Enumerated y lo mapea en una instancia de columna
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


    //Recibe una clase y una entidad
    //Mapea en la entidad para cuando la clase trae la anotacion @MappedSupperclass
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

    //Recibe una lista de clases, un nombre de entidad, una entidad de herencia y una clase padre
    //Toma la lista de clases e itera sobre ella buscando cuales subclases heredan de la clase padre y asi genera una entidad
    //que representa la ISA
    public void mapeaHerencia(List<Class<?>> cl, String nomEntidad, EntidadHerencia entidadesHerencia, Class<?> clasePadre) {
        List<Entidad> listtmp = new ArrayList<>();
        Class<?> clase = null;
        boolean estarep = false;
        for (int i = 0; i < cl.size(); i++) {
            Entidad entidadTemp = new Entidad();
            clase = cl.get(i);

            if (!clase.isAnnotationPresent(Inheritance.class)) {
                while (!clase.isAnnotationPresent(Inheritance.class) && clase.getSimpleName().compareToIgnoreCase("Object") != 0) {
                    if (clase.isAnnotationPresent(Table.class)) {
                        Table tabla = clase.getAnnotation(Table.class);
                        entidadTemp.setNombTable(tabla.name());
                    } else {
                        entidadTemp.setNombTable(clase.getSimpleName());
                    }

                    readMembers(clase, entidadTemp);


                    listtmp.add(entidadTemp);

                    // listtmp.add(entidadTemp);
                    clase = clase.getSuperclass();

                }

            }
            boolean esta = false;
            if (nomEntidad.compareToIgnoreCase(clase.getSimpleName()) != 0) {
                listtmp.clear();
            } else {
                for (int k = 0; k < listtmp.size(); k++) {
                    for (int l = 0; l < ent.size(); l++) {
                        if (listtmp.get(k).getNombTable().compareToIgnoreCase(ent.get(l).getNombTable()) == 0) {
                            esta = true;
                        }
                    }
                    if (!esta) {
                        ent.add(listtmp.get(k));
                    }
                    esta = false;
                }
            }
        }

        //  ir.entidadesconherencia.put(entidadesHerencia, listtmp);
        if (entidadesHerencia.getEstrategia().compareToIgnoreCase("SINGLE_TABLE") == 0) {
        }


        InheritanceSingleTable(entidadesHerencia, ent, clasePadre);


    }

    //Recibe una entidad de herencia y una lista de entidades
    //Extrae las relaciones de las superclases para heredarlas a la subclase
    public void InheritanceSingleTable(EntidadHerencia entidadHerencia, List<Entidad> list, Class<?> clasePadre) {
        Entidad entidad = new Entidad();
        entidad.setNombTable(entidadHerencia.getEntidad().nombTable + "_new");
        //  readMembers(clasePadre,entidad);
        if (entidadHerencia.DiscriminatorColumn.compareToIgnoreCase("") != 0) {

            Columna columna = new Columna();
            columna.setName(entidadHerencia.DiscriminatorColumn);
            columna.setNombreTipo("varchar ()");
            columna.setLength(255);
            entidad.setColumns(columna);
            entidad.setPrimaryKey(entidadHerencia.getEntidad().primaryKey);
            for (int i = 0; i < entidadHerencia.getEntidad().columns.size(); i++) {
                entidad.columns.add(entidadHerencia.getEntidad().columns.get(i));

            }


            for (int i = 0; i < entidadHerencia.getEntidad().listaOneToOne.size(); i++) {
                entidad.listaOneToOne.add(entidadHerencia.getEntidad().listaOneToOne.get(i));
            }
            for (int i = 0; i < entidadHerencia.getEntidad().getListaOneToMany().size(); i++) {
                entidad.getListaOneToMany().add(entidadHerencia.entidad.getListaOneToMany().get(i));

            }
            for (int i = 0; i < entidadHerencia.getEntidad().listaManyToOne.size(); i++) {
                entidad.listaManyToOne.add(entidadHerencia.entidad.listaManyToOne.get(i));

            }
            for (int i = 0; i < entidadHerencia.getEntidad().getListaManyToMany().size(); i++) {
                entidad.getListaManyToMany().add(entidadHerencia.entidad.getListaManyToMany().get(i));

            }

            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).getColumns().size(); j++) {
                    entidad.columns.add(list.get(i).getColumns().get(j));
                }
                for (int k = 0; k < list.get(i).listaOneToOne.size(); k++) {
                    entidad.listaOneToOne.add(list.get(i).listaOneToOne.get(k));
                }
                for (int k = 0; k < list.get(i).getListaOneToMany().size(); k++) {
                    entidad.getListaOneToMany().add(list.get(i).getListaOneToMany().get(k));
                }
                for (int k = 0; k < list.get(i).listaManyToOne.size(); k++) {
                    entidad.listaManyToOne.add(list.get(i).listaManyToOne.get(k));
                }
                for (int k = 0; k < list.get(i).getListaManyToMany().size(); k++) {
                    entidad.getListaManyToMany().add(list.get(i).getListaManyToMany().get(k));
                }


            }


        }


        ir.ListaDeEntidades.add(entidad);

    }


    //Recibe una entidad, un "field" o campo y una columna
    //Se encarga de generar una columna que representa la llave foranea de una relacion OneToOne y la guarda en su respectiva entidad
    public void MapeaOneToOne(Entidad entidad, Field values, Columna columna) {
        OneToOne oto = values.getAnnotation(OneToOne.class);
        Column column = null;
        OnetoOneClass relacionNueva = new OnetoOneClass();
        boolean esdueña = false;

        if (values.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn jc = values.getAnnotation(JoinColumn.class);
            if (jc.name().compareToIgnoreCase("") != 0) {
                relacionNueva.setNameJoinColumn(jc.name() + entidad.getNombTable());
                System.out.println(jc.name());
            } else {
                relacionNueva.setNameJoinColumn(values.getName() + "_id");
            }
            esdueña = true;
        } else {
            relacionNueva.setNameJoinColumn(values.getName() + "_id");
        }

        String prue = values.getType().getSimpleName();
        String vect[] = new String[5];
        vect = validations.validarRelacionOTO(relacionNueva, prue, cls);
        if (!values.isAnnotationPresent(JoinColumn.class)) {
            if (vect[4].compareToIgnoreCase(oto.mappedBy()) != 0) {
                System.out.println("Error en el MappedBy tiene que nombrarlo" + vect[4]);
            }
        }

        if (vect[0].compareToIgnoreCase("true") == 0 && esdueña) {

            relacionNueva.setTargetEntity(vect[1]);
            relacionNueva.setPk(vect[2]);
            columna.setName(relacionNueva.getNameJoinColumn());
            if (values.isAnnotationPresent(Lob.class)) {
                if (values.getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                    columna.setLob(true);
                } else {
                    columna.setLob(false);
                }
            }
            if (values.isAnnotationPresent(Enumerated.class)) {
                getEnumeracion(values, columna);

            }
            column = values.getAnnotation(Column.class);

            if (vect[3].compareToIgnoreCase("String") == 0) {
                columna.setNombreTipo("VARCHAR ()");
            } else {
                if (vect[3].compareToIgnoreCase("int") == 0) {
                    columna.setNombreTipo("INT");
                } else {
                    if (vect[3].compareToIgnoreCase("float") == 0) {
                        columna.setNombreTipo("FLOAT");
                        columna.setPrecision(column.precision());
                        columna.setScale(column.scale());
                    } else {
                        if (vect[3].compareToIgnoreCase("Double") == 0) {
                            columna.setNombreTipo("DOUBLE");
                            columna.setPrecision(column.precision());
                            columna.setScale(column.scale());

                        } else {
                            if (vect[3].compareToIgnoreCase("Boolean") == 0) {
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

            entidad.listaOneToOne.add(relacionNueva);
            columna.esdeRelacion = true;
            columna.setLength(column.length());
            entidad.setColumns(columna);


        } else {
            if (vect[0].compareToIgnoreCase("true") != 0) {
                System.out.println("No existe la clase relacionada" + prue);
                System.exit(0);
            }
        }

    }

    //Recibe una entidad, un "field" o campo y una columna
    //Se encarga de generar una columna que representa la llave foranea de una relacion OneToMany y la guarda en su respectiva entidad
    public void MapeaOneToMany(Entidad entidad, Field values, Columna columna) {
        OneToMany otm = values.getAnnotation(OneToMany.class);
        OnetoManyClass relacionNueva = new OnetoManyClass();
        boolean esdueña = false;
        if (values.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn jc = values.getAnnotation(JoinColumn.class);
            if (jc.name().compareToIgnoreCase("") != 0) {
                relacionNueva.setNameJoinColumn(jc.name());
            } else {
                relacionNueva.setNameJoinColumn(values.getName() + "_id");
            }

            esdueña = true;

        } else {
            relacionNueva.setNameJoinColumn(values.getName() + "_id");
        }

        String prue = values.getType().getSimpleName();
        String vect[] = new String[5];
        vect = validations.validarRelacionOTM(relacionNueva, prue, cls);

        if (!values.isAnnotationPresent(JoinColumn.class)) {
            if (vect[4] != null) {
                if (vect[4].compareToIgnoreCase(otm.mappedBy()) != 0) {
                    System.out.println("Error en el MappedBy tiene que nombrarlo" + vect[4]);
                }
            }

        }


        if (vect[0].compareToIgnoreCase("true") == 0 && esdueña) {

            relacionNueva.setTargetEntity(vect[1]);
            relacionNueva.setPk(vect[2]);
            columna.setName(relacionNueva.getNameJoinColumn());
            if (values.isAnnotationPresent(Lob.class)) {
                if (values.getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                    columna.setLob(true);
                } else {
                    columna.setLob(false);
                }
            }
            if (values.isAnnotationPresent(Enumerated.class)) {
                getEnumeracion(values, columna);

            }
            Column column = values.getAnnotation(Column.class);
            if (vect[3].compareToIgnoreCase("String") == 0) {
                columna.setNombreTipo("VARCHAR ()");

            } else {
                if (vect[3].compareToIgnoreCase("int") == 0) {
                    columna.setNombreTipo("INT");
                } else {
                    if (vect[3].compareToIgnoreCase("float") == 0) {
                        columna.setNombreTipo("FLOAT");
                        columna.setPrecision(column.precision());
                        columna.setScale(column.scale());
                    } else {
                        if (vect[3].compareToIgnoreCase("Double") == 0) {
                            columna.setNombreTipo("DOUBLE");
                            columna.setPrecision(column.precision());
                            columna.setScale(column.scale());

                        } else {
                            if (vect[3].compareToIgnoreCase("Boolean") == 0) {
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

            entidad.ListaOneToMany.add(relacionNueva);
            columna.esdeRelacion = true;
            columna.setLength(column.length());
            entidad.setColumns(columna);

        } else {
            System.out.println("No existe la clase relacionada" + prue);
            System.exit(0);
        }
    }

    //Recibe una entidad, un "field" o campo y una columna
    //Se encarga de generar una columna que representa la llave foranea de una relacion ManyToOne y la guarda en su respectiva entidad
    public void MapeaManyToOne(Entidad entidad, Field values, Columna columna) {

        OneToMany otm = values.getAnnotation(OneToMany.class);
        ManytoOneClass relacionNueva = new ManytoOneClass();

        if (values.isAnnotationPresent(JoinColumn.class) && values.getAnnotation(JoinColumn.class).name().compareToIgnoreCase("") != 0) {
            JoinColumn jc = values.getAnnotation(JoinColumn.class);
            relacionNueva.setNameJoinColumn(jc.name());

        } else {
            relacionNueva.setNameJoinColumn(values.getName() + "_id");
        }

        String vect[] = validations.validarRelacionMTO(values.getType().getSimpleName(), cls);

        if (vect[0].compareToIgnoreCase("true") == 0) {

            relacionNueva.setTargetEntity(vect[1]);
            relacionNueva.setPk(vect[2]);

            if (values.isAnnotationPresent(Lob.class)) {
                if (values.getType().getSimpleName().compareToIgnoreCase("String") == 0) {
                    columna.setLob(true);
                } else {
                    columna.setLob(false);
                }
            }

            if (values.isAnnotationPresent(Enumerated.class)) {
                getEnumeracion(values, columna);
            }

            Column column = values.getAnnotation(Column.class);

            if (vect[3].compareToIgnoreCase("String") == 0) {
                columna.setNombreTipo("VARCHAR ()");
            }

            if (vect[3].compareToIgnoreCase("int") == 0) {
                columna.setNombreTipo("INT");
            }

            if (vect[3].compareToIgnoreCase("float") == 0) {
                columna.setNombreTipo("FLOAT");
                columna.setPrecision(column.precision());
                columna.setScale(column.scale());
            }

            if (vect[3].compareToIgnoreCase("Double") == 0) {
                columna.setNombreTipo("DOUBLE");
                columna.setPrecision(column.precision());
                columna.setScale(column.scale());
            }

            if (vect[3].compareToIgnoreCase("Boolean") == 0) {
                columna.setNombreTipo("BOOLEAN");
            }

            columna.setName(relacionNueva.getNameJoinColumn());

            entidad.listaManyToOne.add(relacionNueva);
            columna.esdeRelacion = true;
            columna.setLength(column.length());
            entidad.setColumns(columna);
        } else {

            if (vect[0].compareToIgnoreCase("true") != 0) {
                System.out.println("No existe la clase Relacionada " + values.getName());
                System.exit(0);
            }
        }

    }
    //Recibe una entidad, un "field" o campo y un data type
    //Se encarga de generar una instancia de relacion ManyToMany, la cual representa la tabla que se genera a partir de una
    //relacion ManyToMany
    public void MapeaManyToMany(Entidad entidad, Field values, String pkType) {

        ManytoManyClass relacionNueva = new ManytoManyClass();
        if (values.isAnnotationPresent(JoinTable.class)) {

            JoinTable jt = values.getAnnotation(JoinTable.class);

            String nombreTabla = jt.name();

            String llaveFuerte = jt.joinColumns()[0].name();

            String llaveDebil = jt.inverseJoinColumns()[0].name();


            relacionNueva.setTableName(nombreTabla);
            relacionNueva.setMypk(llaveFuerte);

            String vect[] = validations.validarRelacionMTM(values.getType().getSimpleName(), cls);

            if (vect[0].compareToIgnoreCase("true") == 0) {

                String targetEntity = values.getType().getSimpleName();

                relacionNueva.setTargetEntity(vect[1]);
                relacionNueva.setTargetEntityPK(vect[2]);
                relacionNueva.setTargetEntityPKType(vect[3]);
                relacionNueva.setMypkType(pkType);

                //System.out.println("ManyToMany ->" + "Llave fuerte: " + relacionNueva.getMypk() + " El nombre de la clase asociada es: " + relacionNueva.getTargetEntity() + " Llave debil: " + relacionNueva.getTargetEntityPK() + " El tipo de la llave debil es: " + relacionNueva.getTargetEntityPKType() + " La nueva tabla es: " + relacionNueva.getTableName());

                entidad.ListaManyToMany.add(relacionNueva);

            }

        } else {

            System.out.println("No hay joinTable");
        }
    }


}
