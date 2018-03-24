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
        User u = new User("Alessandra", "Genca");
        userreg.add(u);

    }

    //aggiunta utente valido
    @Test
    public void TestAdd1() {

        int size=userreg.size();
        User u= new User("Ale", "Genca");
        try {

            userreg.add(u);


        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }

        //verifico le dimensioni del realm per verificare che sia stato effettivamente aggiunto l'utente
        assertNotEquals("dimensioni realm non modificate",size, userreg.size());
    }



    //aggiunta utente duplicato
    @Test
    public void TestAdd2() {
        int size=userreg.size();
        User u= new User("Alessandra", "Genca");
        try {
            userreg.add(u);
        } catch (InvalidUsernameException e) {
            System.err.println(e);
            assertTrue("Utente duplicato", true);
        } finally {


            //verifico le dimensioni del realm per verificare che sia stato effettivamente aggiunto l'utente
            assertEquals("dimensioni realm non modificate",size, userreg.size());
        }
    }
}