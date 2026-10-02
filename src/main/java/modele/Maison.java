package modele;

import java.util.ArrayList;
import java.util.List;

public class Maison {
    private final List<Appareil> appareils = new ArrayList<>();

    public void ajouter(Appareil a) {
        appareils.add(a);
    }

    public void lancer(Scenario s) {
        s.executer(appareils);
    }

    long nombreAllumes() {
        int c = 0;
        for (Appareil a : appareils) {
            if (a.estAllume()) {
                c++;
            }
        }
        return c;
    }

    public void afficherEtat() {
        for (Appareil a : appareils) {
            System.out.println(a.toString());
        }
    }
}
