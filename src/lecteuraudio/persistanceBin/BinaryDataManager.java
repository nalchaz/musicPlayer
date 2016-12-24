/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.persistanceBin;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lecteuraudio.metier.PlayList;
import lecteuraudio.modele.IDataManager;

/**
 *
 * @author alexd
 */
public class BinaryDataManager implements IDataManager{
    private String repositoryPlayLists=System.getProperty("user.dir")+"/Playlists"; 
    private File f= new File (repositoryPlayLists); 
    // TOUTES LES PLAYLISTS VONT DEVENIR DES IPLAYLISTS
    @Override
    public void charger(PlayList racine) {
        if (!new File(repositoryPlayLists).exists()) 
            new File(repositoryPlayLists).mkdir(); 
        for (File f : new File(repositoryPlayLists).listFiles()) {
            String nom = f.getName();
            String extension = nom.substring(nom.length() - 3, nom.length());
            if (extension.equals("bin") ) {
                importerPlayList(f, racine);
            }

        }
        
    }
    
    private void importerPlayList (File f,PlayList racine){ 
        try (ObjectInputStream reader=new ObjectInputStream(new FileInputStream (f))){
            PlayList p=(PlayList) reader.readObject(); 
            racine.ajouter(p); 
        }
        catch (Exception e){ 
            e.printStackTrace();
        }
    }

    @Override
    public void sauver(PlayList racine) {
        for (File f : new File(repositoryPlayLists).listFiles()){ 
            f.delete(); 
        }
        for (PlayList p : racine.getListPlayList()) {
            creerFichierPlayList(p);
        }
    }
    
    private void creerFichierPlayList (PlayList p){ 
        File f=new File (repositoryPlayLists+"/"+p.getTitre()+".bin"); 
        try (ObjectOutputStream writer=new ObjectOutputStream( new FileOutputStream(f))){
            writer.writeObject(p); 
        }
        catch (Exception e){ 
            e.printStackTrace();
        }
    }
    
}
