package lecteuraudio.cellfactory;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lecteuraudio.metier.IMusique;

/**
 *
 * @author nahel
 */

public class TableColumnAuteurCellFactory implements Callback<TableColumn,TableCell<IMusique,String>> {
    /** 
     * fabrique une cellule pour la lsite de musiques
     * @param param la liste à mettre à jour
     * @return une cellule avec le bon format
     */
    @Override
    public TableCell<IMusique,String> call(TableColumn param) {
        return new TableColumnTitreCell();
    }
}
