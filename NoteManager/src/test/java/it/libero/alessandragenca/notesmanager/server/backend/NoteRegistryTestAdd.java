package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class NoteRegistryTestAdd {

    NoteRegistry nr = new NoteRegistry();


    @Before
    public void setUp() throws Exception {

        System.out.print("Dimensioni prima dell'add:"+nr.size()+"\n");
        Note n = new Note("Nota di Alessandra", "Testo della nota", new Date());
        nr.add(n);
        System.out.print("Dimensioni dopo dell'add:"+nr.size()+"\n");


    }

    //aggiunta nota valida
    @Test
    public void testAdd1() {
        System.out.println("inizio test1->test per verificare add di una nota valida");
        int size=nr.size();
        System.out.println("dimensioni registro:"+ size);
        Note n= new Note ("Nota Ale", "Testo aaaa bbb ccc", new Date());
        try {

            nr.add(n);
            System.out.println("Nota aggiunta correttamente, dimensioni registro:" + nr.size());

        } catch (InvalidKeyException e) {
            System.err.print(e);
            e.printStackTrace();
            System.out.println("Lanciata eccezione");
        }


        assertNotEquals("dimensioni non modificate",size, nr.size());
        System.out.println("fine test1->test per verificare add di una nota valida");
    }



    //aggiunta nota duplicata
    @Test
    public void testAdd2() {
        System.out.println("inizio test2->aggiunta nota duplicata");
        int size=nr.size();
        System.out.println("dimensioni registro:"+ size);
        Note n= new Note("Nota di Alessandra", "Testo della nota", new Date());

        try {
            nr.add(n);
        } catch (InvalidKeyException e) {
            System.err.println(e);
            System.out.println("Lanciata eccezione per nota duplicata");
            assertTrue("Nota duplicata", true);
        } finally {


            //verifico le dimensioni del realm per verificare che sia stato effettivamente aggiunto l'utente
            assertEquals("dimensioni realm non modificate",size, nr.size());

        }
        System.out.println("fine test2->aggiunta nota duplicata");
    }

}