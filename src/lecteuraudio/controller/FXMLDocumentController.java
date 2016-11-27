/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import lecteuraudio.modele.GestionnaireImport;
import lecteuraudio.modele.GestionnaireRepertoire;
import lecteuraudio.modele.Lecteur;
import lecteuraudio.modele.LecteurAudio;
import lecteuraudio.modele.Musique;
import lecteuraudio.modele.MusiqueWav;
import lecteuraudio.modele.PlayList;

/**
 *
 * @author nachazot1
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button play;
    
    @FXML
    private ListView listMusique;
    
    Lecteur lec=new Lecteur(LecteurAudio.tout);
    @FXML
    private Button previous;
    @FXML
    private Button next;
    @FXML
    private Label titreMusique;
    @FXML
    private Label tempsEcoule;
    @FXML
    private Label tempsRestant;
    @FXML
    private Label auteur;
    
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    
    
    @FXML
    private void playPressed(ActionEvent event) {
        
        if("play".equals(play.getId())){
            
            lec.play();
            play.setId("pause");
        }
        else {
            play.setId("play");
            lec.pause();
        }
    }
     
    
    @FXML 
    private void importPressed(ActionEvent event) { 
        GestionnaireImport.chercherDisqueDur(); 
    }

    @FXML
    private void previousPressed(ActionEvent event) {
        
    }

    @FXML
    private void nextPressed(ActionEvent event) {
        if("play".equals(play.getId())){
            play.setId("pause");
        }
        lec.next();
    }
    
    
}
