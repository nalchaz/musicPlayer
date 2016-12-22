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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lecteuraudio.modele.Musique;
import lecteuraudio.modele.PlayList;
import lecteuraudio.modele.Utils;
import java.awt.Desktop;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lecteuraudio.modele.Lecteur;
import lecteuraudio.modele.Manager;
import lecteuraudio.modele.NoeudMusique;
import lecteuraudio.persistancetexte.TextDataManager;

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
    private BorderPane borderPane;
    @FXML
    private FlowPane zoneAjout;

    @FXML
    private ProgressBar progressbar;
    @FXML
    private Slider volumeSlider;

    //Noeud sélectionné dans la TreeView
    private final ObjectProperty<NoeudMusique> selectedNoeud = new SimpleObjectProperty<>();
    public NoeudMusique getSelectedNoeud() { return selectedNoeud.get(); }
    public void setSelectedNoeud(NoeudMusique value) { selectedNoeud.set(value); }
    public ObjectProperty<NoeudMusique> selectedNoeudProperty() { return selectedNoeud; }
   
    
    @FXML
    private TextField zoneRech;
    @FXML
    private Button rechButton;
    
    @FXML
    private PlayList racine;
    @FXML
    private TreeItem<NoeudMusique> rootItem;
    @FXML
    private TreeView<NoeudMusique> treeView;
    
   
    private NoeudMusique pressePapier; //Utilisé pour le copié collé
    
    private PlayList listemusiques;
    
    private Manager manager;
    
    private Lecteur lec=new Lecteur();
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = new Manager(); 
        manager.setDataManager(new TextDataManager()); 
        //Importation des musiques 
        manager.ouverture(racine);      
        //Importation des playlists
        manager.charger(racine); 

        
        initializeTreeView();
        ObjectBinding<NoeudMusique> ob = Bindings.createObjectBinding(
                () -> treeView.getSelectionModel().getSelectedItem().getValue(), 
            treeView.getSelectionModel().selectedItemProperty());
        selectedNoeudProperty().bind(ob);
    }
    
    private void initializeTreeView()
    {
        treeView=new TreeView<>(rootItem);
        //sélectionne le premier item
        treeView.getSelectionModel().selectFirst();

        treeView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)->
                treeViewSelectedItemListener(observable, oldValue, newValue));

    }
    
    private void treeViewSelectedItemListener(Observable observable, 
                                              Object oldValue, Object newValue)
    {
        //récupération de l'élément de la structure composite correspondant
        NoeudMusique noeud = getSelectedItemInTreeView((TreeItem<NoeudMusique>)newValue);
        
        
    }
    
    private NoeudMusique getSelectedItemInTreeView(TreeItem<NoeudMusique> item)
    {
        
        TreeItem<NoeudMusique> treeItem = item;

        if(treeItem == null) return null;

        NoeudMusique noeud = treeItem.getValue();
        
        return noeud;
    }
 
    
    //methode en attendant d'arriver a utiliser les bindings fxml
    private void bindings() {
        //Binding sur le titre da la musique courante (musiqueView)
        titreMusique.textProperty().bind(manager.getNoeudCourant().titreProperty());
        if(manager.getNoeudCourant() instanceof Musique){
            Musique m=(Musique)manager.getNoeudCourant();
            auteur.textProperty().bind(m.auteurProperty());
        }
        else{
            auteur.textProperty().set("");
        }
        //Binding du Slider sur le volume du Lecteur
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    lec.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });

        //Binding des labels tempsEcoule, tempsRestant sur les valeur du mediaPlayer, ainsi que le binding de la progressBar
        lec.currentTimeProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                Duration current = lec.getCurrentTime();
                Duration duration = lec.getTotalDuration();
                tempsEcoule.textProperty().bind(new SimpleStringProperty(Utils.formatTime(current)));
                tempsRestant.textProperty().bind(new SimpleStringProperty(Utils.formatTime(duration)));
                progressbar.setProgress((current.toSeconds() / duration.toSeconds()));

            }
        });
        //Si la musique est a la fin, jouer la prochaine musique du lecteur
        lec.setOnEndOfMedia(new Runnable() {
            public void run() {
                manager.setNoeudCourant(lec.next());
                bindings();
            }
        });
    }

    @FXML
    private void importPressed(ActionEvent event) {
        manager.chercherDisqueDur(borderPane.getScene().getWindow(), racine);

    }

    @FXML
    private void previousPressed(ActionEvent event) {
        if ("play".equals(play.getId())) {
            play.setId("pause");
        }
        manager.setNoeudCourant(lec.precedent());
        bindings();
    }

    @FXML
    private void nextPressed(ActionEvent event) {
        if ("play".equals(play.getId())) {
            play.setId("pause");
        }
        manager.setNoeudCourant(lec.next());
        bindings();
    }

    @FXML
    private void onNoeudMusiqueChoisie(MouseEvent event) {
        NoeudMusique nm = treeView.getSelectionModel().getSelectedItem().getValue();
        if (nm != null) {
            if (nm instanceof Musique) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {  //double clic gauche
                    //Met la musique séléctionné dans musiqueView puis la joue
                    manager.setNoeudCourant((Musique) listMusique.getSelectionModel().getSelectedItem());
                    lec.setPlaylist(listemusiques);
                    lec.setMusiqueCourante(manager.getNoeudCourant());
                    lec.pause();
                    if ("play".equals(play.getId())) {
                        play.setId("pause");
                    }
                    lec.play(manager.getNoeudCourant());

                    lec.setVolume(volumeSlider.getValue());
                    bindings();

                }
            } else {
                listemusiques = (PlayList) nm;
                listMusique.itemsProperty().bind(listemusiques.playlistProperty());
            }
        }

    }

    @FXML
    private void onMusiqueChoisie(MouseEvent event) {

        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {  //double clic gauche
            //Met la musique séléctionné dans musiqueView puis la joue
            manager.setNoeudCourant((Musique)listMusique.getSelectionModel().getSelectedItem());
            lec.setPlaylist(listemusiques);
            lec.setMusiqueCourante(manager.getNoeudCourant());
            lec.pause();
            if ("play".equals(play.getId())) {
                play.setId("pause");
            }
            lec.play(manager.getNoeudCourant());

            lec.setVolume(volumeSlider.getValue());
            bindings();

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
        if (!lec.isNull()) {
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
    private void onAjoutPlayList(ActionEvent event) {
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

    //onSupprimerNoeudMusique : Supprime un noeud, affiche une fenetre de dialogue permettant de confirmer
    @FXML
    private void onSupprimerNoeudMusique(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer " + treeView.getSelectionModel().getSelectedItem().getValue().getTitre() + " ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            racine.supprimer(treeView.getSelectionModel().getSelectedItem().getValue());   
        }
        listMusique.itemsProperty().bind(racine.playlistProperty());
    }

    //creerPlayListDepuisView : validation apres la saisie d'une playlist, la rajoute dans la liste des playlist, affiche une fenetre d'erreur si le titre de la playlist existe deja
    private void creerPlaylistDepuisView() {
        PlayList p = new PlayList(nomPlayListAjout.getText());
        if (!racine.ajouter(p)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une playlist possède déja le nom \"" + p.getTitre() + "\".");
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
    private void onExit(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir quitter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            manager.sauver(racine);
            Platform.exit();
        }

    }

    @FXML
    private void onCopier(ActionEvent event) {
        pressePapier = (NoeudMusique)listMusique.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onColler(ActionEvent event) {
        if (pressePapier != null) {
            if (!listemusiques.ajouter(pressePapier)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Vous ne pouvez pas ajouter deux fois la même musique dans une playlist.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onSuppr(ActionEvent event) {

        NoeudMusique m = (NoeudMusique) listMusique.getSelectionModel().getSelectedItem();
        if (m != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer " + m.getTitre() + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                listemusiques.supprimer(m);

            }
        }

    }

    @FXML
    private void OnProgressBar(MouseEvent event) {
        if (progressbar != null && !lec.isNull()) {
            double d = event.getX();
            Duration newDuration = new Duration(event.getX() / progressbar.getWidth() * lec.getTotalDuration().toMillis());
            lec.seek(newDuration);
            progressbar.setProgress(event.getX() / progressbar.getWidth());
        }
    }
    
/*
    //private static final DataFormat musiqueDataFormat = new DataFormat("lecteuraudio.modele.Musique");
    @FXML
    private void onDragDetected(MouseEvent event) {
        Musique dragged = (Musique) listMusique.getSelectionModel().getSelectedItem();
        Dragboard dragBoard = listMusique.startDragAndDrop(TransferMode.COPY);
        dragBoard.setDragView(new Text(dragged.toString()).snapshot(null, null), event.getX() / 100, event.getY() / 100);
        ClipboardContent content = new ClipboardContent();
        content.putString(dragged.toString());
        //content.put(musiqueDataFormat,dragged);
        dragBoard.setContent(content);

        event.consume();
    }

    private void onDragOverListPlayList(DragEvent event) {
        Dragboard db = event.getDragboard();
        event.acceptTransferModes(TransferMode.COPY);
        event.consume();
    }

    private void onDragDroppedListPlayList(DragEvent event) {
        Dragboard db = event.getDragboard();

        //Musique m=(Musique)db.getContent(musiqueDataFormat);
        //PlayList tmp=(PlayList)event.getGestureTarget();
        event.setDropCompleted(true);
        event.consume();
    }
*/
    @FXML
    private void onRech(ActionEvent event) {
        String recherche = zoneRech.getText();
        if (recherche != null && !"".equals(recherche)) {
            listMusique.itemsProperty().bind(listemusiques.rechByString(recherche).playlistProperty());
        }
    }

    @FXML
    private void onYoutube() {
        try {
            Desktop.getDesktop().browse(new URL("http://youtube.fr").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onDragOverTree(DragEvent event) {
    }
    @FXML
    private void onDragDroppedTree(DragEvent event) {
    }
    @FXML
    private void onDragDetected(MouseEvent event) {
    
    }
}
