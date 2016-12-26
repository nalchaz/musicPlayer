/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;


import java.util.ArrayList;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author nahel
 */
public class PlayList extends NoeudMusique implements IPlayList{
    
    private  ListProperty<NoeudMusique> playlist= new SimpleListProperty<>(FXCollections.observableArrayList());
    public ObservableList<NoeudMusique> getPlayList() {return playlist.get(); }   
    public void setPlayList(ListProperty<NoeudMusique> playList) {this.playlist.set(playList);}
    public ListProperty<NoeudMusique> playlistProperty() { return playlist ; }
    
    public PlayList(){
    }
    
    public PlayList(String titre){
        this.titreProperty.set(titre);
    }
    
    // Return true si la musique a été ajouté, false si la musique y était déja
    public boolean ajouter(NoeudMusique m) {

        for (NoeudMusique nm : playlist) {
            if (nm.getTitre().equals(m.getTitre())) {
                return false;
            }
        }
        playlist.add(m);
        return true;
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
   
    /* 
    * Methode de recherche de NoeudMusique par String dans une playlist
    */
    public PlayList rechByString(String recherche){
        recherche=recherche.toLowerCase();
        PlayList playListRech=new PlayList("Recherche");
        for(NoeudMusique m : playlist){
            if(m.titreProperty().get().toLowerCase().contains(recherche)) //To mis un lowercase pour une recherche plus efficace
                playListRech.ajouter(m);
        }
        return playListRech;
    }
    

    
    public ArrayList<PlayList> getListPlayList(){
        ArrayList<PlayList> list=new ArrayList();
        for(NoeudMusique m : playlist){
            if(m instanceof PlayList){
                list.add((PlayList)m);
            }
        }
        return list;
    }
    
}
