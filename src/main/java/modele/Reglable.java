package modele;

public interface Reglable extends Appareil {
    int NIVEAU_MIN = 0;
    int NIVEAU_MAX = 100;

    void setNiveau(int niveau);

    int getNiveau();

    static boolean niveauValide(int niveau) {
        return niveau >= NIVEAU_MIN && niveau <= NIVEAU_MAX;
    }
}
