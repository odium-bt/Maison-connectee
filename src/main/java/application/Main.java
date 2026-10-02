package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import modele.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utils.Utils.log;

public class Main {
    static void main() throws InterruptedException, IOException {
        log.debug("Lancement de l'application");

        Scenario toutEteindre = new Scenario() {
            @Override
            public void executer(List<Appareil> appareils) {
                for (Appareil a : appareils) {
                    a.eteindre();
                }
            }
        };

        Scenario modeSoiree = new Scenario() {
            @Override
            public void executer(List<Appareil> appareils) {
                for (Appareil a : appareils) {
                    if (a instanceof Lampe) {
                        try {
                            a.allumer();
                            ((Lampe) a).setNiveau(60);
                        } catch (IllegalArgumentException e) {
                            log.error("Erreur de réglage de la lampe : {}", String.valueOf(e));
                        }
                    } else if (a instanceof Thermostat) {
                        try {
                            a.allumer();
                            ((Thermostat) a).setNiveau(20);
                        } catch (IllegalArgumentException e) {
                            log.error("Erreur de réglage du thermostat : {}", String.valueOf(e));
                        }
                    }
                }
            }
        };

        Scenario reveil = new Scenario() {
            @Override
            public void executer(List<Appareil> appareils) {
                for (Appareil a : appareils) {
                    if (a instanceof Lampe) {
                        a.allumer();
                        ((Lampe) a).setNiveau(100);
                    } else if (a instanceof MachineACafe) {
                        a.allumer();
                        ((MachineACafe) a).programmer(7);
                    }
                }
            }
        };

        Maison maison = new Maison();
        Lampe l = new Lampe("Lampe salon");
        maison.ajouter(l);
        maison.ajouter(new Lampe("Lampe salle de bain"));
        Thermostat t = new Thermostat("Thermostat");
        maison.ajouter(t);
        MachineACafe m = new MachineACafe("Machine à café");
        maison.ajouter(m);

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode lampe = mapper.createObjectNode();
        lampe.put("nom", l.getNom());
        lampe.put("niveau", l.getNiveau());
        ObjectNode thermostat = mapper.createObjectNode();
        thermostat.put("nom", t.getNom());
        thermostat.put("niveau", t.getNiveau());
        thermostat.put("allume", t.estAllume());
        lampe.set("thermostat", thermostat);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lampe);
        log.info(json);
        FileWriter fichier = new FileWriter("monfichier.json");
        fichier.write(json);
        fichier.close();

        ObjectNode racine = mapper.createObjectNode();
        racine.put("proprietaire", "zmouquet");
        racine.put("version", "1.0");
        ObjectNode home = mapper.createObjectNode();
        home.put("nom", "Maison");
        ArrayNode tabAppareils = home.putArray("appareils");
        for (Appareil a : maison.appareils) {
            tabAppareils.add(mapper.valueToTree(a));
        }
        racine.set("maison", home);
        mapper.writeValue(new File("maison.json"), racine);


        int c = 0;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("=== Smart house ===");
            System.out.println("Choisissez l'opération");
            System.out.println("1 : Tout éteindre");
            System.out.println("2 : Mode soirée");
            System.out.println("3 : Mode réveil");
            System.out.println("4 : Lister les appareils");
            System.out.println("0 : Quitter");
            System.out.print("Choix : ");

            boolean v = false;
            do {
                try {
                    c = scan.nextInt();
                    v = true;
                } catch (InputMismatchException e) {
                    System.out.println("Veuillez entrer un chiffre");
                    scan.nextLine();
                }
            } while (!v);

            scan.nextLine();

            switch (c) {
                case 1:
                    maison.lancer(toutEteindre);
                    maison.afficherEtat();
                    break;
                case 2:
                    maison.lancer(modeSoiree);
                    maison.afficherEtat();
                    break;
                case 3:
                    maison.lancer(reveil);
                    maison.afficherEtat();
                    break;
                case 4:
                    maison.afficherEtat();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    continue;
                default:
                    log.error("Choix invalide : {}", c);
                    throw new IllegalArgumentException();
            }
            System.out.println("Appuyez sur entrée pour continuer...");
            scan.nextLine();
        } while (c != 0);

    }
}

