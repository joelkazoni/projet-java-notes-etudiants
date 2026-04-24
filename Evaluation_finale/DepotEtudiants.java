import java.util.ArrayList;
import java.util.List;

// DepotEtudiants - Principe SRP : uniquement la gestion CRUD en mémoire.
// Implémente IDepot (DIP).


public class DepotEtudiants implements IDepot {

    private final List<Etudiant> etudiants;

    public DepotEtudiants() {
        this.etudiants = new ArrayList<>();
    }

    // Charge une liste initiale  LecteurCSV
    public void chargerDepuis(List<Etudiant> liste) {
        etudiants.clear();
        etudiants.addAll(liste);
    }

    @Override
    public void ajouter(Etudiant etudiant) {
        if (etudiant == null) {
            System.out.println("Erreur : étudiant null, impossible d'ajouter.");
            return;
        }
        // Vérification doublon par matricule (equals redéfini dans Etudiant)
        if (etudiants.contains(etudiant)) {
            System.out.println("Erreur : le matricule " + etudiant.getMatricule() + " existe déjà.");
            return;
        }
        etudiants.add(etudiant);
        System.out.println("Étudiant ajouté : " + etudiant.getPrenom()
                + " " + etudiant.getNom() + " [" + etudiant.getMatricule() + "]");
    }

    @Override
    public void supprimer(String matricule) {
        Etudiant trouve = trouverParMatricule(matricule);
        if (trouve == null) {
            System.out.println("Aucun étudiant trouvé avec le matricule : " + matricule);
            return;
        }
        etudiants.remove(trouve);
        System.out.println("Étudiant supprimé : " + trouve.getPrenom()
                + " " + trouve.getNom() + " [" + matricule + "]");
    }

    @Override
    public Etudiant trouverParMatricule(String matricule) {
        for (Etudiant e : etudiants) {
            if (e.getMatricule().equalsIgnoreCase(matricule)) return e;
        }
        return null;
    }

    @Override
    public List<Etudiant> getTous() {
        return etudiants;
    }

    @Override
    public boolean estVide() {
        return etudiants.isEmpty();
    }
}
