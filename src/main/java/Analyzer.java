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

public class Analyzer {
    //List<Entidad> ListaDeEntidades = new ArrayList<>();
    IR ir = new IR();
    Validations validations = new Validations();
    List<Class<?>> cls = new ArrayList<>();
    List<Entidad> ent = new ArrayList<Entidad>();

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

            // System.out.println("Nombre de Entidad: " + ir.ListaDeEntidades.get(i).getNombTable() + "\n" + ir.ListaDeEntidades.get(i).imprimecolumns() + "\n");

        }

    }

    public void validationsCicles() {
        validations.whenCheckCycles_thenDetectCycles(validations.getDirectedGraph(ir.ListaDeEntidades));

        validations.validadExistenciasPK(ir.ListaDeEntidades);
    }

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


    public void readMembers(Class<?> cl, Entidad entidad) {
        Field[] fields = cl.getDeclaredFields();
        Columna columna2;
        Columna columna;
        for (Field values : fields) {


            values.setAccessible(true);
            if (values.isAnnotationPresent(Id.class)) {
                columna2 = new Columna();
                columna2 = defirnirColumna(values, columna2);
                entidad.setPrimaryKey(columna2);


            }
            if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToOne.class) != true && values.isAnnotationPresent(OneToMany.class) != true && values.isAnnotationPresent(ManyToOne.class) != true && values.isAnnotationPresent(ManyToMany.class) != true && !values.isAnnotationPresent(Id.class)) {
                columna = new Columna();
                columna = defirnirColumna(values, columna);
                entidad.setColumns(columna);

            }
            if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToOne.class) == true && !values.isAnnotationPresent(Id.class)) {
                columna = new Columna();
                this.MapeaOneToOne(entidad, values, columna);
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
                this.MapeaManyToMany(entidad, values);
            }

        }
    }


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

    public void mapeaHerencia(List<Class<?>> cl, String nomEntidad, EntidadHerencia entidadesHerencia, Class<?> clasePadre) {
        List<Entidad> listtmp = new ArrayList<>();
        Class<?> clase = null;
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

                    clase = clase.getSuperclass();
                }
            }
            if (nomEntidad.compareToIgnoreCase(clase.getSimpleName()) != 0) {
                listtmp.clear();
            }
        }


        //  ir.entidadesconherencia.put(entidadesHerencia, listtmp);
        if (entidadesHerencia.getEstrategia().compareToIgnoreCase("SINGLE_TABLE") == 0) {
        }

        for (int i = 0; i < listtmp.size(); i++) {
            ent.add(listtmp.get(i));
        }


        InheritanceSingleTable(entidadesHerencia, listtmp, clasePadre);

    }

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


    public void MapeaOneToOne(Entidad entidad, Field values, Columna columna) {
        OneToOne oto = values.getAnnotation(OneToOne.class);
        OnetoOneClass relacionNueva = new OnetoOneClass();
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

            entidad.listaOneToOne.add(relacionNueva);
            columna.esdeRelacion = true;
            columna.setLength(column.length());
            entidad.setColumns(columna);

        } else {
            //System.out.println("No se encontro la clase relacionada.");
        }
    }

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
            //System.out.println("No se encontro la clase relacionada.");
        }
    }

    public void MapeaManyToOne(Entidad entidad, Field values, Columna columna) {

        OneToMany otm = values.getAnnotation(OneToMany.class);
        ManytoOneClass relacionNueva = new ManytoOneClass();

        if (values.isAnnotationPresent(JoinColumn.class) && values.getAnnotation(JoinColumn.class).name().compareToIgnoreCase("") != 0) {

            JoinColumn jc = values.getAnnotation(JoinColumn.class);
            //System.out.println("jc existe y nom diferente de vacio, El nombre de la columna es " + jc.name());
            relacionNueva.setNameJoinColumn(jc.name());

        } else {
            //System.out.println("No hay jc o no traia nombre, por lo que se toma el nombre de " + values.getName() + "_id");
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

            System.out.println("No existe la entidad relacionada.");
        }

    }

    public void MapeaManyToMany(Entidad entidad, Field values) {

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

                System.out.println("ManyToMany ->" + "Llave fuerte: " + relacionNueva.getMypk() + " El nombre de la clase asociada es: " + relacionNueva.getTargetEntity() + " Llave debil: " + relacionNueva.getTargetEntityPK() + " El tipo de la llave debil es: " + relacionNueva.getTargetEntityPKType() + " La nueva tabla es: " + relacionNueva.getTableName());

                entidad.ListaManyToMany.add(relacionNueva);

            } else {

                System.out.println("No existe la entidad relacionada.");
            }

        } else {

            System.out.println("No hay joinTable");
        }
    }


}
