package modele;

import java.util.List;

@FunctionalInterface
public interface Scenario {
    void executer(List<Appareil> appareils);
}
