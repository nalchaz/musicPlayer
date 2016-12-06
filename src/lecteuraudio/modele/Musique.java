/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lecteuraudio.modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author nahel
 */
public class Musique {
    
    private String auteur;
    private String path;
    private String titre;
    
    
    public Musique(){
        
    }
    
    public Musique (String auteur, String titre, String path){
        this.auteur=auteur; 
        this.titre=titre;
        this.path=path; 
    }

    private StringProperty titreProperty;
    
    public StringProperty titreProperty(){
        return new SimpleStringProperty(titre); 
    }
    
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre=titre;
    }
    
    
    public void setAuteur(String auteur){
        this.auteur=auteur;
    }
    
    public String getAuteur(){
        return auteur;
    }
    

    public void setPath(String path){    
        this.path=path;        
    }
    
    public String getPath(){
        return path;
    }
    
    @Override 
    public String toString (){ 
        return titre; 
    }
}
