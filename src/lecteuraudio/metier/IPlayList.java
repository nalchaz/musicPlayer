/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

import java.util.ArrayList;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author alexd
 */
public interface IPlayList extends INoeudMusique {
    public  ArrayList<PlayList> getListPlayList();
    public boolean ajouter(NoeudMusique nm);
    public ObservableList<NoeudMusique> getPlayList();
    
}
