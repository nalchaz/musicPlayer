/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

import java.io.File;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Window;
import lecteuraudio.modele.GestionnaireImport;
import lecteuraudio.modele.IDataManager;
import lecteuraudio.persistanceBin.BinaryPlayList;

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
    

    private ObjectProperty<IPlayList> racine=new SimpleObjectProperty<>(new BinaryPlayList(new PlayList("Racine")));
    public ObjectProperty<IPlayList> racineProperty (){  return racine;   }
    public IPlayList getRacine (){return racine.get(); }
        
    
    public Manager (){ 
        gesImp= new GestionnaireImport(); 
    }
    
    public void setDataManager (IDataManager m){ 
        datamanager=m;
    }
    
    public void charger(){ 
        gesImp.ouverture();
        //gesImp.importerRepertoireMusiques(getRacine());  
        datamanager.charger(getRacine());
    }
    
    public void sauver(){  
        datamanager.sauver(getRacine());
    }
    
    public void suppression(List<IMusique> m){
        //On supprime la/les musique dans toutes les playlists 
        for (IPlayList p : getRacine().getListPlayList()){ 
            p.getPlayList().removeAll(m); 
        }
        //On supprime la/les musiques dans le dossier Musiques de l'appli
        gesImp.suppression(m);
    }
    
    
    public boolean ajouter(NoeudMusique nm){ 
        return getRacine().ajouter(nm); 
    }
    
    /*
    *chercherDisqueDur :
    return true si une musique est ajouté, false si rien n'est ajouté
    */
    public boolean chercherDisqueDur(Window window){
        return gesImp.chercherDisqueDur(getRacine(), window);
    }
    
    public void ajouterMusique(File f) {
        gesImp.ajouterMusique(f, getRacine());
    }
    
}
