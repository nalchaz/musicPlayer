/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Window;
import lecteuraudio.modele.GestionnaireImport;
import lecteuraudio.modele.IDataManager;

/**
 *
 * @author alexd
 */
public class Manager {
    
    private GestionnaireImport gesImp; 
    private IDataManager datamanager;  
    
    private ObjectProperty noeudCourant = new SimpleObjectProperty();
    public ObjectProperty noeudCourantProperty() { return noeudCourant;}
    public NoeudMusique getNoeudCourant() { return (NoeudMusique) noeudCourant.get();}
    public void setNoeudCourant(NoeudMusique noeud) { this.noeudCourant.set(noeud);}
    
    public Manager (){ 
        gesImp= new GestionnaireImport();             
    }
    
    public void setDataManager (IDataManager m){ 
        datamanager=m ;
    }
    
    public void charger(PlayList racine){ 
        datamanager.charger(racine);
    }
    
    public void sauver (PlayList racine){ 
        datamanager.sauver(racine);
    }
    
    
    public void ouverture (PlayList racine){ 
        gesImp.ouverture();
        gesImp.importerRepertoireMusiques(racine);    
    }
    
    /*
    *chercherDisqueDur :
    return true si une musique est ajouté, false si rien n'est ajouté
    */
    public boolean chercherDisqueDur(Window window,PlayList racine){
        return gesImp.chercherDisqueDur(racine, window);
    }
    
    public void ajouterMusique(File f, PlayList racine) {
        gesImp.ajouterMusique(f, racine);
    }
    
}
