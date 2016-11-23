/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.io.File;

/**
 *
 * @author aldonne
 */
public class GestionnaireImport {
     static PlayList tout= new PlayList("Tout");
    public static void importerRepertoireMusiques (File repertoire){ 
        for( String s : repertoire.list()){ 
           
            
                Musique m=new MusiqueWav("auteur",s,s);  
            
            
            tout.ajouter(m); 
        }
        
    }
    
    
}
