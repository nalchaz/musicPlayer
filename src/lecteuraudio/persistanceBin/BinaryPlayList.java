/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.persistanceBin;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import lecteuraudio.metier.NoeudMusique;
import lecteuraudio.metier.IPlayList;
import lecteuraudio.metier.NoeudMusique;
import lecteuraudio.metier.PlayList;

/**
 *
 * @author alexd
 */
public class BinaryPlayList extends IPlayList implements  Externalizable {
    private PlayList playlist= new PlayList();  
    
    public BinaryPlayList(){}
    
    public BinaryPlayList(String titre){ 
        playlist.setTitre(titre);
    }

    @Override
    public ArrayList<IPlayList> getListPlayList() {
        return playlist.getListPlayList(); 
    }

    @Override
    public String getTitre() {
        return playlist.getTitre(); 
    }
    
    @Override
    public StringProperty titreProperty(){
        return playlist.titreProperty();
    }
    
    @Override 
    public void setTitre(String titre){ 
        playlist.setTitre(titre);
    }
    
    @Override 
    public boolean ajouter(NoeudMusique nm){ 
        return playlist.ajouter(nm);
    }
    
    @Override 
    public ObservableList<NoeudMusique> getPlayList(){ 
        return playlist.getPlayList(); 
    }
    
    @Override
    public boolean isEmpty(){
        return playlist.isEmpty();
    }
    
    @Override
    public void supprimer(NoeudMusique m){
        playlist.supprimer(m);      
    }

    @Override
    public ListProperty<NoeudMusique> playlistProperty() { 
        return playlist.playlistProperty() ; 
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        List<NoeudMusique> liste= new ArrayList<>(getPlayList()); 
        out.writeObject(getTitre());
        out.writeObject(liste);
    }

    
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setTitre((String)in.readObject()); 
        List<NoeudMusique> liste=(ArrayList)in.readObject(); 
        for (NoeudMusique nm : liste){
            ajouter(nm); 
        }
    }
    
    
}
