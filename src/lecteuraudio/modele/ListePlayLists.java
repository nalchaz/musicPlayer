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
    private static  ListePlayLists instance; 
    private ListProperty<PlayList> listePlaylists= new SimpleListProperty<>(FXCollections.observableArrayList()); 
   
    public static ListePlayLists getInstance (){ 
        if (instance == null){ 
             instance=new ListePlayLists();
        }
        return instance;        
    }
     
    public ObservableList<PlayList> getPlayLists() {
        return listePlaylists.get(); 
    }
    
    public ListProperty<PlayList> playListsProperty() { 
        return listePlaylists  ; 
    }
    
    public void ajouterPlayList (PlayList p){    
        listePlaylists.add(p);   
    }
    public PlayList getPlayListTout (){ 
        return listePlaylists.get(0); 
    }
     
}
