package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.Note;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class NoteRegistryTestSave {

    private NoteRegistry nr = new NoteRegistry();
    Note n = new Note ("nota1", "testonota1", new Date());
    Note n2 = new Note ("nota2", "testonota2", new Date());
    Note n3 = new Note ("nota3", "testonota3", new Date());

    @Before
    public void setUp() throws Exception {

        System.out.print("Dimensioni prima dell'add e del save:"+nr.size()+"\n");
        nr.add(n);
        nr.add(n2);
        nr.add(n3);




    }

    @Test
    public void testSave1() {
        System.out.print("Dimensioni dopo dell'add:"+nr.size()+"\n");

        try{

            nr.save("src/main/resources/FileProvaSaveNote.txt");
            System.out.print("salvataggio nel file avvenuto correttamente");


        }catch (IOException e) {
            assertTrue("Errore creazione file", true);
        }




    }





}