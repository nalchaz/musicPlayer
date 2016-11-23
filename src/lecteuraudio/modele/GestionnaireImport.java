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
     static PlayList tout= new PlayList("Tout");
    public static void importerRepertoireMusiques (File repertoire){ 
        for( File f : repertoire.listFiles()){ 
           GestionnaireImport.ajouterMusique(f); 
        }
        
    }
    public static void chercherDisqueDur () {
        JFileChooser dialogue = new JFileChooser(new File(".."));
	File fichier;
	
	if (dialogue.showOpenDialog(null)== 
	    JFileChooser.APPROVE_OPTION) {
	    fichier = dialogue.getSelectedFile();
            GestionnaireImport.ajouterMusique(fichier);
        }    
    }
    
    public static void ajouterMusique(File f) { 
        
    }
    
}
