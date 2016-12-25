    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
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
    private WebView webView;
    @FXML
    private TextField urlTextField;
    @FXML
    private Label downloadStatus;
    
    
    
    private final ObjectProperty<WebEngine> webengineProperty = new SimpleObjectProperty<>();
    @FXML
    private ProgressBar downloadProgress;
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
        
        // binding sur l'url
        urlTextField.textProperty().bind(getWebEngine().locationProperty());
    }

    public void DirectDownload(String url) throws Exception {

        String dest = System.getProperty("user.dir") + "/Musiques";

        ManagedDownload managerDownload = new ManagedDownload(url, dest);
        downloadStatus.textProperty().bind(managerDownload.downloadStatusProperty());
        downloadProgress.progressProperty().bind(managerDownload.progressProperty());
        managerDownload.start();
        managerDownload.pathDownloadProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal,
                    Object newVal) {
                cancel.setDisable(false);
                download.setDisable(false);
                downloadProgress.setVisible(false);
                setpathDownload(managerDownload.getpathDownload());
            }
        });
    }

    @FXML
    private void onDownload(ActionEvent event) throws Exception{
        getWebEngine().reload();
        cancel.setDisable(true);
        download.setDisable(true);
        downloadProgress.setVisible(true);
        DirectDownload(getWebEngine().getLocation());
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    
}
