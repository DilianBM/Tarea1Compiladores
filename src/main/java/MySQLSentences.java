import jdk.dynalink.linker.support.CompositeTypeBasedGuardingDynamicLinker;

import java.util.ArrayList;
import java.util.List;

public class MySQLSentences extends SentenceGenerator {

    @Override
    public List<String> generateSentences(List<Entidad> entidades) {
        List<String> sentences = new ArrayList();
        String values;

        for (int i = 0; i < entidades.size(); i++) {
            values = "CREATE TABLE " + entidades.get(i).getNombTable() + " ( " + "\n";
            values += createSentencePK(entidades.get(i).getPrimaryKey());
            if (entidades.get(i).getColumns().size() > 0) {
                values += ",\n";
                for (int j = 0; j < entidades.get(i).getColumns().size(); j++) {
                    if (entidades.get(i).getColumns().get(j).esdeRelacion == false) {
                        values += entidades.get(i).getColumns().get(j).Name;
                        if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("VARCHAR ()") == 0) {
                            if (entidades.get(i).getColumns().get(j).getLob()) {
                                values += " TEXT ";
                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";

                                }
                            } else {
                                values += " VARCHAR (" + entidades.get(i).getColumns().get(j).getLength() + ") ";

                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";

                                }

                            }
                        }

                        if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("INT") == 0) {
                            if (entidades.get(i).getColumns().get(j).getLob()) {
                                values += " BLOB ";
                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";
                                }
                            } else {
                                values += " INT ";
                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";

                                }

                            }
                        }
                        if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("DOUBLE") == 0) {
                            if (entidades.get(i).getColumns().get(j).getLob()) {
                                values += " BLOB ";
                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";
                                }
                            } else {
                                values += " DOUBLE " + " (" + entidades.get(i).getColumns().get(j).getPrecision() + "," + entidades.get(i).getColumns().get(j).getScale() + ")";
                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";

                                }

                            }
                        }
                        if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("FLOAT") == 0) {
                            if (entidades.get(i).getColumns().get(j).getLob()) {
                                values += " BLOB ";
                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";
                                }
                            } else {
                                values += " FLOAT " + " (" + entidades.get(i).getColumns().get(j).getPrecision() + "," + entidades.get(i).getColumns().get(j).getScale() + ")";
                                if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                    values += " NOT NULL ";

                                }

                            }
                        }
                        if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("BOOLEAN") == 0) {
                            values += " BOOLEAN ";
                            if (!entidades.get(i).getColumns().get(j).getNullable()) {
                                values += " NOT NULL ";

                            }
                        }
                    } else {

                        String tmp = entidades.get(i).getColumns().get(j).Name;
                        for (int k = 0; k < entidades.get(i).getLista1().size(); k++) {
                            if (entidades.get(i).getLista1().get(k).getNameJoinColumn().compareToIgnoreCase(tmp) == 0) {
                                if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("varchar ()") == 0) {


                                    entidades.get(i).getColumns().get(j).setNombreTipo(" VARCHAR (" + entidades.get(i).getColumns().get(j).getLength() + ") ");

                                }
                                values += " " + entidades.get(i).getLista1().get(k).getNameJoinColumn() + " " + entidades.get(i).getColumns().get(j).getNombreTipo() + " FOREING KEY REFERENCES " + entidades.get(i).getLista1().get(k).getTargetEntity() + "(" + entidades.get(i).getLista1().get(k).getPk() + ")";
                            }


                        }
                        for (int k = 0; k < entidades.get(i).getListaOneToMany().size(); k++) {
                            if (entidades.get(i).getListaOneToMany().get(k).getNameJoinColumn().compareToIgnoreCase(tmp) == 0) {
                                if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("varchar ()") == 0) {

                                    entidades.get(i).getColumns().get(j).setNombreTipo(" VARCHAR (" + entidades.get(i).getColumns().get(j).getLength() + ") ");

                                }
                                values += " " + entidades.get(i).getListaOneToMany().get(k).getNameJoinColumn() + " " + entidades.get(i).getColumns().get(j).getNombreTipo() + " FOREING KEY REFERENCES " + entidades.get(i).getListaOneToMany().get(k).getTargetEntity() + "(" + entidades.get(i).getListaOneToMany().get(k).getPk() + ")";
                            }


                        }
                        for (int k = 0; k < entidades.get(i).listaManyToOne.size(); k++) {
                            if (entidades.get(i).getListaManyToOne().get(k).getNameJoinColumn().compareToIgnoreCase(tmp) == 0) {
                                if (entidades.get(i).getColumns().get(j).getNombreTipo().compareToIgnoreCase("varchar ()") == 0) {

                                    entidades.get(i).getColumns().get(j).setNombreTipo(" VARCHAR (" + entidades.get(i).getColumns().get(j).getLength() + ") ");

                                }
                                values += " " + entidades.get(i).getListaManyToOne().get(k).getNameJoinColumn() + " " + entidades.get(i).getColumns().get(j).getNombreTipo() + " FOREING KEY REFERENCES " + entidades.get(i).getListaManyToOne().get(k).getTargetEntity() + "(" + entidades.get(i).getListaManyToOne().get(k).getPk() + ")";
                            }


                        }

                    }

                    if (j + 1 < entidades.get(i).getColumns().size()) {
                        values += ",";
                    }

                    values += "\n";

                }
            }
            values += ");";
            sentences.add(values);
        }

        for (int i = 0; i < entidades.size(); i++){
            String scriptMTM = "";
            for(int j = 0; j< entidades.get(i).getListaManyToMany().size(); j++){

                scriptMTM += "CREATE TABLE " + entidades.get(i).getListaManyToMany().get(j).getTableName() + " (";

                System.out.println("Relacion mtm: " + scriptMTM);

            }


        }
        return sentences;
    }

    public String createSentencePK(Columna columna) {
        String values = "";
        values += columna.Name;

        if (columna.getNombreTipo().compareToIgnoreCase("VARCHAR ()") == 0) {
            if (columna.getLob()) {
                values += " TEXT ";
                if (!columna.getNullable()) {
                    values += " NOT NULL ";

                }
            } else {
                values += " VARCHAR (" + columna.getLength() + ") ";

                if (!columna.getNullable()) {
                    values += " NOT NULL ";

                }

            }
        }

        if (columna.getNombreTipo().compareToIgnoreCase("INT") == 0) {
            if (columna.getLob()) {
                values += " BLOB ";
                if (!columna.getNullable()) {
                    values += " NOT NULL ";
                }
            } else {
                values += " INT ";
                if (!columna.getNullable()) {
                    values += " NOT NULL ";

                }

            }
        }
        if (columna.getNombreTipo().compareToIgnoreCase("DOUBLE") == 0) {
            if (columna.getLob()) {
                values += " BLOB ";
                if (!columna.getNullable()) {
                    values += " NOT NULL ";
                }
            } else {
                values += " DOUBLE " + " (" + columna.getPrecision() + "," + columna.getScale() + ")";
                if (!columna.getNullable()) {
                    values += " NOT NULL ";

                }

            }
        }
        if (columna.getNombreTipo().compareToIgnoreCase("FLOAT") == 0) {
            if (columna.getLob()) {
                values += " BLOB ";
                if (!columna.getNullable()) {
                    values += " NOT NULL ";
                }
            } else {
                values += " FLOAT " + " (" + columna.getPrecision() + "," + columna.getScale() + ")";
                if (!columna.getNullable()) {
                    values += " NOT NULL ";

                }

            }
        }
        if (columna.getNombreTipo().compareToIgnoreCase("BOOLEAM") == 0) {
            values += " BOOLEAN ";
            if (!columna.getNullable()) {
                values += " NOT NULL ";

            }
        }
        return values + " PRIMARY KEY";
    }


    public void imprimeScript(List<String> sentences) {

        for (int i = 0; i < sentences.size(); i++) {
            System.out.println(sentences.get(i) + "\n");
        }

    }
}
