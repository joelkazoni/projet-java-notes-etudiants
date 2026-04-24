import java.util.List;

public interface ILecteur {

     // Lit les données et retourne une liste d'étudiants.
     // @param cheminFichier chemin du fichier source
     // @return liste des étudiants lus

    List<Etudiant> lire(String cheminFichier);
}
