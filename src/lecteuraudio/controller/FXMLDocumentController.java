/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lecteuraudio.modele.GestionnaireImport;
import lecteuraudio.modele.GestionnaireRepertoire;
import lecteuraudio.modele.Lecteur;
import lecteuraudio.modele.ListePlayLists;
import lecteuraudio.modele.Musique;
import lecteuraudio.modele.PlayList;
import lecteuraudio.modele.PlayListSimple;

/**
 *
 * @author nachazot1
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button play;
    
    @FXML 
    private TextField nomPlayListAjout; 
    
    @FXML
    private ListView listMusique;
    
    @FXML 
    private ListView listeplaylists; 
    
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
   
    @FXML
    private ListePlayLists liste=ListePlayLists.getInstance();
    @FXML
    private Musique musiqueView;
    
    private PlayList listemusiques;    
    private ListProperty<Musique> listProp;

            
    Lecteur lec=new Lecteur();
    private GestionnaireRepertoire gesRep= new GestionnaireRepertoire(); 
    private GestionnaireImport gesImp= new GestionnaireImport(gesRep);
    @FXML
    private Button muteButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        PlayList tout=new PlayListSimple("Musiques"); 
        liste.ajouterPlayList(tout);
        gesRep.ouverture();
        gesImp.importerRepertoireMusiques(new File(gesRep.getRepositoryPath()),tout);
       
    }    

    
    @FXML
    private void playPressed(ActionEvent event) {
        
        if("play".equals(play.getId())){ 
            if(lec.play()!=null) 
                play.setId("pause");
        }
        else {
            play.setId("play");
            lec.pause();
        }
    }
     
    
    @FXML 
    private void importPressed(ActionEvent event) { 
       gesImp.chercherDisqueDur(liste.getPlayListTout()); 
        
    }

    @FXML
    private void previousPressed(ActionEvent event) {
        if("play".equals(play.getId())){
            play.setId("pause");
        }
        musiqueView=(Musique)lec.precedent();
        titreMusique.textProperty().bind(musiqueView.getTitre());
    }

    @FXML
    private void nextPressed(ActionEvent event) {
        if("play".equals(play.getId())){
            play.setId("pause");
        }
        musiqueView=(Musique)lec.next();
        titreMusique.textProperty().bind(musiqueView.getTitre());
    }
    
 
    @FXML 
    private void onPlayListChoisie (MouseEvent event){ 
        
        
            listemusiques=(PlayList)listeplaylists.getSelectionModel().getSelectedItem(); 
            listProp=listemusiques.playlistProperty();
            listMusique.itemsProperty().bind(listProp);
       
        
        
    }

    @FXML
    private void onMusiqueChoisie(MouseEvent event) {
        if(event.getClickCount()==2){
            musiqueView=(Musique)listMusique.getSelectionModel().getSelectedItem();
            lec.setPlaylist(listemusiques);
            lec.setMusiqueCourante(musiqueView);
            lec.pause();
            if("play".equals(play.getId())){
                play.setId("pause");
            }
            lec.play(musiqueView);
            titreMusique.textProperty().bind(musiqueView.getTitre());
            
        }
    }

    @FXML
    private void onMuteClic(ActionEvent event) {
        if(lec.isMute())
            lec.setMute(false);
        else
            lec.setMute(true);
    }
    
    @FXML 
    private void onAjoutPlayList (ActionEvent event){ 
        nomPlayListAjout.setVisible(true);             
    }
    
    @FXML 
    private void onValidNom (KeyEvent key){ 
            if (key.getCode().equals(KeyCode.ENTER)){
                PlayList p=new PlayListSimple(nomPlayListAjout.getText()); 
                liste.ajouterPlayList(p);
                nomPlayListAjout.clear();
                nomPlayListAjout.setVisible(false); 
            } 
    }
}
