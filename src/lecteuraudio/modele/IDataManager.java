/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import java.io.File;

/**
 *
 * @author alexd
 */
public interface IDataManager {
    public void charger(PlayList racine); 
    public void sauver (PlayList racine); 
}
