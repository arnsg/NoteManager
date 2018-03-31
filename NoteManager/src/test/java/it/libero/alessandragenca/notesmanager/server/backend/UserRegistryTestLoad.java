package it.libero.alessandragenca.notesmanager.server.backend;

import org.junit.Test;
import org.restlet.security.User;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class UserRegistryTestLoad {


    private UserRegistry userreg = new UserRegistry();




    @Test
    public void testLoad1() {


        try{

            userreg.load("src/main/resources/FileProvaSave.txt");
            System.out.print("Caricamento file avvenuto\n");
            System.out.print("Dimensione registro"+userreg.size()+"\n");
            for(User user : userreg.getRealm().getUsers())
                    System.out.println("user:" +user.getIdentifier()+","+String.copyValueOf(user.getSecret()));
            {



            }


        }catch (IOException e) {
            assertTrue("Errore gestione file", true);
            e.printStackTrace();
        }


    }


}