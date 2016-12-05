/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 *
 * @author aldonne
 */
public class GestionnaireRepertoire {
    private static final String repositoryPath=System.getProperty("user.dir")+"/Musiques";
    
    
    public String getRepositoryPath (){ 
        return repositoryPath; 
    }
    
    public  void ouverture() { 
        if (!new File(repositoryPath).exists()){
            creerRepertoire();    
        }                      
    }
    
    private  void creerRepertoire() { 
        new File(System.getProperty("user.dir")+"/Musiques").mkdir();
    }
    
    public  void copierDansRepository(File source) { 
       String destination=repositoryPath+"/"+source.getName();
        try{         
            Files.copy(source.toPath(), new File(destination).toPath()); 
        }
        catch(FileAlreadyExistsException e) { 
            dialogueErreurFichierExistant();       
        }
        catch (IOException e){ 
            e.printStackTrace();
        }
    }
    
    private void dialogueErreurFichierExistant (){ 
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setHeight(200);
        dialogStage.setWidth(400);
        Button b=new Button ("Ok");
        b.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
                dialogStage.close();
            }
        });
        VBox vbox = new VBox(new Text("Musique déjà importée"), b);
        vbox.setAlignment(Pos.CENTER);
         
        dialogStage.setScene(new Scene(vbox));
        dialogStage.show();
    }
    
}

