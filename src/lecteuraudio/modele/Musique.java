/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lecteuraudio.modele;
import java.net.URL;


/**
 *
 * @author nahel
 */
public abstract class Musique {
    
    private String auteur;
    private String titre;
    private URL son;

    
    public void setAuteur(String auteur){
        this.auteur=auteur;
    }
    
    public String getAuteur(){
        return auteur;
    }
    
    public void setTitre(String titre){
        this.titre=titre;
    }
    
    public String getTitre(){
        return titre;
    }
    public void setSon(String son){
        URL url=Musique.class.getResource(son);
        this.son=url;
        
    }
    
    public URL getSon(){
        return son;
    }
}
