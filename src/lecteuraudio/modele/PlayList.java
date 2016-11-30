/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;


import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author nahel
 */
public abstract class PlayList extends NoeudMusique{
    
    private  ListProperty<NoeudMusique> playlist= new SimpleListProperty<>(FXCollections.observableArrayList());

    
    public void setPlayList(ListProperty<NoeudMusique> playList) {
        this.playlist = playList;
    }

    public ObservableList<NoeudMusique> getPlayList() {
        return playlist.get(); 

    }
    
    public void setPlayList(ObservableList<NoeudMusique> value) {
        playlist.set(value); 

    }
    
    public ListProperty<NoeudMusique> playlistProperty() { 
        return playlist ; 
    }
    
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
    
    
    public boolean isEmpty(){
        return playlist.isEmpty();
    }
            
            
    @Override
    public String toString() { 
        return nom; 
    }
   
    
    
}
