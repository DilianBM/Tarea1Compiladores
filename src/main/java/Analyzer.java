import ClasesPrueba.C;
import Relaciones.OnetoOneClass;
import Relaciones.Relaciones;
import Relaciones.OnetoManyClass;
import Relaciones.ManytoOneClass;

import javax.persistence.ForeignKey;
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

    public void procesaEntidades(List<Class<?>> cl) {
        cls = cl;
        for (int i = 0; i < cl.size(); i++) {
            Entidad entidad = new Entidad();
            ProcessClassAnnotations(cl.get(i), entidad);
            // ListaDeEntidades.add(entidad);
            ir.ListaDeEntidades.add(entidad);

            validations.getDirectedGraph(ir.ListaDeEntidades);
            System.out.println("Nombre de Entidad: " + entidad.getNombTable() + "\n" + entidad.imprimecolumns() + "\n");
        }

        validations.validadExistenciasPK(ir.ListaDeEntidades);
    }

    public void ProcessClassAnnotations(Class<?> cl, Entidad entidad) {
        Class<?> clase = null;
        Annotation[] anotations = cl.getAnnotations();
        if (anotations.length != 0) {
            if (cl.isAnnotationPresent(Table.class)) {
                Table tabla = cl.getAnnotation(Table.class);
                entidad.setNombTable(tabla.name());
                if(!cl.isAnnotationPresent(Inheritance.class)){
                readMembers(cl, entidad);}
            } else {
                entidad.setNombTable(cl.getSimpleName());
                if(!cl.isAnnotationPresent(Inheritance.class)){
                    readMembers(cl, entidad);}
            }
            if (cl.getSuperclass().isAnnotationPresent(MappedSuperclass.class)) {
                clase = cl.getSuperclass();
                MapeaSuperClass(clase, entidad);
            }
            if (cl.isAnnotationPresent(Inheritance.class)) {

                Inheritance inheritance = cl.getAnnotation(Inheritance.class);
                EntidadHerencia entidadHerencia = new EntidadHerencia();
                entidadHerencia.entidad = new Entidad();
                if (cl.isAnnotationPresent(Table.class)) {
                    Table tabla = clase.getAnnotation(Table.class);
                    entidadHerencia.entidad.setNombTable(tabla.name());
                } else {
                    entidadHerencia.entidad.setNombTable(cl.getSimpleName());

                }
                entidadHerencia.setEstrategia(inheritance.strategy().name());
                readMembers(cl, entidadHerencia.entidad);

                if (cl.isAnnotationPresent(DiscriminatorColumn.class)) {
                    DiscriminatorColumn discriminatorColumn = cl.getAnnotation(DiscriminatorColumn.class);
                    entidadHerencia.setDiscriminatorColumn(discriminatorColumn.name());
                }
                entidadHerencia.setEntidad(entidadHerencia.entidad);
                ir.ListaDeEntidadesInheritance.add(entidadHerencia);

                mapeaHerencia(cls, cl.getSimpleName(), entidadHerencia);

            }
        } else {
            out.println("Annotations are not present...");
        }
    }


    public void readMembers(Class<?> cl, Entidad entidad) {
        Field[] fields = cl.getDeclaredFields();
        for (Field values : fields) {
            Annotation[] anot = values.getAnnotations();
            if (anot.length != 0) {
                Columna columna = new Columna();
                values.setAccessible(true);
                if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToOne.class) != true && values.isAnnotationPresent(OneToMany.class) != true && values.isAnnotationPresent(ManyToOne.class) != true && values.isAnnotationPresent(ManyToMany.class) != true) {

                    columna = defirnirColumna(values, columna);
                    entidad.setColumns(columna);
                    if (values.isAnnotationPresent(Id.class)) {
                        entidad.setPrimaryKey(columna);
                    }
                }
                if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToOne.class) == true) {
                    this.MapeaOneToOne(entidad, values, columna);
                }

                if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(OneToMany.class) == true) {
                    Columna columna1 = new Columna();
                    this.MapeaOneToMany(entidad, values, columna1);
                }

                if (values.isAnnotationPresent(Column.class) && values.isAnnotationPresent(ManyToOne.class) == true) {
                    Columna columna1 = new Columna();
                    this.MapeaManyToOne(entidad, values, columna1);
                }
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

    public void mapeaHerencia(List<Class<?>> cl, String nomEntidad, EntidadHerencia entidadesHerencia) {
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
        System.out.println("Padre " + nomEntidad);
        for (int i = 0; i < listtmp.size(); i++) {
            System.out.println("herencia " + listtmp.get(i).getNombTable());
        }

        ir.entidadesconherencia.put(entidadesHerencia, listtmp);
    }

    public void MapeaOneToOne(Entidad entidad, Field values, Columna columna) {
        OneToOne oto = values.getAnnotation(OneToOne.class);
        OnetoOneClass relacionNueva = new OnetoOneClass();
        boolean esdueña = false;

        if (values.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn jc = values.getAnnotation(JoinColumn.class);
            relacionNueva.setNameJoinColumn(jc.name());
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
            entidad.setColumns(columna);
        } else {

            System.out.println("No existe la entidad relacionada.");
        }
    }
}
