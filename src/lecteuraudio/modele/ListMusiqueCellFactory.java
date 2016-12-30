package lecteuraudio.modele;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import lecteuraudio.metier.IMusique;

/**
 *
 * @author nahel
 */

public class ListMusiqueCellFactory implements Callback<ListView<IMusique>, ListCell<IMusique>> {
    /** 
     * fabrique une cellule pour la lsite de musiques
     * @param param la liste à mettre à jour
     * @return une cellule avec le bon format
     */
    @Override
    public ListCell<IMusique> call(ListView<IMusique> param) {
        return new ListMusiqueCell();
    }
}