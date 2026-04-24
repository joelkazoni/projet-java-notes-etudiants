import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe Main - Principe SRP : point d'entrée et menu interactif uniquement.
// Principe DIP : les implémentations concrètes sont instanciées ici une seule fois,
//                puis injectées via les interfaces partout ailleurs.
// Aucune création de fichier CSV ici : le programme LIT un fichier existant.

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // --- Injection de dépendances (DIP) ---
        // Chaque interface reçoit son implémentation concrète ici uniquement.
        ILecteur     lecteur     = new LecteurCSV();
        ICalculateur calculateur = new CalculateurMoyenne();
        IClasseur    classeur    = new ClasseurParMoyenne();
        ISauvegarde  sauvegarde  = new SauvegardeCSV();
        IDepot       depot       = new DepotEtudiants();
        IAfficheur   afficheur   = new AfficheurConsole();

        GestionEtudiants gestionnaire = new GestionEtudiants(
                lecteur, calculateur, classeur, sauvegarde, depot, afficheur);

        afficherBanniere();

        int choix;
        do {
            afficherMenu(gestionnaire.isModifie());
            System.out.print("Votre choix : ");
            choix = lireEntier(sc);
            sc.nextLine();
            System.out.println();

            switch (choix) {

                // --------------------------------------------------
                case 1: // Charger un fichier CSV existant
                // --------------------------------------------------
                    System.out.print("Chemin du fichier CSV (ex: etudiants.csv) : ");
                    String fichier = sc.nextLine().replace(" ", "");
                    gestionnaire.charger(fichier);
                    break;

                // --------------------------------------------------
                case 2: // Afficher le classement et les statistiques
                // --------------------------------------------------
                    gestionnaire.afficherClassement();
                    break;

                // --------------------------------------------------
                case 3: // Ajouter un étudiant
                // --------------------------------------------------
                    System.out.print("Nom        : ");
                    String nom = sc.nextLine().replace(" ", "");

                    System.out.print("Prénom     : ");
                    String prenom = sc.nextLine().replace(" ", "");

                    System.out.print("Matricule  : ");
                    String matricule = sc.nextLine().replace(" ", "");

                    final int NB_NOTES = 5;
                    List<Double> notes = new ArrayList<>();
                    System.out.println("Entrez les " + NB_NOTES + " notes (0 à 100) :");
                    while (notes.size() < NB_NOTES) {
                        System.out.print("  Note " + (notes.size() + 1) + "/" + NB_NOTES + " : ");
                        double note = lireDouble(sc);
                        sc.nextLine();
                        if (note < 0 || note > 100) {
                            System.out.println("  Note invalide. Entrez une valeur entre 0 et 100.");
                            continue;
                        }
                        notes.add(note);
                    }

                    Etudiant nouvel = new Etudiant(nom, prenom, matricule);
                    for (double n : notes) nouvel.ajouterNote(n);
                    gestionnaire.ajouterEtudiant(nouvel);
                    break;

                // --------------------------------------------------
                case 4: // Supprimer un étudiant
                // --------------------------------------------------
                    System.out.print("Matricule de l'étudiant à supprimer : ");
                    String matSuppr = sc.nextLine().replace(" ", "");
                    gestionnaire.supprimerEtudiant(matSuppr);
                    break;

                // --------------------------------------------------
                case 5: // Consulter et modifier les notes d'un étudiant
                // --------------------------------------------------
                    System.out.print("Matricule de l'étudiant : ");
                    String matNotes = sc.nextLine().replace(" ", "");
                    gestionnaire.afficherNotesEtudiant(matNotes);

                    System.out.println("\nQue voulez-vous faire ?");
                    System.out.println("  1. Modifier une note existante");
                    System.out.println("  2. Ajouter une nouvelle note");
                    System.out.println("  3. Retour au menu");
                    System.out.print("Choix : ");
                    int sousChoix = lireEntier(sc);
                    sc.nextLine();

                    if (sousChoix == 1) {
                        System.out.print("Numéro de la note à modifier (1, 2, 3...) : ");
                        int idx = lireEntier(sc);
                        sc.nextLine();
                        System.out.print("Nouvelle valeur (0 à 100) : ");
                        double nvVal = lireDouble(sc);
                        sc.nextLine();
                        gestionnaire.modifierNote(matNotes, idx, nvVal);

                    } else if (sousChoix == 2) {
                        System.out.print("Valeur de la nouvelle note (0 à 100) : ");
                        double nvNote = lireDouble(sc);
                        sc.nextLine();
                        gestionnaire.ajouterNote(matNotes, nvNote);
                    }
                    break;

                // --------------------------------------------------
                case 6: // Recalculer les moyennes et reclasser
                // --------------------------------------------------
                    gestionnaire.recalculer();
                    break;

                // --------------------------------------------------
                case 7: // Sauvegarder les résultats
                // --------------------------------------------------
                    System.out.print("Nom du fichier de sortie (ex: resultats.csv) : ");
                    String sortie = sc.nextLine().replace(" ", "");
                    gestionnaire.sauvegarder(sortie);
                    break;

                // --------------------------------------------------
                case 8: // Quitter
                // --------------------------------------------------
                    if (gestionnaire.isModifie()) {
                        System.out.println("/!\\ Des modifications non sauvegardées seront perdues !");
                        System.out.print("Quitter quand même ? (o/n) : ");
                        String rep = sc.nextLine().replace(" ", "");
                        if (!rep.equalsIgnoreCase("o")) {
                            choix = 0; // annule la sortie
                            break;
                        }
                    }
                    System.out.println("Fin du programme. Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide. Entrez un nombre entre 1 et 8.");
            }

        } while (choix != 8);

        sc.close();
    }

    // =========================================================
    //  AFFICHAGE DU MENU — SRP : uniquement la présentation
    // =========================================================

    private static void afficherBanniere() {
        System.out.println("=".repeat(55));
        System.out.println("      SYSTÈME DE GESTION DES ÉTUDIANTS");
        System.out.println("        Architecture SOLID - Java");
        System.out.println("=".repeat(55));
    }

    private static void afficherMenu(boolean modifie) {
        String etat = modifie ? "  [* modifications non sauvegardées]" : "";
        System.out.println("\n--- Menu Principal" + etat + " ---");
        System.out.println("  1. Charger un fichier CSV");
        System.out.println("  2. Afficher le classement et les statistiques");
        System.out.println("  3. Ajouter un étudiant");
        System.out.println("  4. Supprimer un étudiant");
        System.out.println("  5. Consulter / modifier les notes d'un étudiant");
        System.out.println("  6. Recalculer les moyennes et reclasser");
        System.out.println("  7. Sauvegarder les résultats dans un fichier CSV");
        System.out.println("  8. Quitter");
    }

    // =========================================================
    //  SAISIE SÉCURISÉE — évite les crash sur mauvaise entrée
    // =========================================================

    private static int lireEntier(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Entrée invalide. Entrez un nombre entier : ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double lireDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Entrée invalide. Entrez un nombre décimal : ");
            sc.next();
        }
        return sc.nextDouble();
    }
}
