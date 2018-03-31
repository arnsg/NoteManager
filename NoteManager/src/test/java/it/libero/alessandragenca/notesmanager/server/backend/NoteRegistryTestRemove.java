package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class NoteRegistryTestRemove {

    private NoteRegistry notereg = new NoteRegistry();

    @Before
    public void setUp() throws Exception {
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2018, GregorianCalendar.SEPTEMBER, 27);
        Date data1 = gregorianCalendar1.getTime();
        //GregorianCalendar gregorianCalendar2 = new GregorianCalendar(2018, GregorianCalendar.DECEMBER, 13);
        Date data2 = gregorianCalendar1.getTime();

        Note n= new Note ("Titolo della nota di prova1", "Testo della nota di prova1", data1 );
        notereg.add(n);
        System.out.print("Aggiunta nota1:" + n.getTitle()+"\n");
        Note n2= new Note ("Titolo della nota di prova2", "Testo della nota di prova2", data2);
        notereg.add(n2);
        System.out.print("Aggiunta nota2:" + n2.getTitle()+"\n");
        Note n3= new Note ("Titolo della nota di prova3", "Testo della nota di prova3", data1 );
        notereg.add(n3);
        System.out.print("Aggiunta nota3:" + n3.getTitle()+"\n");
        Note n4= new Note ("Titolo della nota di prova4", "Testo della nota di prova4", data2);
        notereg.add(n4);
        System.out.print("Aggiunta nota4:" + n4.getTitle()+"\n");
    }

    @Test
    public void testRemoveMethod(){
        //remove di una nota esistente
        System.out.println("inizio test-1>test per verificare remove di una nota valida");
        int size = notereg.size();
        System.out.print("Dimensioni del registry antecedente la rimozione: "+notereg.size()+"\n");
        try {
            notereg.remove("Titolo della nota di prova4");
            System.out.print("Dimensioni del registry dopo la rimozione:"+notereg.size()+"\n");

        } catch (InvalidKeyException e) {
            System.err.println(e);
            System.out.print("Lanciata Eccezione");
            assertTrue("Errore nella rimozione dell'utente",true);

        } finally {

            //verifico le dimensioni del realm per verificare che sia stato effettivamente rimossa la nota
            assertNotEquals("dimensioni realm non modificate",size, notereg.size());
        }
        System.out.println("fine test-1>test per verificare remove di una nota valida");


    }


    @Test
    public void testRemoveMethod2(){
        //remove di una nota inesistente

        System.out.println("inizio test-2>test per verificare remove di una nota NON presente");
        int size = notereg.size();
        System.out.print("Dimensioni del registry antecedente la rimozione: "+notereg.size()+"\n");
        try {
            notereg.remove("titolononpresente");
            System.out.print("Dimensioni del registry dopo la rimozione:"+notereg.size()+"\n");

        } catch (InvalidKeyException e) {
            System.err.println(e);
            System.out.print("Lanciata Eccezione"+"\n");
            assertTrue("Errore nella rimozione della nota",true);

        } finally {


            assertEquals("dimensioni  non modificate",size, notereg.size());
        }
        System.out.println("fine test-2>test per verificare remove di una nota NON Presente"+"\n");


    }




}