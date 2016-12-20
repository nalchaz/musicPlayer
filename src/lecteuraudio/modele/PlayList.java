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
public class PlayList extends NoeudMusique{
    
    private  ListProperty<NoeudMusique> playlist= new SimpleListProperty<>(FXCollections.observableArrayList());
    
    public PlayList(){
    
    }
    
    public PlayList(String titre){
        this.titreProperty.set(titre);
    }
    
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
    
    
    public String getNom(){ 
        return titreProperty.get();
    }
    
    public void setNom (String nom){ 
        this.titreProperty.set(nom); 
    }
    // Return true si la musique a été ajouté, false si la musique y était déja
    
    public boolean ajouter(NoeudMusique m){ 
        if(!playlist.contains(m)){
            playlist.add(m);
            return true;
        }
    return false;
    }
    
    public void supprimer(NoeudMusique m){
        playlist.remove(m);      
    }
    
    
    public boolean isEmpty(){
        return playlist.isEmpty();
    }
            
            
    @Override
    public String toString() { 
        return titreProperty.get(); 
    }
   
    public PlayList rechByString(String recherche){
        recherche=recherche.toLowerCase();
        PlayList playListRech=new PlayList("Recherche");
        for(NoeudMusique m : playlist){
            if(m.titreProperty().get().toLowerCase().contains(recherche)) //To mis un lowercase pour un erecherche plus efficace
                playListRech.ajouter(m);
        }
        return playListRech;
    }
    
    
}
