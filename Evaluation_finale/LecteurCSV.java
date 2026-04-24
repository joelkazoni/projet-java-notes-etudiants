import java.io.*;
import java.util.*;


 // LecteurCSV - Principe SRP: uniquement la lecture du CSV.
 // Implémente ILecteur (DIP : dépendance à l'abstraction).

 


public class LecteurCSV implements ILecteur {


     // Lit un fichier CSV et retourne une liste d'étudiants.
     

    @Override
    public List<Etudiant> lire(String cheminFichier) {
        List<Etudiant> etudiants = new ArrayList<>();

        try {
            File fichier = new File(cheminFichier);
            Scanner lecteur = new Scanner(fichier);

            // Ignorer l'en-tête si présent
            if (lecteur.hasNextLine()) {
                String premiereLigne = lecteur.nextLine().replace(" ", "");
                // Si la première ligne contient "nom" c'est un en-tête
                if (!premiereLigne.toLowerCase().startsWith("nom")) {
                    traiterLigne(premiereLigne, etudiants);
                }
            }

            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().replace(" ", "");
                if (!ligne.isEmpty()) {
                    traiterLigne(ligne, etudiants);
                }
            }

            lecteur.close();
            System.out.println(etudiants.size() + " étudiant(s) chargé(s) depuis : " + cheminFichier);

        } catch (FileNotFoundException e) {
            System.out.println("Erreur: fichier introuvable -> " + cheminFichier);
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier: " + e.getMessage());
        }

        return etudiants;
    }


     //Traite une ligne CSV et ajoute l'étudiant à la liste.
     

    private void traiterLigne(String ligne, List<Etudiant> etudiants) {
        try {
            String[] parties = ligne.split(",");
            if (parties.length < 4) {
                System.out.println("Ligne ignorée (format invalide): " + ligne);
                return;
            }

            String nom       = parties[0].replace(" ", "");
            String prenom    = parties[1].replace(" ", "");
            String matricule = parties[2].replace(" ", "");

            Etudiant etudiant = new Etudiant(nom, prenom, matricule);

            // Lire les notes à partir de la 4e colonne
            for (int i = 3; i < parties.length; i++) {
                try {
                    double note = Double.parseDouble(parties[i].replace(" ", ""));
                    etudiant.ajouterNote(note);
                } catch (NumberFormatException e) {
                    System.out.println("Note invalide ignorée: '" + parties[i].replace(" ", "") + "'");
                }
            }

            etudiants.add(etudiant);

        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("Erreur: indice hors limites lors du traitement de la ligne.");
        }
    }
}
