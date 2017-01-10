/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

import lecteuraudio.modele.IDataManager;
import lecteuraudio.persistanceBin.BinaryDataManager;
import lecteuraudio.persistanceBin.BinaryPlayList;

/**
 *
 * @author alexd
 */
public class FabriquePlayList {
    private static IDataManager datamanager; 
    
    public FabriquePlayList(IDataManager m){ 
        datamanager=m; 
    }
    
    //En fonction du DataManager, retourne une playlist ou une binaryPlayList
    public static IPlayList creerPlayList (String titre){
        PlayList p=new PlayList(titre); 
        if (datamanager instanceof BinaryDataManager){ 
            return new BinaryPlayList(p); 
        }
        else { 
            return p; 
        }
    }
    
    public void setDataManager (IDataManager datamanager){ 
        this.datamanager=datamanager;     
    }
}
