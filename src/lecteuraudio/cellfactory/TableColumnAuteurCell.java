import javafx.scene.control.TableCell;
import lecteuraudio.metier.IMusique;

/**
 *
 * @author nahel
 */

public class TableColumnAuteurCell extends TableCell<IMusique, String> {
   
    
    @Override
    public void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if(empty)
        {
            setText(null);
            setGraphic(null);
            textProperty().unbind();
        }
        else
        {
            setText(item);
        }

    }
}
