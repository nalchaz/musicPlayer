/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nachazot1
 */
public class LecteurAudio extends Application {
    
    public static PlayList tout= new PlayList("Tout");
    private ObservableList<Musique> observablePlayList = FXCollections.observableArrayList();
    public static Musique musique;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/lecteuraudio/vue/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        GestionnaireRepertoire.ouverture();
        if(LecteurAudio.tout.getPlayList() != null){
            for(Musique m : LecteurAudio.tout.getPlayList()) {
                observablePlayList.add(m);
            }
            musique=observablePlayList.get(0);
        }
        
        
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
