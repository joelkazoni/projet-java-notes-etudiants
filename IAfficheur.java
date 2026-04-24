import java.util.List;

// IAfficheur - Principe ISP : interface dédiée uniquement à l'affichage.
// DIP : les classes qui ont besoin d'afficher dépendent de cette abstraction.

public interface IAfficheur {

    // Affiche le classement complet avec rangs et mentions
    void afficherClassement(List<Etudiant> etudiantsClasses);

    // Affiche les statistiques globales de la promotion
    void afficherStatistiques(List<Etudiant> etudiantsClasses);

    // Affiche le détail des notes d'un seul étudiant
    void afficherNotes(Etudiant etudiant);
}
