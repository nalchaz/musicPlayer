/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 *
 * @author nahel
 */
public class Lecteur {
        
    private MediaPlayer mediaPlayer;
    private int index=0;
    private PlayList playList;
    private MediaPlayer.Status status;
    
    
    public boolean play(){
        if(mediaPlayer==null){
            if(playList.isEmpty())
                return false;
            play(playList.getPlayList().get(index));
        }
        else{ 
            mediaPlayer.play();
        }
        return true;
    }
    
    public void setPlaylist(PlayList p){ 
        this.playList=p; 
    }
    
    public void play(NoeudMusique musique){
        String chemin;

            
        chemin="file:///"+System.getProperty("user.dir").replace("\\", "/")+"/Musiques/"+musique.getPath();           
        mediaPlayer=new MediaPlayer(new Media(chemin.replaceAll(" ","%20"))); //Replace all pour que les espaces ne pausent pas de problème
        mediaPlayer.play();
        
    }
    public void pause(){
        
        mediaPlayer.pause();
        
    }
    
    public void next(){
        index++;
        if(index>=playList.getPlayList().size()){
            index=0;
        }
        mediaPlayer.stop();   
        play(playList.getPlayList().get(index));
        
    }
    
    public void precedent(){
        index--;
        if(index<0){
            index=playList.getPlayList().size()-1;
        }
        mediaPlayer.stop();   
        play(playList.getPlayList().get(index));
        
    }
    
    
    
}
