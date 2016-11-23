/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lecteuraudio.modele.GestionnaireImport;
import lecteuraudio.modele.GestionnaireRepertoire;
import lecteuraudio.modele.Lecteur;
import lecteuraudio.modele.Musique;
import lecteuraudio.modele.MusiqueWav;

/**
 *
 * @author nachazot1
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button play;
    
    Lecteur lec=new Lecteur();
    Musique musique=new MusiqueWav("Auteur", "Titre", "/lecteuraudio/vue/RustedRoot.wav");
  
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GestionnaireRepertoire.ouverture();
        
    
    }    

    @FXML
    private void playPressed(ActionEvent event) {
        if("play".equals(play.getId())){
            
            lec.play(musique);
            
            play.setId("pause");
        }
        else {
            play.setId("play");
            lec.stop();
        }
    } 
    
    @FXML 
    private void importPressed(ActionEvent event) { 
        GestionnaireImport.chercherDisqueDur(); 
    }
    
    
}
