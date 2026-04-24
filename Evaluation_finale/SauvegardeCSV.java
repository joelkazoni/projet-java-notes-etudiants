import java.io.*;
import java.util.List;


 //SauvegardeCSV - Principe SRP: uniquement la sauvegarde.
 //Implémente ISauvegarde (DIP).
 //      sans modifier cette classe.

 

public class SauvegardeCSV implements ISauvegarde {

     //Sauvegarde la liste classée dans un fichier CSV.
     

    @Override
    public void sauvegarder(List<Etudiant> etudiants, String cheminFichier) {
        if (etudiants == null || etudiants.isEmpty()) {
            System.out.println("Aucun étudiant à sauvegarder.");
            return;
        }

        try {
            FileWriter writer = new FileWriter(cheminFichier);
            PrintWriter imprimeur = new PrintWriter(writer);

            // En-tête du CSV
            imprimeur.println("rang,code,nom,prenom,matricule,nombre_notes,moyenne,mention");

            // Écriture de chaque étudiant avec son rang
            for (int i = 0; i < etudiants.size(); i++) {
                Etudiant e = etudiants.get(i);
                double moy = (int)(e.getMoyenne() * 100 + 0.5) / 100.0;
                imprimeur.println((i + 1) + "," + e.getCode() + "," + e.getNom()
                        + "," + e.getPrenom() + "," + e.getMatricule()
                        + "," + e.getNotes().size() + "," + moy + "," + e.getMention());
            }

            imprimeur.close();
            writer.close();

            System.out.println("Résultats sauvegardés dans : " + cheminFichier);
            System.out.println(etudiants.size() + " étudiant(s) enregistré(s).");

        } catch (IOException e) {
            System.out.println("Erreur de sauvegarde: " + e.getMessage());
        }
    }
}
