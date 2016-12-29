/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lecteuraudio.controller.FXMLDocumentController;
import lecteuraudio.metier.Manager;
import lecteuraudio.persistanceBin.BinaryDataManager;

/**
 *
 * @author nachazot1
 */
public class LecteurAudio extends Application {
    
    private Manager manager;
    
    private void charger (){ 
        manager=new Manager(); 
        // Pour passer en texte : TextDataManager ici, Musique dans GestionnaireImport, PlayList dans "creerPlayListDepuisView dans controller
        manager.setDataManager(new BinaryDataManager());
        manager.charger();
    }
    
    
   
    
    @Override
    public void start(Stage stage) throws Exception {
        charger(); 
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/lecteuraudio/vue/FXMLDocument.fxml"));                
        fxmlloader.setController(new FXMLDocumentController(manager)); 
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);       
        stage.setTitle("MusicPlayer");
        stage.setScene(scene);
        stage.show();
        
    }
    
    
    @Override
    public void stop()
    {
        manager.sauver();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}
