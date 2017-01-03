 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;


import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lecteuraudio.metier.IMusique;
import lecteuraudio.metier.Musique;
import lecteuraudio.metier.IPlayList;
import lecteuraudio.persistanceBin.BinaryMusique;

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
        if (!new File(repositoryPath+ "/").exists()) {
            new File(repositoryPath + "/").mkdir();
        }
    }
    
 
    public  void importerRepertoireMusiques (IPlayList racine){ 
        for( File f : new File(repositoryPath).listFiles()){ 
           ajouterMusique(f,racine); 
        }
        
    }    
    
    public  boolean chercherDisqueDur (IPlayList racine, Window window) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Music files", "*.mp3", "*.wav", "*.mp4"), 
            new FileChooser.ExtensionFilter("All files", "*.*"),       
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

    public boolean ajouterMusique(File f, IPlayList racine) {
        int taille = (int) f.getName().length() - 4;
        IMusique m = new BinaryMusique(new Musique("Artiste inconnu", f.getName().substring(0, taille), ("file:///" + System.getProperty("user.dir").replace("\\", "/") + "/Musiques/" + f.getName().replaceAll(" ", "%20"))));
        Media media = new Media(m.getPath());

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //Attendre que le mediaplayer soit pret pour récuperer les metadatas
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                
                m.setDuree(Utils.formatTime(media.getDuration()));
                m.setAuteur((String)media.getMetadata().get("artist"));

            }
        });
        

       

        return racine.ajouter(m);
    }

    public void copierDansRepository(File source) throws Exception {
        String destination = System.getProperty("user.dir") + "/Musiques" + "/" + source.getName();

        Files.copy(source.toPath(), new File(destination).toPath());

    }
    
   

    
}
