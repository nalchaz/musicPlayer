/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;




/**
 *
 * @author aldonne
 */
public class GestionnaireRepertoire {
    
    private static final String repositoryPath=System.getProperty("user.dir")+"/Musiques";
    private static final String repositoryPlayLists=System.getProperty("user.dir")+"/Playlists"; 
    
    
    public String getRepositoryPath (){ 
        return repositoryPath; 
    }
    
    public String getRepositoryPlayLists (){ 
        return repositoryPlayLists; 
    }
    
    public  void ouverture() { 
        if (!new File(repositoryPath).exists()){
            creerRepertoire(repositoryPath);
             
        }
        if (!new File(repositoryPlayLists).exists()){
            creerRepertoire(repositoryPlayLists);
             
        }
    }
    
   
    
    private void creerRepertoire(String nom) {
        new File(nom).mkdir();
    }

    public void copierDansRepository(File source) throws Exception {
        String destination = repositoryPath + "/" + source.getName();
       
            Files.copy(source.toPath(), new File(destination).toPath());
        
    }


    
    public void ecrirePlayLists (ListePlayLists liste){ 
        for (PlayList p : liste.getPlayLists()){ 
            creerFichierPlayList(p); 
        }
    }

    public void creerFichierPlayList(PlayList p) {
        if (!p.getNom().equals("Musiques")) {
            File f = new File(repositoryPlayLists+"/."+p.getNom()+".txt");
            try { 
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
                System.out.println(p.getNom());
                pw.println(p.getNom());
                for (Musique m : p.getPlayList()) {
                    pw.println(m.getTitre());
                }
                pw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void ecraserPlayList (PlayList p){ 
        File f =new File(repositoryPlayLists+ "/."+p.getNom()+".txt"); 
        f.delete(); 
    }

}
