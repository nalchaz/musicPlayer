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
    
    public void play(Musique musique){
        
        if(mediaPlayer == null)
         mediaPlayer=new MediaPlayer(new Media("file:///"+System.getProperty("user.dir").replace("\\", "/")+"/Musiques/"+musique.getPath()));
        mediaPlayer.play();
        
    }
    public void pause(){
        
        mediaPlayer.pause();
        
    }
    
    
    
}
