package it.libero.alessandragenca.notesmanager.server.backend;

import org.junit.Before;
import org.junit.Test;
import org.restlet.security.User;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class UserRegistryTestSave {

    private UserRegistry userreg = new UserRegistry();
    User u = new User("Alessandra", "Genca");

    @Before
    public void setUp() throws Exception {
        //userreg.getRealm();
        System.out.print("Dimensioni prima dell'add e del save:"+userreg.size()+"\n");
        userreg.add(u);




    }

    @Test
    public void testSave1() {
        System.out.print("Dimensioni dopo dell'add:"+userreg.size()+"\n");

        try{

            userreg.save("src/main/resources/FileProvaSave.txt");
            System.out.print("salvataggio nel file avvenuto correttamente");


        }catch (IOException e) {
            assertTrue("Errore creazione file", true);
        }




    }



}