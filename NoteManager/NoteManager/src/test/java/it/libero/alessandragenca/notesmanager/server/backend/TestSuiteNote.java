package it.libero.alessandragenca.notesmanager.server.backend;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ NoteRegistryTestAdd.class, NoteRegistryTestGet.class ,NoteRegistryTestRemove.class,
        NoteRegistryTestUpdate.class, NoteRegistryTestSave.class, NoteRegistryTestLoad.class})


public class TestSuiteNote {

}
