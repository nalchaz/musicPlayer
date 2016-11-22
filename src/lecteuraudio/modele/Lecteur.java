/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;
import java.applet.Applet;
import java.applet.AudioClip;
/**
 *
 * @author nahel
 */
public class Lecteur {
        
    private AudioClip ac;
    
    public void play(Musique musique){
        
        ac=Applet.newAudioClip(musique.getSon());
        ac.play();
        
    }
    public void stop(){
        
        ac.stop();
        
    }
    
    
    
}
