package it.libero.alessandragenca.notesmanager.server.web.resources;

import com.google.gson.Gson;
import it.libero.alessandragenca.notesmanager.commons.Note;
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

public class NoteRegJsonTest {

    static NoteRegJson NoteRegJson= new NoteRegJson();

    static Gson gson=new Gson();
    static GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2018, GregorianCalendar.SEPTEMBER, 8);
    static Date data = gregorianCalendar1.getTime();
    static Note n= new Note ("Titolo della nota di prova", "Testo della nota di prova", data);

    class Settings
    {


        public String storage_base_dir; // Directory per lo storage delle note


    }

    @AfterClass
    public static void tearDownAfterClass() {
        File file = new File("src/main/resources/storage");
        if(file.exists()){
            File[] listFiles=file.listFiles();

            if(listFiles!=null){
                for(File f:listFiles) {
                    //System.out.println(f.getName());
                    if (f.getName().startsWith("Test")) {
                        f.deleteOnExit();
                    }
                }
            }
        }

    }



    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        Settings settings = null;

        try
        {

            Scanner scanner = new Scanner(new File ("src/main/resources/settings.json"),"UTF-8");
            settings = gson.fromJson(scanner.nextLine(),NoteRegJsonTest.Settings.class);
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
        String n1s=gson.toJson(n,Note.class);
        gson.fromJson(NoteRegJson.addNote(n1s),String.class);
        System.out.print("Aggiunta nota1:" + n.getTitle()+"\n");



    }


    // test per l'aggiunta di una nota già presente nel registro
    @Test
    public void testAdd1() {

        //aggiunta Nota duplicata
        String n1s=gson.toJson(n,Note.class);

        try{
            gson.fromJson(NoteRegJson.addNote(n1s),String.class);
            assertTrue("La nota "+n.getTitle() +" gia' esiste!", false);

        }catch (Exception e) {
            assertTrue("La nota "+n.getTitle() +" gia' esiste!", true);
        }

    }

    // test per l'aggiunta di una nota non presente in registro
    @Test
    public void testAdd2() {


        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2018, GregorianCalendar.SEPTEMBER, 10);
        Date data = gregorianCalendar1.getTime();
        Note n2 = new Note("Titolo della nota di prova2", "Testo della nota di prova2", data);
        //aggiunta Nota  Non duplicata
        String n1s = gson.toJson(n2, Note.class);

        try {
            gson.fromJson(NoteRegJson.addNote(n1s), String.class);
            assertTrue("La nota NON esiste e dovrebbe essere aggiunta!", true);

        } catch (Exception e) {
            assertTrue("La nota NON esiste e dovrebbe essere aggiunta!", false);
        }

    }


    // test per l'ottenimento di tutte le note
    @Test
    public void testGetAllNotes() {


        try {
            String []titles=gson.fromJson(NoteRegJson.getTitles(), String[].class);
            for (int i=0; i<titles.length ; i++) {
                System.out.print("Titolo Nota: "+ titles[i] +"\n");
            }
            assertTrue("Le note non sono state ottenute!", true);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Le note non sono state ottenute!", false);
        }
    }



    // test per verificare l'update di una nota presente nel registro
    @Test
    public void testUpdate1() {


        Note n2 = new Note("Titolo della nota di prova2", "Testo della nota di prova2 modificato", new Date());
        //aggiunta Nota  Non duplicata
        String n1s = gson.toJson(n2, Note.class);

        try {
            gson.fromJson(NoteRegJson.updateNote(n1s), String.class);


        } catch (Exception e) {
            assertTrue("La nota NON è stata modificata!", false);
        }

    }

    // test per verificare l'update di una nota non presente in registro
    @Test
    public void testUpdate2() {


        Note n2 = new Note("aa", "Testo", new Date());
        String n1s = gson.toJson(n2, Note.class);

        try {
            gson.fromJson(NoteRegJson.updateNote(n1s), String.class);



        } catch (Exception e) {
            assertTrue("La nota  è stata modificata!", true);
        }

    }

    //test per cancellare tutte le note
    @Test
    public void testDeleteAll() {

        try {
            gson.fromJson(NoteRegJson.deleteAll(), String.class);


        } catch (Exception e) {
            assertTrue("Le note non sono state eliminate!", false);
        }

    }

}