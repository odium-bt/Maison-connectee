package modele;

import static utils.Utils.log;

public class MachineACafe implements Programmable {
    String nom;
    int heure;
    boolean allume;

    public MachineACafe(String nom) {
        this.nom = nom;
    }

    @Override
    public void programmer(int heure) {
        if (heure >= 0 && heure <= 24) {
            this.heure = heure;
        } else {
            log.error("Heure invalide : {}, doit être entre 0 et 24", heure);
            throw new IllegalArgumentException();
        }
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

        return nom + " [" + statut + ", café prêt à " + heure + "h]";
    }
}
