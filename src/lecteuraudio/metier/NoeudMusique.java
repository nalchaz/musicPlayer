/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

import java.io.Externalizable;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nahel
 */
public abstract class NoeudMusique implements INoeudMusique{

    protected StringProperty titreProperty=new SimpleStringProperty();
    @Override
    public StringProperty titreProperty(){return titreProperty; }
    @Override
    public String getTitre() {return titreProperty.get();}
    @Override
    public void setTitre(String titre) {this.titreProperty.set(titre); }
    @Override 
    public String toString (){ 
        return getTitre(); 
    }
    
}
