/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import javafx.collections.MapChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
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
    
    public void importerPlayLists(File repertoire,ListePlayLists liste){ 
        for(File f : repertoire.listFiles()){ 
            String nom=f.getName(); 
            String extension=nom.substring(nom.length()-3, nom.length()); 
            if (extension.equals("txt") && nom.charAt(0)=='.' ){ 
                importerPlayList(f,liste); 
            }
           
        }
    }
    
    private void importerPlayList(File f,ListePlayLists liste){ 
        String nomMusique; 

        try { 
            BufferedReader br= new BufferedReader(new FileReader(f)) ;
            PlayList p=new PlayList (br.readLine());
            while ((nomMusique=br.readLine())!=null){ 
                ajouterMusiqueAPlayList(p,nomMusique,liste.getPlayListTout()); 
            }
            liste.ajouterPlayList(p); 
            br.close();
            
        }
        catch (IOException e){ 
            e.printStackTrace();
        } 
    }
    
    private void ajouterMusiqueAPlayList (PlayList p,String nom,PlayList tout){ 
        for (Musique m : tout.getPlayList()){ 
            if (m.getTitre().equals(nom)){ 
                p.ajouter(m); 
            }
        }
    }
    
    public  void chercherDisqueDur (PlayList tout) {
        JFileChooser dialogue = new JFileChooser(new File(".."));
	File fichier;
	
        if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            fichier = dialogue.getSelectedFile();
            try {
                gesRep.copierDansRepository(fichier);
                ajouterMusique(fichier, tout);
            } 
            catch (FileAlreadyExistsException e) {
                dialogueErreurFichierExistant();
            }
            catch (Exception ex){ 
                ex.printStackTrace();
            }
        }
    
    }   
    
    private void dialogueErreurFichierExistant() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Musique déjà importée");
        alert.showAndWait();

    }
  
    private void ajouterMusique(File f, PlayList tout) {
        int taille = (int) f.getName().length() - 4;
        Musique m = new Musique("auteur", f.getName().substring(0, taille), ("file:///" + System.getProperty("user.dir").replace("\\", "/") + "/Musiques/" + f.getName().replaceAll(" ", "%20")));
        Media media = new Media(m.getPath());
        media.getMetadata().addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Object> ch) {
                if (ch.wasAdded()) {
                    m.setAuteur((String) media.getMetadata().get("artist"));
                }
            }
        });

        tout.ajouter(m);
    }

}
