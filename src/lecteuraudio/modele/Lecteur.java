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
    private Musique musiqueCourante;
    
    
    public Musique play(){
        if(mediaPlayer==null){
            if(playList==null || playList.isEmpty())
                return null;
            musiqueCourante=playList.getPlayList().get(index);
            play(musiqueCourante);
        }
        else{ 
            mediaPlayer.play();
        }
    return musiqueCourante;
    }
    
    public void play(Musique musique){
        String chemin;

            
        chemin="file:///"+System.getProperty("user.dir").replace("\\", "/")+"/Musiques/"+musique.getPath();           
        mediaPlayer=new MediaPlayer(new Media(chemin.replaceAll(" ","%20"))); //Replace all pour que les espaces ne pausent pas de problème
        mediaPlayer.play();
        
    }
    
    public Musique getMusiqueCourante(){ 
        return musiqueCourante;
    }
    
    public void setMusiqueCourante(Musique musique){ 
        this.musiqueCourante=musique; 
    }
    
    public void setPlaylist(PlayList p){ 
        this.playList=p; 
    }
    
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    
    public void pause(){
        
        if(mediaPlayer!=null)
            mediaPlayer.pause();
        
    }
    
    public Musique next(){
        index++;
        if(index>=playList.getPlayList().size()){
            index=0;
        }
        mediaPlayer.stop(); 
        musiqueCourante=playList.getPlayList().get(index);
        play(musiqueCourante);
        return musiqueCourante;
        
    }
    
    public Musique precedent(){
        index--;
        if(index<0){
            index=playList.getPlayList().size()-1;
        }
        mediaPlayer.stop();   
        musiqueCourante=playList.getPlayList().get(index);
        play(musiqueCourante);
        return musiqueCourante;
    }
    
    public void setMute(boolean mute){
        mediaPlayer.setMute(mute);
    }
    
    public boolean isMute(){
        return mediaPlayer.isMute();
    }
    
    
}
