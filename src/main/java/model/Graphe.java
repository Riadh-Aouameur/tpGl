package model;

import java.util.*;

public class Graphe {
    private HashMap<Tache, Integer> symbolTable; // Task -> index
    private Tache keys[];                        // index -> Task;

    private final int V;                // nombre des noeuds dans le graphe
    private int E;                      // nombre des arcs dans le graphe
    private LinkedList<Tache> adj[];     // adj[v] = liste d'adjacence du noeud v
    private int[] indegree;             // indegree[v] = indegree odu noeud v

    private Projet projet;

    private LinkedList<Tache> niveau[];

    public Graphe(int v, HashMap<Tache, Integer> symbolTable, Tache[] keys) {
        this.symbolTable = symbolTable;
        this.keys = keys;

        this.V = v;

        this.indegree = new int[V];

        this.adj = new LinkedList[V];
        for (int i = 0; i < V; i++)
            this.adj[i] = new LinkedList<>();

        niveau = new LinkedList[V];
        for (int i = 0; i < V; i++)
            this.niveau[i] = new LinkedList<>();
    }

    public int getNiveau(Tache t) {
        for(int i=0;i<V;i++){
            if(niveau[i].contains(t))
                return i;
        }
        return -1;

    }

    public LinkedList<Tache>[] getN(){
        return niveau;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }


    public Tache[] getKeys() {
        return keys;
    }

    public int V() {
        return V;
    }


    public void ajouterArc(Tache t1, Tache t2) {
        int v = symbolTable.get(t1);
        int w = symbolTable.get(t2);

        adj[v].add(t2);
        indegree[w]++;
        E++;
    }


    public LinkedList<Tache> adj(int v) {
        return adj[v];
    }

    // Tri topologique des taches
    // L'algorithme de "Kahn"
    public LinkedList<Integer> topologicalSort() {
        int ins[] = new int[V()];
        System.arraycopy(indegree, 0, ins, 0, indegree.length);
        // Creer une file avec tous les noeuds qu'ont indegree 0
        Queue<Integer> q = new LinkedList<Integer>();

        int niv = 0;
        for (int i = 0; i < V; i++) {
            if (ins[i] == 0){
                q.add(i);
                niveau[0].add(keys[i]);
            }

        }

        // Initialiser le compteur des noeuds visites
        int cnt = 0;

        // Creer une liste pour stocker les resultats
        // un ordre topologique des noeuds
        LinkedList<Integer> topOrder = new LinkedList<Integer>();
        int sizePerN = q.size();
        int i = 0;
        niv++;
        while (!q.isEmpty()) {


            // Extraire le debut de la file
            // et l'ajouter dans la liste de l'ordre topologique
            int u = q.poll();




            topOrder.add(u);

            // decrementer (par 1) le indegree de tous les noeuds adjacents
            // avec le noeuds que nous avons retirer
            for (Tache t : adj[u]) {
                // Si le indegree devient 0, ajouter le le noeud t dans la file
                if (--ins[symbolTable.get(t)] == 0){
                    q.add(symbolTable.get(t));
                    niveau[niv].add(t);
                }

            }

            i++;
            if(i == sizePerN){
                i = 0;
                sizePerN = q.size();
                niv++;
            }

            cnt++;
        }

        // Assurer qu'il n'y a pas un cycle
        if (cnt != V) {
            System.out.println("Il existe un cycle dans le graphe");
            return null;
        }

        return topOrder;
    }

    // Pour calculer les DTO et les FTO
    public void forwardpass(LinkedList<Integer> topOrder) {
        ArrayList<Tache> predessecors;
        Tache t;

        for (int i : topOrder) {
            t = keys[i];
            predessecors = t.getPredecessurs();
            if (predessecors.isEmpty()) {
                t.setFto(t.getDuree());
            } else {
                float dto = predessecors.stream().map(Tache::getFto).max(Comparator.naturalOrder()).get();
                t.setDto(dto);
                t.setFto(dto + t.getDuree());
            }
        }

        float dureeProjet = Arrays.stream(keys).map(Tache::getFto).max(Comparator.naturalOrder()).get();
        projet.setDureeProjet(dureeProjet);
    }

    // Pour Calculer les DTA et les FTA
    public void backwardPass(LinkedList<Integer> topOrder) {
        Collections.reverse(topOrder);

        LinkedList<Tache> successors;
        Tache t;

        for (int i : topOrder) {
            t = keys[i];
            successors = adj[i];

            if (successors.isEmpty()) {
                float dureeProjet = projet.getDureeProjet();
                t.setFta(dureeProjet);
                t.setDta(dureeProjet - t.getDuree());
                t.setMl(0);
            } else {
                float fta = successors.stream().map(Tache::getDta).min(Comparator.naturalOrder()).get();
                t.setFta(fta);
                t.setDta(fta - t.getDuree());

                float minDto = successors.stream().map(Tache::getDto).min(Comparator.naturalOrder()).get();
                t.setMl(minDto - t.getFto());
            }
        }
    }

    public Stack<Tache> criticalPath(LinkedList<Integer> topOrder) {
        Stack<Tache> criticalPath = new Stack<>();


        for (int i : topOrder) {
            Tache t = keys[i];
            if (t.getMt() == 0)
                criticalPath.push(t);
        }
        return criticalPath;

    }

}
