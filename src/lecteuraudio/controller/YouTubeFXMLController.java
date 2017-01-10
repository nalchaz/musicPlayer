    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lecteuraudio.metier.NoeudMusique;
import lecteuraudio.modele.ManagedDownload;

/**
 * FXML Controller class
 *
 * @author nahel
 */
public class YouTubeFXMLController implements Initializable {

    @FXML
    private Button download;
    @FXML
    private Button cancel;
    @FXML
    private Button precedentWeb;
    @FXML
    private Button nextWeb;
    @FXML
    private Button downloadFromNav;
    
    @FXML
    private WebView webView;
    @FXML
    private TextField urlTextField;
    @FXML
    private Label downloadStatus;
    @FXML
    private ProgressBar downloadProgress;
    
    
    private final ObjectProperty<WebEngine> webengineProperty = new SimpleObjectProperty<>();
   
    public WebEngine getWebEngine() { return webengineProperty.get(); }
    public void setWebEngine(WebEngine value) { webengineProperty.set(value); }
    public ObjectProperty<WebEngine> webengineProperty() { return webengineProperty; }
    
    private final StringProperty pathDownloadProperty=new SimpleStringProperty();
    public StringProperty pathDownloadProperty(){return pathDownloadProperty; }
    public String getpathDownload() {return pathDownloadProperty.get();}
    public void setpathDownload(String titre) {this.pathDownloadProperty.set(titre);}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setWebEngine(webView.getEngine());
        getWebEngine().load("https://www.youtube.com/");

        
        getWebEngine().getHistory().currentIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal,
                    Object newVal) {
                precedentWeb.setDisable(false);
            }
        });
       
        // set du premier url
        urlTextField.setText(getWebEngine().getLocation());
        // Binding sur l'url, utilisation du WebHistory car la property location du WebEngine ne marche pas bien
        getWebEngine().getHistory().currentIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal,
                    Object newVal) {
                final WebHistory history=getWebEngine().getHistory();
                int currentIndex=history.getCurrentIndex();
                urlTextField.setText(history.getEntries().get(history.getEntries().size()-1).getUrl());
            }
        });
                
    }


    public void DirectDownload(String url) throws Exception {

        String dest = System.getProperty("user.dir") + "/Musiques";

        ManagedDownload managerDownload = new ManagedDownload(url, dest);
        downloadStatus.textProperty().bind(managerDownload.downloadStatusProperty());
        downloadProgress.progressProperty().bind(managerDownload.progressProperty());
        managerDownload.start();
        //Appelle la méthode changed quand le path du téléchargement est appellé, verifie qu'il n'est pas vide
        managerDownload.pathDownloadProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal,
                    Object newVal) {
                cancel.setDisable(false);
                download.setDisable(false);
                downloadProgress.setVisible(false);
                if(managerDownload.getpathDownload()!=null && !managerDownload.getpathDownload().equals(""))
                    setpathDownload(managerDownload.getpathDownload());
            }
        });
    }

    @FXML
    private void onDownload(ActionEvent event) throws Exception{

        cancel.setDisable(true);
        download.setDisable(true);
        downloadProgress.setVisible(true);
        DirectDownload(urlTextField.getText());

    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
        
    }
  
    @FXML
    private void onPrecedentWeb(ActionEvent event) {
        
        nextWeb.setDisable(false);
        final WebHistory history=getWebEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList=history.getEntries();
        int currentIndex=history.getCurrentIndex();
        
        Platform.runLater(new Runnable() { 
            public void run() { 
                history.go(-1); 
                urlTextField.setText(entryList.get(currentIndex-1).getUrl());
            } 
        });
        
        if(currentIndex<2) Platform.runLater(new Runnable() { public void run() { precedentWeb.setDisable(true); } });
    }

    @FXML
    private void onNextWeb(ActionEvent event) {
        
        
        precedentWeb.setDisable(false);
        final WebHistory history=getWebEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList=history.getEntries();
        int currentIndex=history.getCurrentIndex();
        
        Platform.runLater(new Runnable() { 
            public void run() { 
                history.go(1); 
                urlTextField.setText(entryList.get(currentIndex+1).getUrl());
            } 
        });
        
        Platform.runLater(new Runnable() { public void run() { nextWeb.setDisable(true); } });
        
        
    }

    @FXML
    private void onDownloadFromNav(ActionEvent event) throws Exception {
        
        Desktop.getDesktop().browse(new URI("www.youtubeinmp3.com/fetch/?video="+urlTextField.getText()));
    }
    
}
