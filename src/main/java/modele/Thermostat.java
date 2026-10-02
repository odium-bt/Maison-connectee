package modele;

import static utils.Utils.log;

public class Thermostat implements Programmable, Reglable {
    String nom;
    boolean allume;
    int NIVEAU_MIN = 5;
    int NIVEAU_MAX = 30;
    int niveau = 0;
    int heure;

    public Thermostat(String nom) {
        this.nom = nom;
    }

    @Override
    public void programmer(int heure) {
        this.heure = heure;
    }

    @Override
    public void setNiveau(int niveau) {
        if (Reglable.niveauValide(niveau)) {
            this.niveau = niveau;
        } else {
            log.error("Niveau invalide : {}, doit être entre {} et {}", niveau, NIVEAU_MIN, NIVEAU_MAX);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getNiveau() {
        return niveau;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public boolean estAllume() {
        return allume;
    }

    @Override
    public void allumer() {
        allume = true;
    }

    @Override
    public void eteindre() {
        allume = false;
    }

    @Override
    public String toString() {
        String statut;
        if (allume) {
            statut = "allumée";
        } else {
            statut = "éteinte";
        }

        return nom + " [" + statut + ", " + niveau + "° C, s'éteint à " + heure + "]";
    }
}
