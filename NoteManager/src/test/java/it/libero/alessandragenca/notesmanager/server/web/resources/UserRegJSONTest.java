package it.libero.alessandragenca.notesmanager.server.web.resources;

import com.google.gson.Gson;
import it.libero.alessandragenca.notesmanager.commons.User1;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.UserRegistryAPI;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.restlet.security.MemoryRealm;
import org.restlet.security.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserRegJSONTest {

    static UserRegJSON userRegJson= new UserRegJSON();
    static MemoryRealm realm ;
    static Gson gson=new Gson();

    class Settings
    {
        public String users_storage_base_dir; // Directory per lo storage degli utenti
    }

    @Before
    public void setUp() throws Exception {
        Settings settings = null;
        try
        {

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

        UserRegistryAPI urapi = UserRegistryAPI.instance();
        assertNotNull(urapi);
        urapi.setStorageFiles(System.getProperty("user.dir") + "/src/main/resources/" + settings.users_storage_base_dir + "\\","TestUser"); // Imposto i file di storage
        realm= urapi.getRealm();
        assertNotNull(realm);
        char[] pass1 = {'2', '7', '0', '9'};

        User utente = new User("AlessandraGenca", pass1);
        assertNotNull(userRegJson.addUser(gson.toJson(utente, User.class)));


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
            assertNotNull(gson.fromJson(userRegJson.addUser(u1S),String.class));

            assertTrue("L'utente "+utente1.getIdentifier() +" gia' esiste!", false);
            assertTrue("L'utente "+utente1.getIdentifier()+" esiste, le credenziali sono corrette e dovrebbe autenticarsi!", gson.fromJson(userRegJson.checkUser(gson.toJson(utente1.getIdentifier()+";"+String.copyValueOf(utente1.getSecret()),String.class)), Boolean.class));
        }catch (Exception e) {
            System.out.print("\nNon è stato aggiunto l'utente\n");
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




        //Stampa di tutti gli utenti
        for (User u :realm.getUsers()){
            System.out.println("utente:"+u.getIdentifier()+"\n");
        }

        //check utente esistente password corretta
        assertTrue("L'utente "+utente1.getIdentifier()+utente1.getSecret().toString() +" esiste, le credenziali sono corrette e dovrebbe autenticarsi!", gson.fromJson(userRegJson.checkUser(gson.toJson(utente1.getIdentifier()+";"+ String.copyValueOf(utente1.getSecret()),String.class)), Boolean.class));

        //check utente esistente password sbagliata
        assertTrue("L'utente "+utente1.getIdentifier() +" esiste, ma le credenziali sono sbagliate e non potrebbe autenticarsi!", !gson.fromJson(userRegJson.checkUser(gson.toJson(utente1.getIdentifier()+";ahbfakshvbadfk",String.class)), Boolean.class));

        //check utente non esistente
        assertTrue("L'utente "+utente3.getIdentifier() +" non esiste e non potrebbe autenticarsi!", !gson.fromJson(userRegJson.checkUser(gson.toJson(utente3.getIdentifier()+";"+String.copyValueOf(utente3.getSecret()),String.class)), Boolean.class));


    }


    @AfterClass
    public static void tearDownAfterClass()  {
        File file = new File("src/main/resources/users");
            if(file.exists()) {
                for (File f : file.listFiles()) {
                    //System.out.println(f.getName());
                    if (f.getName().startsWith("Test")) {
                        f.delete();
                    }
                }
            }

    }




}

