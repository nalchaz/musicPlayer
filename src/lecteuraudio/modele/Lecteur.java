/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
/**
 *
 * @author nahel
 */
public class Lecteur {
        
    private MediaPlayer mediaPlayer;
    private int index;
    private PlayList playList;
    private NoeudMusique musiqueCourante;
    
    
    public NoeudMusique play(){
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
    
    public void play(NoeudMusique musique){
        if(musique instanceof Musique){
            Musique m=(Musique)musique;
            Media media=new Media(m.getPath());
            mediaPlayer=new MediaPlayer(media);
            mediaPlayer.play();
        }
        else{
            playList=(PlayList)musique;
            index=0;
            play();
        }
    }
    
    public ReadOnlyObjectProperty<Duration> currentTimeProperty(){
        return mediaPlayer.currentTimeProperty();
    }
    
    public Duration getCurrentTime(){
        return mediaPlayer.getCurrentTime();
    }
    
    public Duration getTotalDuration(){
        return mediaPlayer.getTotalDuration();
    }
    
    public NoeudMusique getMusiqueCourante(){ 
        return musiqueCourante;
    }
    
    public void setMusiqueCourante(NoeudMusique musique){ 
        this.musiqueCourante=musique; 
    }
    
    public void setPlaylist(PlayList p){ 
        this.playList=p; 
    }
  
    
    public void setVolume(double volume){
        mediaPlayer.setVolume(volume);
    }
    
    public void setOnEndOfMedia(Runnable value){
        mediaPlayer.setOnEndOfMedia(value);
    }
    
    public void seek(Duration newDuration){
        mediaPlayer.seek(newDuration);
    }
    
    public boolean isNull(){
        return mediaPlayer==null;
    }
    
      public void pause(){
        
        if(mediaPlayer!=null)
           mediaPlayer.pause();
        
    }
      
    public NoeudMusique next(){
        index=playList.getPlayList().indexOf(musiqueCourante);
        index++;
        if(index>=playList.getPlayList().size()){
            index=0;
        }
        mediaPlayer.stop(); 
        musiqueCourante=playList.getPlayList().get(index);
        play(musiqueCourante);
        return musiqueCourante;
        
    }
    
    public NoeudMusique precedent(){
        index=playList.getPlayList().indexOf(musiqueCourante);
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
