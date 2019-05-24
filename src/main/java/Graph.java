// Java program to print DFS traversal from a given given graph 

import java.io.*;
import java.util.*;

// This class represents a directed graph using adjacency list 
// representation 
class Grafo {
    ListaAdyacentes l;
    List<ListaAdyacentes> lista = new ArrayList<>();

    class ListaAdyacentes {
        String node;
        boolean visitado = false;
        List<ListaAdyacentes> listaAdyacentes = new ArrayList<>();

    }


    public void agreguevert(String v) {

        boolean existe = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).node.compareToIgnoreCase(v) == 0) {
                existe = true;
            }
        }
        if (!existe) {
            ListaAdyacentes nodo = new ListaAdyacentes();
            nodo.node = v;
            lista.add(nodo);
        }

    }

    private int V;   // No. of vertices

    // Array  of lists for Adjacency List Representation
    private LinkedList<Integer> adj[];

    // Constructor
    Grafo(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    //Function to add an edge into the graph
    void addEdge(String v, String w) {
        boolean existe = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).node.compareToIgnoreCase(v) == 0) {
                existe = true;
            }
        }
        if (!existe) {
            ListaAdyacentes nodo = new ListaAdyacentes();
            nodo.node = v;
            lista.add(nodo);
        }
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).node.compareToIgnoreCase(v) == 0) {
                ListaAdyacentes nodo = new ListaAdyacentes();
                nodo.node = w;
                lista.get(i).listaAdyacentes.add(nodo);
            }

        }
        // adj[v].add(w);

        // Add w to v's list.
    }

    // A function used by DFS
    List<String> DFSUtil(ListaAdyacentes lista, int v) {
        // Mark the current node as visited and print it
        //  visited[v] = true;
        List<String> list = new ArrayList<>();
        //String value = "";
        lista.visitado = true;
        //  if (lista.listaAdyacentes.size() == 0) {
        //value += lista.node;
        list.add(lista.node);



        // }


        /*
        grafo.addEdge("A", "B");
        grafo.addEdge("B", "C");
        grafo.addEdge("C", "D");
        grafo.addEdge("D", "F");*/

        // Recur for all the vertices adjacent to this vertex
        for (int i = 0; i < lista.listaAdyacentes.size(); i++) {
            if (!lista.listaAdyacentes.get(i).visitado) {
                //    System.out.println(lista.listaAdyacentes.get(i).node);
                DFSUtil(lista.listaAdyacentes.get(i), i);
            }
        }
          /*  Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                    DFSUtil(n,visited);
            }*/
        return list;
    }

    // The function to do DFS traversal. It uses recursive DFSUtil()
    List<String> DFS() {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        List<String> list = new ArrayList<>();
        boolean visited[] = new boolean[V];

        // Call the recursive helper function to print DFS traversal
        // starting from all vertices one by one
        String values = "";
        List<String> lTemp=new ArrayList<>();
        for (int i = 0; i < lista.size(); ++i){
            if (!lista.get(i).visitado) {

               lTemp= DFSUtil(lista.get(i), i);

            }
            for (int k = 0; k < lTemp.size(); ++k){

                list.add(lTemp.get(k));
            }

        }
        return list;
    }


    public void pruebagrafo(List<Entidad> listaDeEntidades) {

        for (int i = 0; i < listaDeEntidades.size(); i++) {

            agreguevert(listaDeEntidades.get(i).nombTable);
            for (int j = 0; j < listaDeEntidades.get(i).listaOneToOne.size(); j++) {
                addEdge(listaDeEntidades.get(i).getNombTable(), listaDeEntidades.get(i).listaOneToOne.get(j).getTargetEntity());
            }
            for (int j = 0; j < listaDeEntidades.get(i).getListaOneToMany().size(); j++) {

                addEdge(listaDeEntidades.get(i).getNombTable(), listaDeEntidades.get(i).getListaOneToMany().get(j).getTargetEntity());
            }
            for (int j = 0; j < listaDeEntidades.get(i).listaManyToOne.size(); j++) {
                addEdge(listaDeEntidades.get(i).getNombTable(), listaDeEntidades.get(i).getListaManyToOne().get(j).getTargetEntity());

            }
        }

    }

    public List<Entidad> ordenDeEjecucionTablas(List<Entidad> listaDeEntidades, List<String> listEnt) {
        List<Entidad> lista=new ArrayList<>();
        for (int j = 0; j < listEnt.size(); j++) {
            for (int i = 0; i < listaDeEntidades.size(); i++) {
                if (listEnt.get(j).compareToIgnoreCase(listaDeEntidades.get(i).getNombTable())==0){
                    lista.add(listaDeEntidades.get(i));

                }

            }

        }

        return  lista;
    }

}