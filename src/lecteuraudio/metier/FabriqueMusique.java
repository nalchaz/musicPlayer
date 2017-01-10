/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

import lecteuraudio.modele.IDataManager;
import lecteuraudio.persistanceBin.BinaryDataManager;
import lecteuraudio.persistanceBin.BinaryMusique;

/**
 *
 * @author alexd
 */
public class FabriqueMusique {
    private static IDataManager datamanager; 
    
    public FabriqueMusique(IDataManager m){ 
        datamanager=m; 
    }
    public static IMusique creerMusique (String auteur, String titre, String path){
        Musique m=new Musique(auteur,titre,path); 
        if (datamanager instanceof BinaryDataManager){ 
            return new BinaryMusique(m); 
        }
        else { 
            return m; 
        }
    }
    
    public void setDataManager (IDataManager datamanager){ 
        this.datamanager=datamanager;     
    }
}
