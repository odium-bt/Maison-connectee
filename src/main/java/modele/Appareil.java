package modele;

public interface Appareil {
    String getNom();

    boolean estAllume();

    void allumer();

    void eteindre();

    default void basculer() {
        if (estAllume()) {
            eteindre();
        } else {
            allumer();
        }
    }
}
