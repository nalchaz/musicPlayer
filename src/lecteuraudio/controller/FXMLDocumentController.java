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
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lecteuraudio.modele.GestionnaireImport;
import lecteuraudio.modele.GestionnaireRepertoire;
import lecteuraudio.modele.Lecteur;
import lecteuraudio.modele.ListePlayLists;
import lecteuraudio.modele.Musique;
import lecteuraudio.modele.PlayList;
import lecteuraudio.modele.Utils;

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
    
    @FXML
    private ProgressBar progressbar;
    @FXML
    private Slider volumeSlider;
    
    private Musique pressePapier;
    private PlayList listemusiques; 
    
    private GestionnaireRepertoire gesRep= new GestionnaireRepertoire(); 
    private GestionnaireImport gesImp= new GestionnaireImport(gesRep);
    
    Lecteur lec = new Lecteur();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Création de la playlist principale toujours présente
        PlayList tout = new PlayList("Musiques");
        liste.ajouterPlayList(tout);

        //Importation des musiques
        gesRep.ouverture();
        gesImp.importerRepertoireMusiques(new File(gesRep.getRepositoryPath()), tout);

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
        if ("play".equals(play.getId())) {
            play.setId("pause");
        }
        musiqueView = (Musique) lec.next();
        titreMusique.textProperty().bind(musiqueView.titreProperty());
    }

    @FXML
    private void onPlayListChoisie(MouseEvent event) {

        if (listeplaylists.getSelectionModel().getSelectedItem() != null) {
            listemusiques = (PlayList) listeplaylists.getSelectionModel().getSelectedItem();
            listMusique.itemsProperty().bind(listemusiques.playlistProperty());
        }

    }

    @FXML
    private void onMusiqueChoisie(MouseEvent event) {

        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {  //double clic gauche
            //Met la musique séléctionné dans musiqueView puis la joue
            musiqueView = (Musique) listMusique.getSelectionModel().getSelectedItem();
            lec.setPlaylist(listemusiques);
            lec.setMusiqueCourante(musiqueView);
            lec.pause();
            if ("play".equals(play.getId())) {
                play.setId("pause");
            }
            lec.play(musiqueView);

            MediaPlayer mediaplay = lec.getMediaPlayer();

            mediaplay.setVolume(volumeSlider.getValue());

            //Binding du Slider sur le volume du Lecteur
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    if (volumeSlider.isValueChanging()) {
                        mediaplay.setVolume(volumeSlider.getValue() / 200.0);
                    }
                }
            });

            //Binding des labels tempsEcoule, tempsRestant sur les valeur du mediaPlayer, ainsi que le binding de la progressBar
            mediaplay.currentTimeProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue o, Object oldVal, Object newVal) {
                    Duration current = mediaplay.getCurrentTime();
                    Duration duration = mediaplay.getTotalDuration();
                    tempsEcoule.textProperty().bind(new SimpleStringProperty(Utils.formatTime(current)));
                    tempsRestant.textProperty().bind(new SimpleStringProperty(Utils.formatTime(duration)));
                    progressbar.setProgress((current.toSeconds() / duration.toSeconds()));

                }
            });

            //Binding sur le titre da la musique courante (musiqueView)
            titreMusique.textProperty().bind(musiqueView.titreProperty());

            //Si la musique est a la fin, jouer la prochaine musique du lecteur
            lec.getMediaPlayer().setOnEndOfMedia(new Runnable() {
                public void run() {
                    musiqueView = lec.next();
                    titreMusique.textProperty().bind(musiqueView.titreProperty());
                }
            });

        }
    }

    @FXML
    private void playPressed(ActionEvent event
    ) {

        if ("play".equals(play.getId())) {
            if (lec.play() != null) {
                play.setId("pause");
            }
        } else {
            play.setId("play");
            lec.pause();
        }

    }


    //onMuteClic : mute si le lecteur est unmute, unmute si le lecteur est mute
    @FXML
    private void onMuteClic(ActionEvent event) {
        if (lec.getMediaPlayer() != null) {
            if (lec.isMute()) {
                lec.setMute(false);
                muteButton.setId("mute");
            } else {
                lec.setMute(true);
                muteButton.setId("unmute");
            }
        }
    }

    //onAjoutPlayList : affiche la zone permettant de saisir une nouvelle playlist à ajouter 
    @FXML 
    private void onAjoutPlayList (ActionEvent event){ 
        zoneAjout.setVisible(true);
        ajoutPlayList.setVisible(false);
    }
    
    //onAnnulerPlaylist : annule l'ajout d'une playlist, cache la zone permettant de saisir une nouvelle playlist à ajouter 
    @FXML
    private void onAnnulerPlaylist(ActionEvent event) {
        nomPlayListAjout.clear();
        zoneAjout.setVisible(false);
        ajoutPlayList.setVisible(true);
    }
    
    //onSupprimerPlayList : Supprime une playlist, affiche une fenetre de dialogue permettant de confirmer et une fenetre d'erreur si il s'agit de la playlist principale
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
    
    //creerPlayListDepuisView : validation apres la saisie d'une playlist, la rajoute dans la liste des playlist, affiche une fenetre d'erreur si le titre de la playlist existe deja
    
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
    private void onValidNom(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
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

    @FXML
    private void OnProgressBar(MouseEvent event) {
        MediaPlayer media = lec.getMediaPlayer();
        if (progressbar != null && media != null) {
            double d = event.getX();
            Duration newDuration = new Duration(event.getX() / progressbar.getWidth() * media.getTotalDuration().toMillis());
            media.seek(newDuration);
            progressbar.setProgress(event.getX() / progressbar.getWidth());
        }
    }   
    
}
