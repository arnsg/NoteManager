package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import org.junit.Before;
import org.junit.Test;
import org.restlet.security.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UserRegistryAddTest {
    private UserRegistry userreg = new UserRegistry();

    @Before
    public void setUp() throws Exception {
        userreg.getRealm();
        System.out.print("Dimensioni prima dell'add:"+userreg.size()+"\n");
        User u = new User("Alessandra", "Genca");
        userreg.add(u);
        System.out.print("Dimensioni dopo dell'add:"+userreg.size()+"\n");


    }

    //aggiunta utente valido
    @Test
    public void TestAdd1() {
        System.out.println("inizio test-1>test per verificare add di un utente valido");
        int size=userreg.size();
        System.out.println("dimensioni registro:"+ size);
        User u= new User("Ale", "Genca");
        try {

            userreg.add(u);
            System.out.println("Utente aggiunto correttamente, dimensioni registro:" + userreg.size());

        } catch (InvalidUsernameException e) {
            System.err.print(e);
            e.printStackTrace();
            System.out.println("Lanciata eccezione");
        }

        //verifico le dimensioni del realm per verificare che sia stato effettivamente aggiunto l'utente
        assertNotEquals("dimensioni realm non modificate",size, userreg.size());
        System.out.println("fine test-1>test per verificare add di un utente valido");
    }



    //aggiunta utente duplicato
    @Test
    public void TestAdd2() {
        System.out.println("inizio test2->aggiunta utente duplicato");
        int size=userreg.size();
        System.out.println("dimensioni registro:"+ size);
        User u= new User("Alessandra", "Genca");

        try {
            userreg.add(u);
        } catch (InvalidUsernameException e) {
            System.err.println(e);
            System.out.println("Lanciata eccezione per utente duplicato");
            assertTrue("Utente duplicato", true);
        } finally {


            //verifico le dimensioni del realm per verificare che sia stato effettivamente aggiunto l'utente
            assertEquals("dimensioni realm non modificate",size, userreg.size());
        }
    }
}