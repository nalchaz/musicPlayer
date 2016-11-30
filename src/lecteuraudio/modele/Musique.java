/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lecteuraudio.modele;


/**
 *
 * @author nahel
 */
public class Musique extends NoeudMusique {
    
    private String auteur;
    private String titre;
    private String path;

    public Musique (String auteur, String titre, String path){
        this.auteur=auteur; 
        this.titre=titre; 
        this.path=path; 
    }
    
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
    public void setPath(String path){    
        this.path=path;        
    }
    
    @Override
    public String getPath(){
        return path;
    }
    
    @Override 
    public String toString (){ 
        return titre; 
    }
}
