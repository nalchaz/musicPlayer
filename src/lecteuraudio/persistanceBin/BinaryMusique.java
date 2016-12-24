/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.persistanceBin;

import java.io.Serializable;
import lecteuraudio.metier.IMusique;
import lecteuraudio.metier.Musique;

/**
 *
 * @author alexd
 */
public class BinaryMusique implements IMusique, Serializable{
    private Musique musique; 
    @Override
    public String getAuteur() {
        return musique.getAuteur(); 
    }

    @Override
    public String getPath() {
        return musique.getPath(); 
    }

    @Override
    public void setPath(String path) {
        musique.setPath(path);
    }

    @Override
    public void setAuteur(String auteur) {
        musique.setAuteur(auteur); 
    }

    @Override
    public String getTitre() {
        return musique.getTitre(); 
    }
    
}
