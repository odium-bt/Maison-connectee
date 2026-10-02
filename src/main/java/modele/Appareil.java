package modele;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes({
        @JsonSubTypes.Type(value = Lampe.class, name = "lampe"),
        @JsonSubTypes.Type(value = Thermostat.class, name = "thermostat"),
        @JsonSubTypes.Type(value = MachineACafe.class, name = "cafe"),
})
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
