import java.util.*;


 // ClasseurParMoyenne - Principe SRP: uniquement le classement.
 // Implémente IClasseur (DIP).
 
 

public class ClasseurParMoyenne implements IClasseur {


     ///Trie les étudiants par moyenne décroissante 
     // Retourne une nouvelle liste triée (n'altère pas l'original).

    @Override
    public List<Etudiant> classer(List<Etudiant> etudiants) {
        if (etudiants == null || etudiants.isEmpty()) {
            return new ArrayList<>();
        }

        // Copie pour ne pas modifier la liste originale 
        List<Etudiant> classement = new ArrayList<>(etudiants);

        // Tri par moyenne décroissante
        // Algorithme de tri à bulles - simple et pédagogique
        int n = classement.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (classement.get(j).getMoyenne() < classement.get(j + 1).getMoyenne()) {
                    // Échange
                    Etudiant temp = classement.get(j);
                    classement.set(j, classement.get(j + 1));
                    classement.set(j + 1, temp);
                }
            }
        }

        System.out.println("Classement effectué pour " + classement.size() + " étudiant(s).");
        return classement;
    }
}
