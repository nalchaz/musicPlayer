/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author aldonne
 */
public class PlayListSimple extends PlayList{ 
    
    public PlayListSimple (){ 
    }
    
    public PlayListSimple (String titre){ 
        super.nom=titre; 
    }
    
    public void ajouterMusique(Musique m){ 
        super.ajouter(m); 
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
