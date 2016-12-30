package lecteuraudio.modele;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lecteuraudio.metier.IMusique;

/**
 *
 * @author nahel
 */

public class ListMusiqueCell extends ListCell<IMusique> {
    
    
    @Override
    public void updateItem(IMusique item, boolean empty){
        super.updateItem(item, empty);
        if(empty)
        {
            setText(null);
            setGraphic(null);
            textProperty().unbind();
        }
        else
        {
            String test=item.getAuteur();
            String test2=item.getTitre();
            setText(item.getTitre()+"   ||   "+item.getAuteur());
            ImageView src = new ImageView();
            src.setImage(new Image("lecteuraudio/vue/musique.png"));
            src.setFitHeight(16);
            src.setFitWidth(16);
            setGraphic(src);
        }

    }
}
