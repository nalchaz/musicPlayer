/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.persistancetexte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import lecteuraudio.modele.IDataManager;
import lecteuraudio.modele.Musique;
import lecteuraudio.modele.NoeudMusique;
import lecteuraudio.modele.PlayList;

/**
 *
 * @author alexd
 */
public class TextDataManager implements IDataManager {
    private String repositoryPlayLists=System.getProperty("user.dir")+"/Playlists"; 
    
    
    @Override
    public void charger(PlayList racine) {
        if (!new File(repositoryPlayLists).exists()) 
            new File(repositoryPlayLists).mkdir(); 
        for (File f : new File(repositoryPlayLists).listFiles()) {
            String nom = f.getName();
            String extension = nom.substring(nom.length() - 3, nom.length());
            if (extension.equals("txt") ) {
                importerPlayList(f, racine);
            }

        }
    }

    
    private void importerPlayList(File f, PlayList racine) {
        String nomMusique;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            PlayList p = new PlayList(br.readLine());
            while ((nomMusique = br.readLine()) != null) {
                ajouterMusiqueAPlayList(p, nomMusique, racine);
            }
            racine.ajouter(p);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ajouterMusiqueAPlayList(PlayList p, String nom, PlayList tout) {
        for (NoeudMusique m : tout.getPlayList()) {
            if (m.getTitre().equals(nom)) {
                p.ajouter(m);
            }
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

    public void creerFichierPlayList(PlayList p) {
        if (!p.getTitre().equals("Musiques")) {
            File f = new File(System.getProperty("user.dir")+"/Playlists/" + p.getTitre() + ".txt");
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
                System.out.println(p.getTitre());
                pw.println(p.getTitre());
                for (NoeudMusique m : p.getPlayList()) {
                    pw.println(m.getTitre());
                }
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
}


