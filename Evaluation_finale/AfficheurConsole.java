import java.util.List;

// AfficheurConsole - Principe SRP : uniquement l'affichage dans la console.
// Implémente IAfficheur (DIP).

public class AfficheurConsole implements IAfficheur {

    @Override
    public void afficherClassement(List<Etudiant> etudiantsClasses) {
        if (etudiantsClasses == null || etudiantsClasses.isEmpty()) {
            System.out.println("Aucun étudiant à afficher.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Rang | Nom | Prenom | Matricule | Moyenne | Mention");
        System.out.println("-".repeat(60));

        for (int i = 0; i < etudiantsClasses.size(); i++) {
            Etudiant e = etudiantsClasses.get(i);
            // Arrondi à 2 décimales : (int)(val * 100 + 0.5) / 100.0
            double moy = (int)(e.getMoyenne() * 100 + 0.5) / 100.0;
            System.out.println((i + 1) + " | " + e.getNom() + " | " + e.getPrenom()
                    + " | " + e.getMatricule() + " | " + moy + " | " + e.getMention());
        }

        System.out.println("=".repeat(60));
    }

    @Override
    public void afficherStatistiques(List<Etudiant> etudiantsClasses) {
        if (etudiantsClasses == null || etudiantsClasses.isEmpty()) return;

        double somme = 0;
        int reussis  = 0;
        double max   = etudiantsClasses.get(0).getMoyenne();
        double min   = etudiantsClasses.get(etudiantsClasses.size() - 1).getMoyenne();

        for (Etudiant e : etudiantsClasses) {
            somme += e.getMoyenne();
            if (e.getMoyenne() >= 60) reussis++;
        }

        int total = etudiantsClasses.size();

        System.out.println("\n--- Statistiques de la promotion ---");
        System.out.println("Nombre d'étudiants      : " + total);
        double moyPromo = (int)((somme / total) * 100 + 0.5) / 100.0;
        double maxArrondi = (int)(max * 100 + 0.5) / 100.0;
        double minArrondi = (int)(min * 100 + 0.5) / 100.0;
        int tauxEntier = (int)((reussis * 100.0) / total);

        System.out.println("Moyenne de la promotion : " + moyPromo);
        System.out.println("Meilleure moyenne       : " + maxArrondi
                + "  " + etudiantsClasses.get(0).getPrenom()
                + " " + etudiantsClasses.get(0).getNom());
        System.out.println("Moyenne la plus basse   : " + minArrondi
                + "  " + etudiantsClasses.get(total - 1).getPrenom()
                + " " + etudiantsClasses.get(total - 1).getNom());
        System.out.println("Taux de reussite        : " + tauxEntier
                + "% (" + reussis + "/" + total + ")");
    }

    @Override
    public void afficherNotes(Etudiant etudiant) {
        if (etudiant == null) {
            System.out.println("Étudiant introuvable.");
            return;
        }

        System.out.println("\n--- Notes de " + etudiant.getPrenom()
                + " " + etudiant.getNom()
                + " [" + etudiant.getMatricule() + "] ---");

        List<Double> notes = etudiant.getNotes();
        if (notes.isEmpty()) {
            System.out.println("Aucune note enregistrée.");
            return;
        }

        for (int i = 0; i < notes.size(); i++) {
            double noteArrondie = (int)(notes.get(i) * 100 + 0.5) / 100.0;
            System.out.println("  Note " + (i + 1) + " : " + noteArrondie);
        }
        double moyArrondie = (int)(etudiant.getMoyenne() * 100 + 0.5) / 100.0;
        System.out.println("  Moyenne actuelle : " + moyArrondie + " (" + etudiant.getMention() + ")");
    }
}
