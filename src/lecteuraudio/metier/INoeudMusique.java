/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

import javafx.beans.property.StringProperty;

/**
 *
 * @author alexd
 */
public interface INoeudMusique {
        public String getTitre(); 
        public void setTitre(String titre);
        public StringProperty titreProperty();
}
