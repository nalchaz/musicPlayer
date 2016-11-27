/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author nahel
 */
public class PlayList {
    
    private List<Musique> playList;
    private String nom; 
    public PlayList(String nom){ 
        this.nom=nom; 
    }
    public void ajouter(Musique m){
        if(playList==null){
            playList=new LinkedList<>();
        }
        playList.add(m);
        
    }
    
    public void supprimer(Musique m){

        playList.remove(m);
        
    }
    
    public void setPlayList(List<Musique> list){
        playList=list;
    }
    
    public List<Musique> getPlayList(){
        return playList;
    }
    
    
}
