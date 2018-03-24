package it.libero.alessandragenca.notesmanager.server.web.resources;

import com.google.gson.Gson;
import it.libero.alessandragenca.notesmanager.commons.User1;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.UserRegistryAPI;
import org.junit.*;
import org.restlet.security.MemoryRealm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class UserRegJSONTest {

    static UserRegJSON userRegJson= new UserRegJSON();
    static UserRegistryAPI urapi = UserRegistryAPI.instance();
    static Gson gson=new Gson();
    private static MemoryRealm realm;
    private static String rootDirForWebStaticFiles;

    private class Settings
    {


        public String users_storage_base_dir; // Directory per lo storage degli utenti
        public String users_storage_base_file; // File per lo storage degli utenti
        public String web_base_dir; //directory per le risorse web
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Settings settings = null;


        try
        {
            System.out.print(new File("").getAbsolutePath());
            System.out.print("stampa:"+System.getProperty("user.dir"));
            Scanner scanner = new Scanner(new File ("src/main/resources/settings.json"));
            settings = gson.fromJson(scanner.nextLine(),Settings.class);
            scanner.close();
            System.err.println("Loading settings from file");
        }
        catch (FileNotFoundException e1)
        {
            System.err.println("Settings file not found");
            System.exit(-1);
        }

        rootDirForWebStaticFiles="file:///"+System.getProperty("user.dir")+"/src/main/resources/"+settings.web_base_dir;
        System.err.println("Web Directory: " + rootDirForWebStaticFiles);



        urapi.setStorageFiles(System.getProperty("user.dir") + "/src/main/resources/" + settings.users_storage_base_dir + "\\", settings.users_storage_base_file); // Imposto i file di storage
        realm = urapi.getRealm();
        urapi.restore();


    }

    @Before
    public void setUp() throws Exception {
        char[] pass1 = {'2', '7', '0', '9'};

        User1 utente1 = new User1("AlessandraGenca", pass1);
        userRegJson.addUser(gson.toJson(utente1, User1.class));


        }

    @Test
    public void test() {
        char[] pass1 = {'2', '7', '0', '9'};
        char[] pass2 = {'3','2', '3','2'};
        char[] pass3 = {'3','3', '3','3'};
        User1 utente1 = new User1("AlessandraGenca", pass1);
        User1 utente2 = new User1("UtenteTest", pass2);
        User1 utente3 = new User1("Prova", pass3);




        //aggiunta utente gia' esistente
        String u1S=gson.toJson(utente1,User1.class);
        try{
            gson.fromJson(userRegJson.addUser(u1S),String.class);
            assertTrue("L'utente "+utente1.getIdentifier() +" gia' esiste!", false);
            assertTrue("L'utente "+utente1.getIdentifier()+" esiste, le credenziali sono corrette e dovrebbe autenticarsi!", gson.fromJson(userRegJson.checkUser(gson.toJson(utente1.getIdentifier()+";"+String.copyValueOf(utente1.getSecret()),String.class)), Boolean.class));
        }catch (Exception e) {
            assertTrue("L'utente "+utente1.getIdentifier() +" gia' esiste!", true);
        }

        //aggiunta utente non esistente
        String u2S=gson.toJson(utente2,User1.class);
        try{
            gson.fromJson(userRegJson.addUser(u2S),String.class);
            assertTrue("L'utente "+utente2.getIdentifier() +" non esiste e dovrebbe essere aggiunto", true);


        }catch (Exception e) {
            assertTrue("L'utente "+utente2.getIdentifier() +" non esiste e dovrebbe essere aggiunto!", false);
        }


        System.out.println(realm.getUsers().toString());
        //check utente esistente password corretta


        //check utente esistente password sbagliata
        assertTrue("L'utente "+utente2.getIdentifier() +" esiste, ma le credenziali sono sbagliate e non potrebbe autenticarsi!", !gson.fromJson(userRegJson.checkUser(gson.toJson(utente2.getIdentifier()+";"+"ahbfakshvbadfk",String.class)), Boolean.class));

        //check utente non esistente
        assertTrue("L'utente "+utente3.getIdentifier()+" non esiste e non potrebbe autenticarsi!", !gson.fromJson(userRegJson.checkUser(gson.toJson(utente3.getIdentifier()+";"+ utente3.getSecret().toString(),String.class)), Boolean.class));


    }




    @After
    public void tearDown() throws Exception {
        urapi.remove(realm.findUser("AlessandraGenca"));
        urapi.remove(realm.findUser("UtenteTest"));

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {


    }
}

