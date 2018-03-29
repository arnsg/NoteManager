package it.libero.alessandragenca.notesmanager.server.backend;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UserRegistryAddTest.class, UserRegistryGetUtenteTest.class, UserRegistryRemoveTest.class,
        UserRegistryTestLoad.class, UserRegistryTestSave.class})
public class TestSuiteUser {
}
