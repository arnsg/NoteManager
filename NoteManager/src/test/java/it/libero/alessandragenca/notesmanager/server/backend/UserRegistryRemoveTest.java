package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import org.junit.Before;
import org.junit.Test;
import org.restlet.security.User;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class UserRegistryRemoveTest {
    private UserRegistry userreg = new UserRegistry();
    private  User u = new User("Alessandra", "Genca");
    @Before
    public void setUp() throws Exception {
        userreg.add(u);
        System.out.print("Dimensioni del registry antecedente la rimozione e dopo l'add: "+userreg.size()+"\n");
    }

    @Test
    public void testRemoveMethod(){
        //remove di un utente esistente
        System.out.println("inizio test-1>test per verificare remove di un utente valido");
        int size = userreg.size();
        System.out.print("Dimensioni del registry antecedente la rimozione: "+userreg.size()+"\n");
        try {
            userreg.remove(u);
            System.out.print("Dimensioni del registry dopo la rimozione:"+userreg.size()+"\n");

        } catch (InvalidUsernameException e) {
            System.err.println(e);
            System.out.print("Lanciata Eccezione");
            assertTrue("Errore nella rimozione dell'utente",true);

         } finally {

            //verifico le dimensioni del realm per verificare che sia stato effettivamente rimosso l'utente
            assertNotEquals("dimensioni realm non modificate",size, userreg.size());
    }
        System.out.println("fine test-1>test per verificare remove di un utente valido");


    }


    @Test
    public void testRemoveMethod2(){
        //remove di un utente inesistente
        User u1= new User("utentenonaggiunto", "utentenonaggiunto");
        System.out.println("inizio test-2>test per verificare remove di un utente NON presente");
        int size = userreg.size();
        System.out.print("Dimensioni del registry antecedente la rimozione: "+userreg.size()+"\n");
        try {
            userreg.remove(u1);
            System.out.print("Dimensioni del registry dopo la rimozione:"+userreg.size()+"\n");

        } catch (InvalidUsernameException e) {
            System.err.println(e);
            System.out.print("Lanciata Eccezione"+"\n");
            assertTrue("Errore nella rimozione dell'utente",true);

        } finally {

            //verifico le dimensioni del realm per verificare che sia stato effettivamente rimosso l'utente
            assertEquals("dimensioni realm non modificate",size, userreg.size());
        }
        System.out.println("fine test-2>test per verificare remove di un utente valido"+"\n");


    }

}