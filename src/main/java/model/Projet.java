package model;

public class Projet {
    private float dureeProjet;
    private Graphe graphe;

    public Projet(Graphe graphe) {
        this.graphe = graphe;
    }

    public float getDureeProjet() {
        return dureeProjet;
    }

    public void setDureeProjet(float dureeProjet) {
        this.dureeProjet = dureeProjet;
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public void setGraphe(Graphe graphe) {
        this.graphe = graphe;
    }
}
