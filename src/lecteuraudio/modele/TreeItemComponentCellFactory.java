package lecteuraudio.modele;

import lecteuraudio.metier.NoeudMusique;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 *
 * @author nahel
 */

public class TreeItemComponentCellFactory implements Callback<TreeView<NoeudMusique>, TreeCell<NoeudMusique>> {
    /** 
     * fabrique une cellule pour le TreeView
     * @param param le TreeView à mettre à jour
     * @return une cellule avec le bon format
     */
    @Override
    public TreeCell<NoeudMusique> call(TreeView<NoeudMusique> param) {
        return new TreeItemComponentCell();
    }
}
