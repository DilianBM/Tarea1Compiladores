import java.util.ArrayList;
import java.util.List;

public class PostgresSentences extends SentenceGenerator {

    //Recibe una lista de entidades y a partir de ella genera las sentencias de "CREATE TABLE"
    @Override
    public List<String> generateSentences(List<Entidad> entidades) {
        List<String> sentences = new ArrayList();
        String values;
        String values2 = "";

        for (int i = 0; i < entidades.size(); i++) {
            values = "CREATE TABLE IF NOT EXISTS " + entidades.get(i).getNombTable() + " ( " + "\n";
            if (entidades.get(i).getPrimaryKey() != null) {
                values += createSentencePK(entidades.get(i).getPrimaryKey());
            }
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
                        values2 += sentenciasRelaciones(entidades.get(i), entidades.get(i).getColumns().get(j));
                        values += values2;
                        values2 = "";
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

        for (int i = 0; i < entidades.size(); i++) {
            String scriptMTM = "";
            for (int j = 0; j < entidades.get(i).getListaManyToMany().size(); j++) {

                scriptMTM += "CREATE TABLE " + entidades.get(i).getListaManyToMany().get(j).getTableName() + " (\n";

                scriptMTM += entidades.get(i).getListaManyToMany().get(j).getTargetEntityPK();

                String tipopk1 = entidades.get(i).getListaManyToMany().get(j).getTargetEntityPKType();


                if (tipopk1.compareToIgnoreCase("String") == 0) {
                    scriptMTM += " VARCHAR (255) NOT NULL,\n";
                }

                if (tipopk1.compareToIgnoreCase("Boolean") == 0) {
                    scriptMTM += " Boolean NOT NULL,\n";
                }

                if (tipopk1.compareToIgnoreCase("Float") == 0) {
                    scriptMTM += " Float NOT NULL,\n";
                }

                if (tipopk1.compareToIgnoreCase("Double") == 0) {
                    scriptMTM += " Double NOT NULL,\n";
                }

                if (tipopk1.compareToIgnoreCase("Int") == 0) {
                    scriptMTM += " Int NOT NULL,\n";
                }

                scriptMTM += entidades.get(i).getListaManyToMany().get(j).getMypk();

                String tipopk2 = entidades.get(i).getListaManyToMany().get(j).getMypkType();

                if (tipopk2.compareToIgnoreCase("String") == 0) {
                    scriptMTM += " VARCHAR (255) NOT NULL, \n";
                }

                if (tipopk2.compareToIgnoreCase("Boolean") == 0) {
                    scriptMTM += " Boolean NOT NULL, \n";
                }

                if (tipopk2.compareToIgnoreCase("Float") == 0) {
                    scriptMTM += " Float NOT NULL, \n";
                }

                if (tipopk2.compareToIgnoreCase("Double") == 0) {
                    scriptMTM += " Double NOT NULL, \n";
                }

                if (tipopk2.compareToIgnoreCase("Int") == 0) {
                    scriptMTM += " Int NOT NULL, \n";
                }

                scriptMTM += "PRIMARY KEY (" + entidades.get(i).getListaManyToMany().get(j).getTargetEntityPK() + "," + entidades.get(i).getListaManyToMany().get(j).getMypk() + "), \n";

                scriptMTM += "FOREIGN KEY (" + entidades.get(i).getListaManyToMany().get(j).getTargetEntityPK() + ") REFERENCES " + entidades.get(i).getListaManyToMany().get(j).getTargetEntity() + "(" + entidades.get(i).getListaManyToMany().get(j).getTargetEntityPK() + "),\n";

                scriptMTM += "FOREIGN KEY (" + entidades.get(i).getListaManyToMany().get(j).getMypk() + ") REFERENCES " + entidades.get(i).getNombTable() + "(" + entidades.get(i).getListaManyToMany().get(j).getMypk() + ")\n);";

                sentences.add(scriptMTM);

            }
        }

        return sentences;
    }

    //Genera la sentencia correspondiente a la columna de la llave primaria
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
        if (columna.getNombreTipo().compareToIgnoreCase("BOOLEAN") == 0) {
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

    //Genera la sentencias correspondientes a las columnas que representan llaves foraneas
    public String sentenciasRelaciones(Entidad entidad, Columna columna) {
        String tmp = columna.Name;
        String values = "";

        for (int k = 0; k < entidad.listaOneToOne.size(); k++) {
            if (entidad.listaOneToOne.get(k).getNameJoinColumn().compareToIgnoreCase(tmp) == 0) {
                if (columna.getNombreTipo().compareToIgnoreCase("varchar ()") == 0) {
                    columna.setNombreTipo(" VARCHAR (" + columna.getLength() + ") ");



                }

                values = values + " " + entidad.listaOneToOne.get(k).getNameJoinColumn() + " " + columna.getNombreTipo() + " , " + " FOREIGN KEY (" + entidad.getLista1().get(k).getNameJoinColumn() + ") " + "REFERENCES " + entidad.getLista1().get(k).getTargetEntity() + "(" + entidad.getLista1().get(k).getPk() + ") ";

            }


        }

        for (int k = 0; k < entidad.getListaOneToMany().size(); k++) {
            if (entidad.getListaOneToMany().get(k).getNameJoinColumn().compareToIgnoreCase(tmp) == 0) {
                if (columna.getNombreTipo().compareToIgnoreCase("varchar ()") == 0) {

                    columna.setNombreTipo(" VARCHAR (" + columna.getLength() + ") ");

                }
                values = values + " " + entidad.getListaOneToMany().get(k).getNameJoinColumn() + " " + columna.getNombreTipo() + " , " + " FOREIGN KEY (" + entidad.getListaOneToMany().get(k).getNameJoinColumn() + ") REFERENCES " + entidad.getListaOneToMany().get(k).getTargetEntity() + "(" + entidad.getListaOneToMany().get(k).getPk() + ") ";

            }


        }
        for (int k = 0; k < entidad.listaManyToOne.size(); k++) {
            if (entidad.getListaManyToOne().get(k).getNameJoinColumn().compareToIgnoreCase(tmp) == 0) {

                if (columna.getNombreTipo().compareToIgnoreCase("varchar ()") == 0) {

                    columna.setNombreTipo(" VARCHAR (" + columna.getLength() + ")");

                }
                values = values + " " + entidad.getListaManyToOne().get(k).getNameJoinColumn() + " " + columna.getNombreTipo() + " , " + " FOREIGN KEY (" + entidad.getListaManyToOne().get(k).getNameJoinColumn() + ") REFERENCES " + entidad.getListaManyToOne().get(k).getTargetEntity() + "(" + entidad.getListaManyToOne().get(k).getPk() + ") ";

            }


        }

        return values;
    }
}
