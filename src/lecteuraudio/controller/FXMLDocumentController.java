/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import lecteuraudio.modele.GestionnaireImport;
import lecteuraudio.modele.GestionnaireRepertoire;
import lecteuraudio.modele.Lecteur;
import lecteuraudio.modele.ListePlayLists;
import lecteuraudio.modele.Musique;
import lecteuraudio.modele.PlayList;

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
    @FXML
    private Button muteButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button ajoutPlayList;
   
    private PlayList listemusiques; 
    
    Lecteur lec=new Lecteur();
    private GestionnaireRepertoire gesRep= new GestionnaireRepertoire(); 
    private GestionnaireImport gesImp= new GestionnaireImport(gesRep);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        PlayList tout=new PlayList("Musiques"); 
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
        titreMusique.textProperty().bind(musiqueView.titreProperty());
    }

    @FXML
    private void nextPressed(ActionEvent event) {
        if("play".equals(play.getId())){
            play.setId("pause");
        }
        musiqueView=(Musique)lec.next();
        titreMusique.textProperty().bind(musiqueView.titreProperty());
    }
    
 
    @FXML 
    private void onPlayListChoisie (MouseEvent event){ 
        
        if(listeplaylists.getSelectionModel().getSelectedItem()!=null){
            listemusiques=(PlayList)listeplaylists.getSelectionModel().getSelectedItem(); 
            listMusique.itemsProperty().bind(listemusiques.playlistProperty());
        }
        
        
    }

    @FXML
    private void onMusiqueChoisie(MouseEvent event) {
        if(event.getButton()==MouseButton.PRIMARY && event.getClickCount()==2){  //double clic gauche
            musiqueView=(Musique)listMusique.getSelectionModel().getSelectedItem();
            lec.setPlaylist(listemusiques);
            lec.setMusiqueCourante(musiqueView);
            lec.pause();
            if("play".equals(play.getId())){
                play.setId("pause");
            }
            lec.play(musiqueView);
            titreMusique.textProperty().bind(musiqueView.titreProperty());
            
        }
        if(event.getButton()==MouseButton.SECONDARY){ //clic droit
         
            ContextMenu contextMenu = new ContextMenu();
            MenuItem item1 = new MenuItem("Copier");
            item1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                 System.out.println("Copier");
            }
            });
            MenuItem item2 = new MenuItem("Coller");
            item2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("Coller");
            }
            });
            contextMenu.getItems().addAll(item1, item2);
            contextMenu.show(borderPane, event.getScreenX(), event.getScreenY());
            //contextMenu.setAutoHide(true);   TROUVER COMMENT FERME LE MENU CONTEXTUEL
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
                PlayList p=new PlayList(nomPlayListAjout.getText()); 
                liste.ajouterPlayList(p);
                nomPlayListAjout.clear();
                nomPlayListAjout.setVisible(false); 
            } 
    }
    
    @FXML 
    private void onSupprimerPlayList (ActionEvent event){ 
        liste.supprimerPlayList(listemusiques);
    }
    
    @FXML 
    private void onExit (ActionEvent event){ 
        Platform.exit();
    }
}
