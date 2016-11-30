/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.sound.midi.Patch;



/**
 *
 * @author aldonne
 */
public class GestionnaireRepertoire {
    private static final String repositoryPath=System.getProperty("user.dir")+"/Musiques";
    
    
    public String getRepositoryPath (){ 
        return repositoryPath; 
    }
    
    public  void ouverture() { 
        if (!new File(repositoryPath).exists()){
            creerRepertoire();    
        }                      
    }
    
    private  void creerRepertoire() { 
        new File(System.getProperty("user.dir")+"/Musiques").mkdir();
    }
    
    public  void copierDansRepository(File source) { 
       
        try{
            String destination=repositoryPath+"/"+source.getName();
            Files.copy(source.toPath(), new File(destination).toPath()); 
        }
        catch(IOException e) { 
            e.printStackTrace(); 
        }
    }
}

