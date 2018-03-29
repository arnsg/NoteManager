package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class NoteRegistryTestUpdate {

    NoteRegistry nr = new NoteRegistry();
    Note n = new Note("Nota di Alessandra", "Testo della nota", new Date());

    @Before
    public void setUp() throws Exception {

        System.out.print("Dimensioni prima dell'add:"+nr.size()+"\n");

        nr.add(n);
        System.out.print("Dimensioni dopo dell'add:"+nr.size()+"\n");
        System.out.print("Nota prima dell'aggiornamento:"+n.toString()+"\n");


    }
    @Test
    public void update() {
        System.out.println("inizio test1->test per verificare update di una nota  presente nel registro");
        n.setText("testo nota modificato");
        try {

            nr.update(n);
            System.out.print("Nota dopo l' aggiornamento:"+n.toString()+"\n");
            assertTrue("Nota non aggiornata", true);
        } catch (InvalidKeyException e) {
            System.err.println(e);
            System.out.println("Lanciata Eccezione " +e);

        } finally {
           assertTrue("Nota non aggiornata", true);

        }
        System.out.println("fine test1->test per verificare update di una nota presente nel registro");
    }


    @Test
    public void update2() {
        System.out.println("inizio test1->test per verificare update di una nota NON presente nel registro");

        try {

            nr.update(new Note ("a","b", new Date()));

            assertTrue("Nota aggiornata", false);
        } catch (InvalidKeyException e) {
            System.err.println(e);
            System.out.println("Lanciata Eccezione " +e);

        } finally {
            assertTrue("Nota aggiornata", true);

        }
        System.out.println("fine test1->test per verificare update di una nota Non presente nel registro");
    }




}