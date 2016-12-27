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
import lecteuraudio.metier.Manager;
import lecteuraudio.persistanceBin.BinaryDataManager;

/**
 *
 * @author nachazot1
 */
public class LecteurAudio extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("/lecteuraudio/vue/FXMLDocument.fxml"));
        Scene scene = new Scene(root); 
        stage.setTitle("MusicPlayer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}
