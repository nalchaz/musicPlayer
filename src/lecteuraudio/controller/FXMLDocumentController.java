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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import lecteuraudio.metier.IMusique;
import lecteuraudio.metier.IPlayList;
import lecteuraudio.modele.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lecteuraudio.metier.Lecteur;
import lecteuraudio.metier.Manager;
import lecteuraudio.metier.Musique;
import lecteuraudio.metier.NoeudMusique;
import lecteuraudio.metier.PlayList;
import lecteuraudio.metier.IPlayList;
import lecteuraudio.metier.PlayListMusiques;
import lecteuraudio.modele.ManagedDownload;
import lecteuraudio.persistanceBin.BinaryPlayList;

/**
 * Controleur de la fenetre principale
 * @author nachazot1
 */
public class FXMLDocumentController implements Initializable {
    
    
    private ObjectProperty<Manager> manager ;
    public Manager getManager() { return manager.get(); }
    public void setManager(Manager value) {  manager.set(value); }
    public ObjectProperty<Manager> managerProperty() { return manager; }
    
    @FXML
    private TextField nomPlayListAjout;

    @FXML
    private TableView tableView=new TableView<IMusique>();;
    

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
    private Button rechButton;
    
    
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
    private TreeItem<NoeudMusique> rootItem;
    @FXML
    private TreeView<NoeudMusique> treeView;
   
    //Fenetre YouTube
    private Stage youTubeStage;
   
    private ArrayList<NoeudMusique> pressePapier; //Utilisé pour le copié collé
    
    private IPlayList listecourante;
    private PlayListMusiques listemusiques; //Liste de musiques seulement
    
    
    private Lecteur lec=new Lecteur();
    
    private YouTubeFXMLController youTubeController;
    
    public FXMLDocumentController (Manager manager){
        this.manager=new SimpleObjectProperty<>(manager);
    }
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTreeView();
        ObjectBinding<NoeudMusique> ob = Bindings.createObjectBinding(
                () -> treeView.getSelectionModel().getSelectedItem().getValue(), 
            treeView.getSelectionModel().selectedItemProperty());
        selectedNoeudProperty().bind(ob);


        //Initialisation des listes

        listecourante=getManager().getRacine();
        listemusiques=new PlayListMusiques(listecourante); 
        
        //resélectionne le premier item pour mettre a jour l'affichage
        treeView.getSelectionModel().select(null);
        treeView.getSelectionModel().selectFirst();
        
        updateTreeView(rootItem, getManager().getRacine());
        
        initializeTableView();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    
    /*
    * Intitilisation de la zone central, la TableView
    * Donne lesproperty à associer à chaque colonne de la TableView
    * @author nachazot1
    */
    public void initializeTableView(){
        
        ((TableColumn<Musique,String>)tableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory("titre"));
        ((TableColumn<Musique,String>)tableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory("auteur"));
        ((TableColumn<Musique,String>)tableView.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory("duree"));
            
    }
    
    /*
    * Intitilisation du TreeView
    * @author nachazot1
    */
 
    private void initializeTreeView()
    {
        //sélectionne le premier item
        treeView.getSelectionModel().selectFirst();
        //Ajoute le listner sur le noeud séléctionné
        treeView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)->
                treeViewSelectedItemListener(observable, oldValue, newValue));

    }
    
    /*
    * Bindings sur les musiques de la PlayList séléctionné dans le treeView
    * @author nachazot1
    */
    
    private void treeViewSelectedItemListener(Observable observable, 
                                              Object oldValue, Object newValue)
    {
        //récupération de l'élément de la structure composite correspondant
        NoeudMusique noeud = getSelectedItemInTreeView((TreeItem<NoeudMusique>)newValue);
        //Affichage des musiques de la IPlayList dans la zone centrale
        if(noeud instanceof IPlayList){
            tableView.itemsProperty().bind(new PlayListMusiques((IPlayList)noeud).playlistProperty());
        }
    }
    
    /*
    * Return la valeur du treeItem
    * @author nachazot1
    */
    
   
    
    private NoeudMusique getSelectedItemInTreeView(TreeItem<NoeudMusique> item)
    {
        
        TreeItem<NoeudMusique> treeItem = item;

        if(treeItem == null) return null;

        NoeudMusique noeud = treeItem.getValue();
        
        return noeud;
    }
 
    //Met a jour le treeView à partir d'un noeud
    private void updateTreeView(TreeItem<NoeudMusique> item, NoeudMusique noeud) {
        //vide le TreeItem
        item.getChildren().clear();

        //ajoute des TreeItems pour chaque noeud du composite
        if (noeud instanceof IPlayList) {
            
            for (NoeudMusique nm : ((IPlayList) noeud).getListPlayList()) {
                TreeItem<NoeudMusique> noeudItem = new TreeItem<>(nm);
                item.getChildren().add(noeudItem);
                updateTreeView(noeudItem, nm);
            }
        }
    }
    
    /*Désélectionne puis reselectionne pour mettre à jour l'affichage de la zone centrale
    *Puis met à jour l'affichage du treeView
    *
    */
    private void updateLayoutTreeView(TreeItem<NoeudMusique> item, NoeudMusique noeud){

        //Met à jour l'affichage du treeView
        updateTreeView(item, noeud);
        //Met à jour l'affichage de la zone centrale
        treeView.getSelectionModel().select(null);
        treeView.getSelectionModel().select(item);
    }
    
    //methode en attendant d'arriver à utiliser les bindings fxml
    private void bindings() {
        //Binding sur le titre da la musique courante (musiqueView)
        titreMusique.textProperty().bind(getManager().getNoeudCourant().titreProperty());
        if(getManager().getNoeudCourant() instanceof IMusique){
            IMusique m=(IMusique)getManager().getNoeudCourant();
            auteur.textProperty().bind(m.auteurProperty());
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
        //Si la musique est à la fin, jouer la prochaine musique du lecteur
        lec.setOnEndOfMedia(new Runnable() {
            public void run() {
                getManager().setNoeudCourant(lec.next());
                bindings();
            }
        });
    }

    @FXML
    private void importPressed(ActionEvent event) {
        if(getManager().chercherDisqueDur(borderPane.getScene().getWindow())){    
            updateLayoutTreeView(rootItem, getManager().getRacine());
        }
        
    }

    @FXML
    private void previousPressed(ActionEvent event) {
        
        NoeudMusique nm = lec.precedent();
        getManager().setNoeudCourant(nm);
        if(nm==null) return;
        if ("play".equals(play.getId())) {
            play.setId("pause");
        }
        bindings();
    }

    @FXML
    private void nextPressed(ActionEvent event) {
        
        NoeudMusique nm =lec.next();
        if(nm==null) return;
        getManager().setNoeudCourant(nm);
        if ("play".equals(play.getId())) {
            play.setId("pause");
        }
        bindings();
    }

    @FXML
    private void onNoeudMusiqueChoisie(MouseEvent event) {
        NoeudMusique nm=null;
        if(treeView.getSelectionModel().getSelectedItem()!=null)
            nm = treeView.getSelectionModel().getSelectedItem().getValue();

        if (nm != null) {
            if (nm instanceof IMusique) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {  //double clic gauche
                    //Met la musique séléctionné dans musiqueView puis la joue
                    getManager().setNoeudCourant((IMusique) nm);
                    lec.setPlaylist((IPlayList)treeView.getSelectionModel().getSelectedItem().getParent().getValue());
                    lec.setMusiqueCourante(getManager().getNoeudCourant());
                    lec.pause();
                    if ("play".equals(play.getId())) {
                        play.setId("pause");
                    }
                    lec.play(getManager().getNoeudCourant());

                    lec.setVolume(volumeSlider.getValue());
                    bindings();

                }
                else{
                    //Changement de node sur la zone centrale à implémenter
                }
            } else {
                listecourante=(IPlayList) nm;
                listemusiques=new PlayListMusiques(listecourante);
            }
        }
    }

    @FXML
    private void onMusiqueChoisie(MouseEvent event) {

        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {  //double clic gauche
            //Met la musique séléctionné dans musiqueView puis la joue
            getManager().setNoeudCourant((IMusique)tableView.getSelectionModel().getSelectedItem());
            lec.setPlaylist(listecourante);
            lec.setMusiqueCourante(getManager().getNoeudCourant());
            lec.pause();
            if ("play".equals(play.getId())) {
                play.setId("pause");
            }
            lec.play(getManager().getNoeudCourant());

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
     
        if (treeView.getSelectionModel().getSelectedItem().getValue() == getManager().getRacine()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous ne pouvez pas supprimer la racine.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer " + treeView.getSelectionModel().getSelectedItem().getValue().getTitre() + " ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            TreeItem<NoeudMusique> item=treeView.getSelectionModel().getSelectedItem();
            ((IPlayList)item.getParent().getValue()).supprimer(item.getValue()); 
            
            updateLayoutTreeView(item.getParent(), item.getParent().getValue());
        }
    }

    //creerIPlayListDepuisView : validation apres la saisie d'une playlist, la rajoute dans la liste des playlist, affiche une fenetre d'erreur si le titre de la playlist existe deja
    private void creerPlaylistDepuisView() {
        IPlayList p = new BinaryPlayList(new PlayList(nomPlayListAjout.getText()));
        if (!getManager().ajouter(p)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une playlist possède déja le nom \"" + p.getTitre() + "\".");
            alert.showAndWait();
        }
        updateTreeView(rootItem, getManager().getRacine());
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

    public void exit(){
        if(youTubeStage!=null){
            youTubeStage.close();
        }
        Platform.exit();
    }
    
    @FXML
    private void onExit(ActionEvent event) {
        getManager().sauver();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir quitter ?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            exit();
        }

    }

    @FXML
    private void onCopier(ActionEvent event) {
        ObservableList list=tableView.getSelectionModel().getSelectedItems();
        pressePapier=new ArrayList<>(list);
    }

    @FXML
    private void onColler(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        
        if (pressePapier != null) {
            TreeItem<NoeudMusique> playlist=treeView.getSelectionModel().getSelectedItem();
            if(playlist.getValue() instanceof IMusique){
                alert.setContentText("Veuillez sélectionner la playlist dans laquelle coller dans le menu à gauche.");
                alert.showAndWait();
                return;
            }
            for (NoeudMusique nm : pressePapier) {
                if (!((IPlayList)playlist.getValue()).ajouter(nm)) {
                    alert.setContentText("Vous ne pouvez pas ajouter deux fois la même musique dans une playlist.\n"+nm.getTitre()+" existe déja.");
                    alert.showAndWait();
                }
            }
            updateLayoutTreeView(playlist, playlist.getValue());
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
    //private static final DataFormat musiqueDataFormat = new DataFormat("lecteuraudio.modele.IMusique");
    @FXML */
    @FXML
    private void onDragDetected(MouseEvent event) {
        List<IMusique> dragged = (List<IMusique>) tableView.getSelectionModel().getSelectedItems();
        Dragboard dragBoard = tableView.startDragAndDrop(TransferMode.COPY);
        dragBoard.setDragView(new Text(dragged.toString()).snapshot(null, null), event.getX() / 100, event.getY() / 100);
        ClipboardContent content = new ClipboardContent();
        content.putString(dragged.toString());
        dragBoard.setContent(content);

        event.consume();
    }
    


    @FXML
    private void onRech(ActionEvent event) {
        String recherche = zoneRech.getText();
        if (recherche != null && !"".equals(recherche)) {
            tableView.itemsProperty().bind(listemusiques.rechByString(recherche).playlistProperty());
        }
        else
            tableView.itemsProperty().bind(listemusiques.playlistProperty());
    }
    @FXML
    private void onEnter(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
           String recherche = zoneRech.getText();
            if (recherche != null && !"".equals(recherche)) {
                tableView.itemsProperty().bind(listemusiques.rechByString(recherche).playlistProperty());
            } 
            else
                tableView.itemsProperty().bind(listemusiques.playlistProperty());
        }
    }
    @FXML
    private void onYoutube() throws Exception{
        
        youTubeStage=new Stage();
        youTubeStage.setTitle("YouTube");
        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("/lecteuraudio/vue/youTubeFXML.fxml"));
        Parent root = fxmloader.load();
        youTubeController=fxmloader.getController();
        Scene scene = new Scene(root);         
        youTubeStage.setScene(scene);
        //
        youTubeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {

                //Arreter les threads ici
                System.out.println("Stage is closing");
            }
        });       
        youTubeStage.show();
        
        //Récupère le chemin de la musique téléchargé quand il change
        youTubeController.pathDownloadProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal,
                    Object newVal) {
                String pathdownload=youTubeController.getpathDownload();
                File f=new File(pathdownload);
                String debutpath=pathdownload.substring(0, pathdownload.length()-4);
                if(!f.exists()){      
                    try{
                        Files.deleteIfExists(new File(debutpath+".video.mp4").toPath());
                        Files.copy(new File(debutpath+".audio.mp4").toPath(),new File(debutpath+".mp4").toPath());
                        Files.delete(new File(debutpath+".audio.mp4").toPath());
                        
                        

                        pathdownload=debutpath+".mp4";
                    }
                    catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
                getManager().ajouterMusique(new File(pathdownload));
                updateLayoutTreeView(rootItem, getManager().getRacine());
            }
        });
    }
    
   
    
    

}
