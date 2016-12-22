package lecteuraudio.modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeCell;

/**
 *
 * @author nahel
 */

public class TreeItemComponentCell extends TreeCell<NoeudMusique> {
    
    
    @Override
    public void updateItem(NoeudMusique item, boolean empty)
    {
        super.updateItem(item, empty);
        if(empty)
        {
            textProperty().unbind();
            return;
        }
        if(item instanceof Musique)
        {
            textProperty().bind(item.titreProperty());
        }
        if(item instanceof PlayList)
        {
            textProperty().bind(item.titreProperty());
        }  
    }
}
