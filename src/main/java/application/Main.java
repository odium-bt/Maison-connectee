package application;

import modele.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utils.Utils.log;

public class Main {
    static void main() throws InterruptedException {
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
        maison.ajouter(new Lampe("Lampe salon"));
        maison.ajouter(new Lampe("Lampe salle de bain"));
        maison.ajouter(new Thermostat("Thermostat"));
        maison.ajouter(new MachineACafe("Machine à café"));

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

