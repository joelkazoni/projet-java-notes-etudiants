import java.util.List;

// GestionEtudiants - Principe SRP : orchestration du flux uniquement.
// Elle coordonne les autres composants mais ne fait aucun calcul,
// aucun affichage, aucun CRUD elle-même.
// DIP : dépend uniquement des interfaces (ILecteur, ICalculateur,
//        IClasseur, ISauvegarde, IDepot, IAfficheur).

public class GestionEtudiants {

    private final ILecteur     lecteur;
    private final ICalculateur calculateur;
    private final IClasseur    classeur;
    private final ISauvegarde  sauvegarde;
    private final IDepot       depot;
    private final IAfficheur   afficheur;

    // Liste classée conservée en mémoire après chaque (re)calcul
    private List<Etudiant> etudiantsClasses;

    // Vrai si une modification a eu lieu depuis le dernier calcul
    private boolean modifie = false;

    // Constructeur par injection de dépendances (DIP)
    public GestionEtudiants(ILecteur lecteur,
                            ICalculateur calculateur,
                            IClasseur classeur,
                            ISauvegarde sauvegarde,
                            IDepot depot,
                            IAfficheur afficheur) {
        this.lecteur     = lecteur;
        this.calculateur = calculateur;
        this.classeur    = classeur;
        this.sauvegarde  = sauvegarde;
        this.depot       = depot;
        this.afficheur   = afficheur;
    }


    //  CHARGEMENT depuis un fichier CSV


    public void charger(String fichierEntree) {
        System.out.println("\n--- Lecture du fichier CSV : " + fichierEntree + " ---");
        List<Etudiant> lus = lecteur.lire(fichierEntree);

        if (lus.isEmpty()) {
            System.out.println("Aucun étudiant chargé. Vérifiez le fichier.");
            return;
        }

        // Transfert vers le dépôt
        ((DepotEtudiants) depot).chargerDepuis(lus);

        // Calcul initial des moyennes puis classement
        calculateur.calculerMoyennes(depot.getTous());
        etudiantsClasses = classeur.classer(depot.getTous());

        modifie = false;
        System.out.println(depot.getTous().size() + " étudiant(s) chargé(s) avec succès.");
    }


    //  AFFICHAGE


    public void afficherClassement() {
        if (!verifierDonnees()) return;
        if (modifie) recalculer();
        afficheur.afficherClassement(etudiantsClasses);
        afficheur.afficherStatistiques(etudiantsClasses);
    }

    public void afficherNotesEtudiant(String matricule) {
        if (!verifierDonnees()) return;
        Etudiant e = depot.trouverParMatricule(matricule);
        afficheur.afficherNotes(e);
    }



    public void ajouterEtudiant(Etudiant etudiant) {
        depot.ajouter(etudiant);
        // Calcul immédiat de la moyenne du nouvel étudiant
        // pour éviter qu'elle reste à 0.0
        calculateur.calculerMoyennes(depot.getTous());
        etudiantsClasses = classeur.classer(depot.getTous());
        modifie = false;
    }

    public void supprimerEtudiant(String matricule) {
        if (!verifierDonnees()) return;
        depot.supprimer(matricule);
        modifie = true;
    }


    //  MODIFICATION DE NOTES


    public void modifierNote(String matricule, int indexNote, double nouvelleNote) {
        if (!verifierDonnees()) return;

        Etudiant e = depot.trouverParMatricule(matricule);
        if (e == null) {
            System.out.println("Matricule introuvable : " + matricule);
            return;
        }

        List<Double> notes = e.getNotes();
        if (indexNote < 1 || indexNote > notes.size()) {
            System.out.println("Index invalide. Cet étudiant a " + notes.size()
                    + " note(s) (index 1 à " + notes.size() + ").");
            return;
        }

        double ancienne = notes.get(indexNote - 1);
        notes.set(indexNote - 1, nouvelleNote);
        modifie = true;
        System.out.println("Note " + indexNote + " modifiee pour " + e.getPrenom()
                + " " + e.getNom() + " : ancienne=" + ancienne + " nouvelle=" + nouvelleNote);
    }

    public void ajouterNote(String matricule, double note) {
        if (!verifierDonnees()) return;

        Etudiant e = depot.trouverParMatricule(matricule);
        if (e == null) {
            System.out.println("Matricule introuvable : " + matricule);
            return;
        }

        if (e.getNotes().size() >= 5) {
            System.out.println("Impossible : " + e.getPrenom() + " " + e.getNom()
                    + " a deja 5 notes. Utilisez l'option modifier a la place.");
            return;
        }

        e.ajouterNote(note);
        modifie = true;
        System.out.println("Note " + note + " ajoutee pour " + e.getPrenom()
                + " " + e.getNom() + " (total : " + e.getNotes().size() + " notes)");
    }


    //  RECALCUL et SAUVEGARDE


    public void recalculer() {
        if (!verifierDonnees()) return;
        calculateur.calculerMoyennes(depot.getTous());
        etudiantsClasses = classeur.classer(depot.getTous());
        modifie = false;
        System.out.println("Recalcul et reclassement effectués.");
    }

    public void sauvegarder(String fichierSortie) {
        if (!verifierDonnees()) return;
        if (modifie) {
            System.out.println("[!] Modifications detectees, recalcul avant sauvegarde...");
            recalculer();
        }
        sauvegarde.sauvegarder(etudiantsClasses, fichierSortie);
    }

    // =========================================================
    //  UTILITAIRE
    // =========================================================

    private boolean verifierDonnees() {
        if (depot.estVide()) {
            System.out.println("Aucune donnée chargée. Utilisez l'option 1 pour charger un fichier CSV.");
            return false;
        }
        return true;
    }

    public boolean isModifie()                  { return modifie; }
    public List<Etudiant> getEtudiantsClasses() { return etudiantsClasses; }
    public IDepot getDepot()                    { return depot; }
}
