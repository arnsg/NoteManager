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
        //userreg.getRealm();
        User u= new User("Alessandra", "Genca");
        userreg.add(u);
        System.out.print("Aggiunto user1:" + u.getIdentifier()+"\n");
        User u2= new User("ale", "Genca");
        userreg.add(u2);
        System.out.print("Aggiunto user2:" + u2.getIdentifier()+"\n");
    }




    //test per verificare get di un utente "Null"
    @Test
    public void testUserRegistry1() {
        System.out.println("inizio test-1>test per verificare get di un utente Null");
        User u=null;

        try {
            u=userreg.get(null);
        } catch (InvalidUsernameException e) {
            System.err.println(e);
            System.out.println("Lanciata eccezione");

        } finally {
            assertNull("Utente non trovato: Get restituisce null", u);

        }
        System.out.println("fine test-1>test per verificare get di un utente Null");
    }

    //test per verificare get di un utente non valido
    @Test
    public void testUserRegistry3() {
        System.out.println("inizio test3->test per verificare get di un utente non valido");
        User u=null;

        try {

            u=userreg.get("aaa");
        } catch (InvalidUsernameException e) {
            System.err.println(e);
            System.out.println("Lanciata Eccezione");

        } finally {
            assertNull("Utente non trovato: Get restituisce null", u);

        }
        System.out.println("fine test-3>test per verificare get di un utente non valido");
    }


    //test per verificare get di un utente  valido
    @Test
    public void testUserRegistry2(){
        User u=null;
        System.out.println("inizio test2->test per verificare get di un utente  valido");
        try {

            u=userreg.get("Alessandra");
            System.out.print("Get su:" + u.getIdentifier()+"\n");
        } catch (InvalidUsernameException e) {
           System.err.println(e);
            System.out.println("Lanciata eccezione");

        } finally {
        assertNotNull( "Utente non trovato: Get restituisce null",u);
    }
        System.out.println("fine test-2>test per verificare get di un utente valido");

    }


}