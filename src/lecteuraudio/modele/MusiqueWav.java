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
public class MusiqueWav extends Musique{
    
    public MusiqueWav(String auteur, String titre, String path){
        super.setAuteur(auteur);
        super.setTitre(titre);
        super.setPath(path);
    }
}
