package it.libero.alessandragenca.notesmanager.server.web.resources;

import com.google.gson.Gson;
import it.libero.alessandragenca.notesmanager.commons.Note;
import it.libero.alessandragenca.notesmanager.server.backend.NoteRegistry;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.NoteRegistryAPI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class NoteRegSizeJSONTest {


    static NoteRegSizeJSON NoteRegSizeJson= new NoteRegSizeJSON();
    static NoteRegJson NoteRegJson= new NoteRegJson();

    static NoteRegistry nr = new NoteRegistry();

    static Gson gson=new Gson();


    class Settings
    {


        public String storage_base_dir; // Directory per lo storage delle note


    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        NoteRegSizeJSONTest.Settings settings = null;

        try
        {

            Scanner scanner = new Scanner(new File("src/main/resources/settings.json"));
            settings = gson.fromJson(scanner.nextLine(),NoteRegSizeJSONTest.Settings.class);
            scanner.close();
            //System.err.println("Loading settings from file");
        }
        catch (FileNotFoundException e1)
        {
            System.err.println("Settings file not found");
            System.exit(-1);
        }



        NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
        nrapi.setStorageFiles(System.getProperty("user.dir") +"/src/main/resources/"+ settings.storage_base_dir + "\\", "Test"); // Imposto i file di storage



        System.out.print("Dimensioni prima dell'add:"+nr.size()+"\n");
        Note n = new Note("Nota di Alessandra", "Testo della nota", new Date());
        nr.add(n);

        System.out.print("Dimensioni dopo dell'add:"+nr.size()+"\n");
        String n1s=gson.toJson(n,Note.class);
        String r1=gson.fromJson(NoteRegJson.addNote(n1s),String.class);
        System.out.print(r1+"\n");
        Note n2 = new Note("Nota2", "Testo della nota2", new Date());
        nr.add(n2);
        System.out.print("Dimensioni dopo dell'add:"+nr.size()+"\n");
        String n2s=gson.toJson(n2,Note.class);
        String r=gson.fromJson(NoteRegJson.addNote(n2s),String.class);
        System.out.print(r+"\n");

        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2018, GregorianCalendar.SEPTEMBER, 28);
        Date data1 = gregorianCalendar1.getTime();
        Note n3 = new Note("Nota3", "Testo della nota3", data1);
        nr.add(n3);
        System.out.print("Dimensioni dopo dell'add:"+nr.size()+"\n");
        String n3s=gson.toJson(n3,Note.class);
        System.out.println(n3s);
        String r3=gson.fromJson(NoteRegJson.addNote(n3s),String.class);
        System.out.print(r3+"\n");




    }




    @Test
    // test con note antecedenti una determinata data
    public void getNotebyDate() {
        System.out.print("Inizio test 1->note antecedenti la data specificata");
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2018, GregorianCalendar.SEPTEMBER, 27);
        Date data1 = gregorianCalendar1.getTime();
        try {
            String []titles=gson.fromJson(NoteRegSizeJson.getNotebyDate(gson.toJson(data1,Date.class)), String[].class);
            System.out.println(titles.length);
            for (int i=0; i<titles.length ; i++) {
                System.out.print("Titolo Nota: "+ titles[i] +"\n");
            }
            assertTrue("Le note non sono state ottenute!", true);

        } catch (Exception e) {
            assertTrue("Le note non sono state ottenute!", false);
        }
    }


    @Test
    // test in cui non ci sono note antecedenti una determinata data
    public void getNotebyDate2() {
        System.out.print("Inizio test 2->non ci sono note antecedenti la data specificata");
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2017, GregorianCalendar.SEPTEMBER, 27);
        Date data1 = gregorianCalendar1.getTime();
        try {
            String []titles=gson.fromJson(NoteRegSizeJson.getNotebyDate(gson.toJson(data1,Date.class)), String[].class);
            System.out.println("numero note: "+titles.length);
            for (int i=0; i<titles.length ; i++) {
                System.out.print("Titolo Nota: "+ titles[i] +"\n");
            }
            assertTrue("Le note non sono state ottenute!", true);

        } catch (Exception e) {
            assertTrue("Le note non sono state ottenute!", false);
        }
    }



    @Test
    // test in cui non ci sono note antecedenti una determinata data
    public void getNotebyDate3() {
        System.out.print("Inizio test 3->Errore inserimento data");

        try {
            gson.fromJson(NoteRegSizeJson.getNotebyDate(gson.toJson("",Date.class)), String[].class);

            assertTrue("Le note sono state ottenute!", false);

        } catch (Exception e) {
            assertTrue("Le note  sono state ottenute!", true);
        }
    }



    @AfterClass
    public static void tearDownAfterClass()  {
        File file = new File("src/main/resources/storage");
        for(File f:file.listFiles()) {
            //System.out.println(f.getName());
            if(f.getName().startsWith("Test")){
                f.delete();
            }
        }

    }
}

