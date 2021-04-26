package model;

import java.util.ArrayList;

public class Tache {
    private String id;
    private float duree;
    private float dto;
    private float dta;
    private float fto;
    private float fta;
    private float ml;

    private ArrayList<Tache> predecessurs;

    public Tache(String id, float duree) {
        this.id = id;
        this.duree = duree;

        predecessurs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getDuree() {
        return duree;
    }

    public void setDuree(float duree) {
        this.duree = duree;
    }

    public float getDto() {
        return dto;
    }

    public void setDto(float dto) {
        this.dto = dto;
    }

    public float getDta() {
        return dta;
    }

    public void setDta(float dta) {
        this.dta = dta;
    }

    public float getFto() {
        return fto;
    }

    public void setFto(float fto) {
        this.fto = fto;
    }

    public float getFta() {
        return fta;
    }

    public void setFta(float fta) {
        this.fta = fta;
    }

    public float getMl() {
        return ml;
    }

    public void setMl(float ml) {
        this.ml = ml;
    }

    public float getMt() {
        return fta - fto;
    }


    public ArrayList<Tache> getPredecessurs() {
        return predecessurs;
    }


    public void ajouterPredecesseur(Tache t) {
        this.predecessurs.add(t);
    }

    public ArrayList<Tache> getPreds() {
        ArrayList<Tache> res = new ArrayList<>(predecessurs);
        for (Tache pred : predecessurs) {
            for (Tache p : pred.getPredecessurs())
                res.remove(p);

        }
        return res;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getDuree() + "\n";
    }
}
