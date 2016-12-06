/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.controller;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.FlowPane;
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
    private TextField nomPlayListAjout; 
    
    @FXML
    private ListView listMusique;
    @FXML 
    private ListView listeplaylists; 
    
    @FXML
    private Button play;
    @FXML   
    private Button previous;
    @FXML
    private Button next;
    @FXML
    private Button muteButton;
    @FXML
    private Button ajoutPlayList;
    
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
    private BorderPane borderPane;
    @FXML
    private FlowPane zoneAjout;
    
    private Musique pressePapier;
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
        zoneAjout.setVisible(true);
        ajoutPlayList.setVisible(false);
    }
    
    @FXML
    private void onAnnulerPlaylist(ActionEvent event) {
        nomPlayListAjout.clear();
        zoneAjout.setVisible(false);
        ajoutPlayList.setVisible(true);
    }
    
    @FXML 
    private void onSupprimerPlayList (ActionEvent event){ 
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer "+listemusiques.getNom()+" ?");

        Optional<ButtonType> result = alert.showAndWait();       
        if (result.get() == ButtonType.OK){
            if(!liste.supprimerPlayList(listemusiques)){
                alert.setAlertType(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Vous ne pouvez pas supprimer la playlist principale.");
                alert.showAndWait();
            }
        }
    }
    
    private void creerPlaylistDepuisView(){
        PlayList p=new PlayList(nomPlayListAjout.getText()); 
        if(!liste.ajouterPlayList(p)){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une playlist possède déja le nom \""+p.getNom()+"\".");
            alert.showAndWait();
        }
        nomPlayListAjout.clear();
        zoneAjout.setVisible(false);
        ajoutPlayList.setVisible(true);
    }
    @FXML 
    private void onValidNom (KeyEvent key){ 
            if (key.getCode().equals(KeyCode.ENTER)){
                creerPlaylistDepuisView();
            } 
    } 
    @FXML
    private void onValidNomButton(ActionEvent event) {
        creerPlaylistDepuisView();
    }
    @FXML 
    private void onExit (ActionEvent event){ 
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir quitter ?");

        Optional<ButtonType> result = alert.showAndWait();       
        if (result.get() == ButtonType.OK){
            Platform.exit();
        } 
        
    }

    @FXML
    private void onCopier(ActionEvent event) {
        pressePapier=(Musique)listMusique.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onColler(ActionEvent event) {
        if(pressePapier!=null)
            if(!listemusiques.ajouter(pressePapier)){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Vous ne pouvez pas ajouter deux fois la même musique dans une playlist.");
                alert.showAndWait();
            }
    }
    
    @FXML
    private void onSuppr(ActionEvent event) {
        
        Musique m=(Musique)listMusique.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer "+m.getTitre()+" ?");

        Optional<ButtonType> result = alert.showAndWait();       
        if (result.get() == ButtonType.OK){
            listemusiques.supprimer(m);
        }
        
    }

    
    
}
