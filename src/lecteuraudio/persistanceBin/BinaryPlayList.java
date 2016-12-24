/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.persistanceBin;

import java.io.Serializable;
import java.util.ArrayList;
import lecteuraudio.metier.IPlayList;
import lecteuraudio.metier.PlayList;

/**
 *
 * @author alexd
 */
public class BinaryPlayList implements IPlayList, Serializable {
    private PlayList playlist; 

    @Override
    public ArrayList<PlayList> getListPlayList() {
        return playlist.getListPlayList(); 
    }

    @Override
    public String getTitre() {
        return playlist.getTitre(); 
    }
    
    
}
