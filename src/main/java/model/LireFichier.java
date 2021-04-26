package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LireFichier {
    private Graphe graphe;
    private Projet projet;
    private File fichier;

    public LireFichier(File fichier) throws FileNotFoundException {
        this.fichier = fichier;
        lireFichier(fichier);
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public Projet getProjet() {
        return projet;
    }

    private void lireFichier(File file) throws FileNotFoundException {
        HashMap<Tache, Integer> symbolTable = new HashMap<>(); // map une tache à un entier, pour le graphe
        Tache keys[]; // map un entier à une tache, pour le graphe
        int index = 0;

//        File file = new File(path);
        Scanner sc = new Scanner(file);

        HashMap<String, Tache> tasks = new HashMap<>(); // map un nom à la tache qui le correspond
        HashMap<String, String[]> predecessors = new HashMap<>(); // map un nom à une liste des noms des taches predecessurs.

        Tache t;
        String line[];
        while (sc.hasNextLine()) {
            line = sc.nextLine().split(" \\| "); // lire une ligne du fichier

            t = new Tache(line[0].trim(), Float.parseFloat(line[1])); // creer une nouvelle tache
            symbolTable.put(t, index);
            index++;

            tasks.put(t.getId(), t);
            predecessors.put(t.getId(), line[2].split(" "));
        }
        sc.close();

        // Remplir le tableau keys
        keys = new Tache[symbolTable.size()];
        for (Map.Entry<Tache, Integer> entry : symbolTable.entrySet()) {
            keys[entry.getValue()] = entry.getKey();
        }

        // Creer et remplir le graphe
        graphe = new Graphe(index, symbolTable, keys);
        projet = new Projet(graphe);
        graphe.setProjet(projet);
        for (Map.Entry<String, String[]> entry : predecessors.entrySet()) {
            for (String s : entry.getValue()) {
                s = s.trim();
                if (!s.equals("-")) {
                    t = tasks.get(s);
                    tasks.get(entry.getKey()).ajouterPredecesseur(tasks.get(s));
                    graphe.ajouterArc(t, tasks.get(entry.getKey()));
                }
            }
        }

    }
}
