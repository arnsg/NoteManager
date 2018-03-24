package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import org.junit.Before;
import org.junit.Test;
import org.restlet.security.User;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserRegistryGetUtenteTest {

    private UserRegistry userreg = new UserRegistry();


    @Before
    public void setUp() throws Exception {
        userreg.getRealm();
        User u= new User("Alessandra", "Genca");
        userreg.add(u);
        User u2= new User("ale", "Genca");
        userreg.add(u2);
    }




    //test per verificare get di un utente "Null"
    @Test
    public void TestUserRegistry1() {
        User u=null;

        try {
            u=userreg.get(null);
        } catch (InvalidUsernameException e) {
            System.err.println(e);

        } finally {
            assertNull("Utente non trovato: Get restituisce null", u);

        }
    }

    //test per verificare get di un utente non valido
    @Test
    public void TestUserRegistry3() {
        User u=null;

        try {
            u=userreg.get("aaa");
        } catch (InvalidUsernameException e) {
            System.err.println(e);

        } finally {
            assertNull("Utente non trovato: Get restituisce null", u);

        }
    }





    //test per verificare get di un utente  valido
    @Test
    public void TestUserRegistry2(){
        User u=null;

        try {
            u=userreg.get("Alessandra");
        } catch (InvalidUsernameException e) {
           System.err.println(e);

        } finally {
        assertNotNull( "Utente non trovato: Get restituisce null",u);
    }

    }


}