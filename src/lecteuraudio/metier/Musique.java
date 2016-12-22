/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lecteuraudio.metier;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author nahel
 */
public class Musique extends NoeudMusique implements Serializable{
    
    private String path;
    private StringProperty auteurProperty=new SimpleStringProperty();    
    
    
    public Musique(){
        
    }
    
    public Musique (String auteur, String titre, String path){
        this.auteurProperty.set(auteur); 
        this.titreProperty.set(titre);
        this.path=path; 
    }  
    
    public StringProperty auteurProperty(){
        return auteurProperty; 
    }
    
    public String getAuteur() {
        return auteurProperty.get();
    }

    public void setAuteur(String auteur) {
        this.auteurProperty.set(auteur);
    }
    

    public void setPath(String path){    
        this.path=path;        
    }
    
    public String getPath(){
        return path;
    }
    
    @Override 
    public String toString (){ 
        return titreProperty.get(); 
    }
}
