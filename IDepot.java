import java.util.List;

// IDepot - Principe ISP : interface dédiée uniquement aux opérations CRUD.
// DIP : GestionEtudiants et Main dépendent de cette abstraction,
//       jamais de DepotEtudiants directement.

public interface IDepot {

    // Ajoute un étudiant (vérifie les doublons par matricule)
    void ajouter(Etudiant etudiant);

    // Supprime l'étudiant correspondant au matricule donné
    void supprimer(String matricule);

    // Retourne l'étudiant correspondant au matricule, ou null si introuvable
    Etudiant trouverParMatricule(String matricule);

    // Retourne la liste complète des étudiants en mémoire
    List<Etudiant> getTous();

    // Indique si la liste est vide
    boolean estVide();
}
