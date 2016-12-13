/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alexd
 */
public class ListePlayLists {

    private ListProperty<PlayList> listePlaylists= new SimpleListProperty<>(FXCollections.observableArrayList()); 
     
    public ObservableList<PlayList> getPlayLists() {
        return listePlaylists.get(); 
    }
    
    public ListProperty<PlayList> playListsProperty() { 
        return listePlaylists  ; 
    }
    
    //Return false si le nom de la playlist existe déja, sinon l'ajoute et return true
    public boolean ajouterPlayList (PlayList p){  
        for (PlayList playList : listePlaylists) {
            if(playList.getNom() == null ? p.getNom() == null : playList.getNom().equals(p.getNom()))
                return false;
        }
        listePlaylists.add(p);   
        return true;
    }
    
    //Return true si la playlist a été supprimé, false si il s'agit de la playlist tout
    public boolean supprimerPlayList (PlayList p){ 
        if(p!=getPlayListTout()){
            listePlaylists.remove(p);  
            return true;
        }
        return false;
    }
    
    //Return la playlist principale avec toutes les musiques dedans
    public PlayList getPlayListTout (){ 
        for (PlayList p : listePlaylists){ 
           if (p.getNom().equals("Musiques")){ 
               return p; 
           }
        }
        return null; 
    }
     
}
