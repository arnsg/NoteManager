package it.libero.alessandragenca.notesmanager.server.backend;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class NoteRegistryTestLoad {


    private NoteRegistry nr = new NoteRegistry();




    @Test
    public void TestLoad1() {


        try{

            nr.load("src/main/resources/FileProvaSaveNote.txt");
            System.out.print("Caricamento file avvenuto\n");
            System.out.print("Dimensione registro"+nr.size()+"\n");
            String[] titles=nr.titles();
            for ( int i=0; i< titles.length ; i++){
                System.out.print("titolo nota " +(i+1)+": "+ titles[i]+"\n");

            }


        }catch (Exception e) {
            assertTrue("Errore gestione file", true);
            e.printStackTrace();


        }


    }

}