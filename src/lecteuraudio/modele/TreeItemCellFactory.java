package lecteuraudio.modele;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import lecteuraudio.metier.NoeudMusique;

/**
 *
 * @author nahel
 */

public class TreeItemCellFactory implements Callback<TreeView<NoeudMusique>, TreeCell<NoeudMusique>> {
    /** 
     * fabrique une cellule pour le TreeView
     * @param param le TreeView à mettre à jour
     * @return une cellule avec le bon format
     */
    @Override
    public TreeCell<NoeudMusique> call(TreeView<NoeudMusique> param) {
        return new TreeItemCell();
    }
}
