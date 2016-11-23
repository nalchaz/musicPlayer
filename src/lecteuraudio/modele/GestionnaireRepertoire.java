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
public class GestionnaireRepertoire {
    private static String path=System.getProperty("user.dir")+"/Musiques";
    public static void ouverture() { 
        if (new File(path).exists()){
            GestionnaireImport.importerRepertoireMusiques(new File(path));
        }
        else {
            creerRepertoire(); 
        }
    }
    
    private static void creerRepertoire() { 
        new File(System.getProperty("user.dir")+"/Musiques").mkdir();
    }
    
    
}
