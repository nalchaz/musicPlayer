 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import lecteuraudio.metier.PlayList;
import lecteuraudio.metier.Musique;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import javafx.collections.MapChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 *
 * @author aldonne
 */
public class GestionnaireImport {
    
    private String repositoryPath=System.getProperty("user.dir")+"/Musiques";
    
    public String getRepositoryPath (){
    return repositoryPath;
    }
    
     
   
    public void ouverture() {
        if (!new File(System.getProperty("user.dir") + "/Musiques" + "/").exists()) {
            new File(System.getProperty("user.dir") + "/Musiques" + "/").mkdir();
        }
    }
    
 
    public  void importerRepertoireMusiques (PlayList racine){ 
        for( File f : new File(repositoryPath).listFiles()){ 
           ajouterMusique(f,racine); 
        }
        
    }    
    
    public  boolean chercherDisqueDur (PlayList racine, Window window) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All files", "*.*"),
            new FileChooser.ExtensionFilter("Music files", "*.mp3", "*.wav", "*.mp4"), 
            new FileChooser.ExtensionFilter("MP3", "*.mp3"),
            new FileChooser.ExtensionFilter("WAV", "*.wav"),
            new FileChooser.ExtensionFilter("MP4", "*.mp4")
        );

        File fichier = fileChooser.showOpenDialog(window);
        if (fichier != null) {
            try {
                copierDansRepository(fichier);
                ajouterMusique(fichier, racine);
                return true;
            } catch (FileAlreadyExistsException e) {
                if(!ajouterMusique(fichier, racine)){
                    dialogueErreurFichierExistant();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }   
    
    private void dialogueErreurFichierExistant() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Musique déjà importée");
        alert.showAndWait();

    }

    public boolean ajouterMusique(File f, PlayList racine) {
        int taille = (int) f.getName().length() - 4;
        Musique m = new Musique("Artiste inconnu", f.getName().substring(0, taille), ("file:///" + System.getProperty("user.dir").replace("\\", "/") + "/Musiques/" + f.getName().replaceAll(" ", "%20")));
        Media media = new Media(m.getPath());
        media.getMetadata().addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> ch) {
                if (ch.wasAdded()) {
                    m.setAuteur((String) media.getMetadata().get("artist"));
                }
            }
        });

        return racine.ajouter(m);
    }

    public void copierDansRepository(File source) throws Exception {
        String destination = System.getProperty("user.dir") + "/Musiques" + "/" + source.getName();

        Files.copy(source.toPath(), new File(destination).toPath());

    }
    
   

    
}
