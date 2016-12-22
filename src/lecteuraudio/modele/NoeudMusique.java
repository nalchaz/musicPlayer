/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nahel
 */
public abstract class NoeudMusique {
    
    protected StringProperty titreProperty=new SimpleStringProperty();
    public StringProperty titreProperty(){return titreProperty; }
    public String getTitre() {return titreProperty.get();}
    public void setTitre(String titre) {this.titreProperty.set(titre);}
    
}
