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
    private GestionnaireRepertoire gesRep; 
    public GestionnaireImport (GestionnaireRepertoire gesRep){ 
        this.gesRep=gesRep; 
        
    }
    
    
    public  void importerRepertoireMusiques (File repertoire, PlayList tout){ 
        for( File f : repertoire.listFiles()){ 
           ajouterMusique(f,tout); 
        }
        
    }
    public  void chercherDisqueDur (PlayList tout) {
        JFileChooser dialogue = new JFileChooser(new File(".."));
	File fichier;
	
	if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
	    fichier = dialogue.getSelectedFile();
            gesRep.copierDansRepository(fichier);
            ajouterMusique(fichier,tout);
        }    
    }
    
    private void ajouterMusique(File f,PlayList tout) { 
        int taille = (int)f.getName().length()-4; 
        NoeudMusique m= new Musique("auteur",f.getName().substring(0,taille)  , f.getName()); 
        tout.ajouter(m);
    }
    
}
