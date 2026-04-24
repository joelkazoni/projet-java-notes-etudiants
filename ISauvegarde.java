import java.util.List;

public interface ISauvegarde {

     // Sauvegarde la liste des étudiants dans un fichier.
     // @param etudiants liste des étudiants à sauvegarder
     // @param cheminFichier chemin du fichier destination

    void sauvegarder(List<Etudiant> etudiants, String cheminFichier);
}
