package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NoteRegistryTestGet {

    private NoteRegistry notereg = new NoteRegistry();


    @Before
    public void setUp() throws Exception {

        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2018, GregorianCalendar.SEPTEMBER, 27);
        Date data1 = gregorianCalendar1.getTime();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar(2018, GregorianCalendar.DECEMBER, 13);
        Date data2 = gregorianCalendar2.getTime();

        Note n= new Note ("Titolo della nota di prova", "Testo della nota di prova", data1 );
        notereg.add(n);
        System.out.print("Aggiunta nota1:" + n.getTitle()+"\n");
        Note n2= new Note ("Titolo della nota di prova2", "Testo della nota di prova2", data2);
        notereg.add(n2);
        System.out.print("Aggiunta nota2:" + n2.getTitle()+"\n");
    }






    //test per verificare get di una nota non valida
    @Test
   public void TestNoteRegistry1() {
        System.out.println("inizio test1->test per verificare get di una nota non presente nel registro");
        Note n=null;

        try {

            n=notereg.get("aaa");
        } catch (InvalidKeyException e) {
            System.err.println(e);
            System.out.println("Lanciata Eccezione " +e);

        } finally {
            assertNull("Nota  non trovata: Get restituisce null", n);

        }
        System.out.println("fine test1->test per verificare get di una nota non presente nel registro");
    }


    //test per verificare get di uan nota  valida
    @Test
    public void TestNoteRegistry2(){
        Note n=null;
        System.out.println("inizio test2->test per verificare get di una nota valida");
        try {

            n=notereg.get("Titolo della nota di prova");
            System.out.print("Get su:" + n.getTitle()+"\n");
        } catch (InvalidKeyException e) {
            System.err.println(e);
            System.out.println("Lanciata eccezione:" + e);

        } finally {
            assertNotNull( "Nota non trovata: Get restituisce null",n);
        }
        System.out.println("fine test2->test per verificare get di una nota valida");

    }


    //test per verificare get di uan nota  valida
    @Test
    public void TestNoteRegistry3(){
        String[] result;
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2018, GregorianCalendar.NOVEMBER, 12);
        Date data1 = gregorianCalendar1.getTime();
        System.out.println("inizio test3->test per verificare get di tutte le note antecedenti una particolare data");
            result=notereg.getNotebyDate(data1);
            System.out.println("Numero note:" + result.length);
            for (int i =0; i<result.length; i++){
                System.out.println("Nota numero " + (i+1) +":"+result[i]);

            }


            assertNotNull( "Note non trovata: Get restituisce null",result);

        System.out.println("fine test3->test per verificare get di tutte le note antecedenti una determinata data");

    }


    //test per verificare get di uan nota  valida
    @Test
    public void TestNoteRegistry4(){
        String[] result;
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2016, GregorianCalendar.NOVEMBER, 12);
        Date data1 = gregorianCalendar1.getTime();
        System.out.println("inizio test4->test per verificare get di tutte le note antecedenti una particolare data");
        result=notereg.getNotebyDate(data1);
        System.out.println("Numero note:" + result.length);
        for (int i =0; i<result.length; i++){
            System.out.println("Nota numero" + (i+1) +":"+result[i]);

        }


        assertNotNull( "Note non trovata: Get restituisce null",result);

        System.out.println("fine test4->test per verificare get di tutte le note antecedenti una determinata data");

    }

    //test per verificare get di tutte le note
    @Test
    public void TestNoteRegistry5(){
        String[] result;

        System.out.println("inizio test5->test per verificare get di tutte le note");
        result=notereg.titles();
        System.out.println("Numero note:" + result.length);
        for (int i =0; i<result.length; i++){
            System.out.println("Nota numero" + (i+1) +":"+result[i]);

        }


        assertNotNull( "Note non trovata: Get restituisce null",result);

        System.out.println("fine test5->test per verificare get di tutte le note");

    }

}