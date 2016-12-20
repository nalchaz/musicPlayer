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
import lecteuraudio.modele.ListePlayLists;
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
    public void charger(ListePlayLists liste) {
        if (!new File(repositoryPlayLists).exists()) 
            new File(repositoryPlayLists).mkdir(); 
        for (File f : new File(repositoryPlayLists).listFiles()) {
            String nom = f.getName();
            String extension = nom.substring(nom.length() - 3, nom.length());
            if (extension.equals("txt") ) {
                importerPlayList(f, liste);
            }

        }
    }

    
    private void importerPlayList(File f, ListePlayLists liste) {
        String nomMusique;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            PlayList p = new PlayList(br.readLine());
            while ((nomMusique = br.readLine()) != null) {
                ajouterMusiqueAPlayList(p, nomMusique, liste.getPlayListTout());
            }
            liste.ajouterPlayList(p);
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
    public void sauver(ListePlayLists playlists) {
        for (File f : new File(repositoryPlayLists).listFiles()){ 
            f.delete(); 
        }
        for (PlayList p : playlists.getPlayLists()) {
            creerFichierPlayList(p);
        }
    }

    public void creerFichierPlayList(PlayList p) {
        if (!p.getNom().equals("Musiques")) {
            File f = new File(System.getProperty("user.dir")+"/Playlists/" + p.getNom() + ".txt");
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
                System.out.println(p.getNom());
                pw.println(p.getNom());
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


