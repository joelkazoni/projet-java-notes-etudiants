import java.util.ArrayList;
import java.util.List;


public class Etudiant {

    // Attributs privés - encapsulation
    private String nom;
    private String prenom;
    private String matricule;
    private List<Double> notes;
    private double moyenne;

    //Compteur de classe
    private static int compteur = 0;
    private int code;

    //Constructeurs
    public Etudiant(String nom, String prenom, String matricule) {
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.notes = new ArrayList<>();
        this.moyenne = 0.0;
        this.code = ++compteur;
    }

    //Getters & Setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public List<Double> getNotes() {
        return notes;
    }

    public void ajouterNote(double note) {
        this.notes.add(note);
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public int getCode() {
        return code;
    }

    public static int getCompteur() {
        return compteur;
    }


    //Retourne la mention selon la moyenne

    public String getMention() {
        if (moyenne >= 90) return "Excellent";
        if (moyenne >= 80) return "Très bien";
        if (moyenne >= 70) return "Bien";
        if (moyenne >= 60) return "Passable";
        return "Échec";
    }


    //Redéfinition de equals - compare par matricule

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Etudiant)) return false;
        Etudiant e = (Etudiant) obj;
        return this.matricule.equals(e.matricule);
    }


    //Redéfinition de toString - affiche les informations

    @Override
    public String toString() {
        double moy = (int)(moyenne * 100 + 0.5) / 100.0;
        return "Code: " + code + " | " + prenom + " " + nom
                + " | Matricule: " + matricule
                + " | Moyenne: " + moy
                + " | Mention: " + getMention();
    }
}
