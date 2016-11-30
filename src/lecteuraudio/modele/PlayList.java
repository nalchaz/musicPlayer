/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lecteuraudio.modele.Musique; 

/**
 *
 * @author nahel
 */
public abstract class PlayList extends NoeudMusique{
    
    private  ListProperty<NoeudMusique> playlist= new SimpleListProperty<>(FXCollections.observableArrayList()); 
    protected String nom; 
    
    
    public String getNom(){ 
        return nom;
    }
    
    public void setNom (String nom){ 
        this.nom=nom; 
    }
    
    public void ajouter(NoeudMusique m){ 
            playlist.add(m);
        
    }
    
    public void supprimer(NoeudMusique m){
        playlist.remove(m);      
    }
    
    public ObservableList<NoeudMusique> getPlayList() {
        return playlist.get(); 

    }
    
    public ListProperty<NoeudMusique> musiquesProperty() { 
        return playlist ; 
    }
    
    @Override
    public String toString() { 
        return nom; 
    }
    
    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
