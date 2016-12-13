/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import javafx.stage.Window;

/**
 *
 * @author alexd
 */
public class Manager {
    private GestionnaireImport gesImp; 
    private Lecteur lec; 
    private IDataManager datamanager; 
    
    public Manager (){ 
        gesImp= new GestionnaireImport(); 
        lec=new Lecteur(); 
        
        
    }
    
    public void setDataManager (IDataManager m){ 
        datamanager=m ;
    }
    
    public void charger(ListePlayLists liste){ 
        datamanager.charger(liste);
    }
    
    public void sauver (ListePlayLists liste){ 
        datamanager.sauver(liste);
    }
    
    
    public void ouverture (ListePlayLists liste){ 
        gesImp.ouverture();
        gesImp.importerRepertoireMusiques(liste.getPlayListTout());    
    }
    
    public Lecteur getLecteur (){ 
        return lec; 
    }
    
    public void chercherDisqueDur(Window window,ListePlayLists liste){
        gesImp.chercherDisqueDur(liste.getPlayListTout(), window);
    }
    

    
}
