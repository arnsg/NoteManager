package it.libero.alessandragenca.notesmanager.commons;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class User1Test {
    User1 u;

    @Before
    public void setUp() throws Exception {
        char[] pass = {'2','7','0','9'};
        u = new User1("AlessandraGenca", pass);
        Assert.assertNull("Creato oggetto USER", u);



    }

    @Test
    public void getIdentifier() {

        Assert.assertEquals(" Errore Test del get Identifier ", u.getIdentifier(), "AlessandraGenca");


    }

    @Test
    public void getSecret() {
        char[] pass = {'2','7','0','9'};
        Assert.assertArrayEquals(" Errore Test del get Secret ", u.getSecret(), pass);

    }
}