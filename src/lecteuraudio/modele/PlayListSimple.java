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
        super.titre=titre; 
    }
    
    public void ajouterMusique(Musique m){ 
        super.ajouter(m); 
    }
   
}
