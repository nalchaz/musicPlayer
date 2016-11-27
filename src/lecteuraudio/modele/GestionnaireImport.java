/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author aldonne
 */
public class GestionnaireImport {
    
    
    public static void importerRepertoireMusiques (File repertoire){ 
        for( File f : repertoire.listFiles()){ 
           GestionnaireImport.ajouterMusique(f); 
        }
        
    }
    public static void chercherDisqueDur () {
        JFileChooser dialogue = new JFileChooser(new File(".."));
	File fichier;
	
	if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
	    fichier = dialogue.getSelectedFile();
            GestionnaireRepertoire.copierDansRepository(fichier);
            GestionnaireImport.ajouterMusique(fichier);
        }    
    }
    
    private static void ajouterMusique(File f) { 
        int taille = (int)f.getName().length()-4; 
        Musique m= new MusiqueWav("auteur",f.getName().substring(0,taille)  , f.getName());      
        LecteurAudio.tout.ajouter(m);
    }
    
}
