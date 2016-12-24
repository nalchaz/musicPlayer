/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.metier;

/**
 *
 * @author alexd
 */
public interface IMusique extends INoeudMusique{
    public String getAuteur();
    public String getPath(); 
    public void setPath(String path);
    public void setAuteur(String auteur); 
}
