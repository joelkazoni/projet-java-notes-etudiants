import java.util.List;


public interface IClasseur {

     // Classe les étudiants selon un critère (moyenne décroissante).
     // @param etudiants liste des étudiants à classer
     // @return liste triée des étudiants

    List<Etudiant> classer(List<Etudiant> etudiants);
}
