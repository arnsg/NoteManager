package it.libero.alessandragenca.notesmanager.server.web.frontend;

import com.google.gson.Gson;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.NoteRegistryAPI;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.UserRegistryAPI;
import it.libero.alessandragenca.notesmanager.server.web.resources.NoteJSON;
import it.libero.alessandragenca.notesmanager.server.web.resources.NoteRegJson;
import it.libero.alessandragenca.notesmanager.server.web.resources.NoteRegSizeJSON;
import it.libero.alessandragenca.notesmanager.server.web.resources.UserRegJSON;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MemoryRealm;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;



public class NoteRegistryWebApp extends Application{
	
	
public static MemoryRealm realm;
	
	/* Creo la classe relative alle impostazioni del server */
	
	private class Settings
	{
		public int port; // Porta utilizzata dal server sulla quale fornisce i propri servizi
		public String storage_base_dir; // Directory per lo storage delle note 
		public String storage_base_file; // File per lo storage delle note
		public String users_storage_base_dir; // Directory per lo storage degli utenti
		public String users_storage_base_file; // File per lo storage degli utenti
		public String web_base_dir; //directory per le risorse web
	}
	
	private static String rootDirForWebStaticFiles;
	
	public Restlet createInboundRoot()
	{	
		Router router = new Router(getContext()); // Creo il Router per l'indirizzamento delle risorse tramite URI
		
		/* Creo i ChallengeAuthenticator per la verifica dell'autenticazione e autorizzazzione delle singole risorse */
		
		ChallengeAuthenticator guardSize = createAuthenticator();
		guardSize.setNext(NoteRegSizeJSON.class);
		
		ChallengeAuthenticator guardNoteReg = createAuthenticator();
		guardNoteReg.setNext(NoteRegJson.class);
		
		ChallengeAuthenticator guardNote = createAuthenticator();
		guardNote.setNext(NoteJSON.class);
		
		
		Directory directory = new Directory(getContext(), rootDirForWebStaticFiles);
        directory.setListingAllowed(true);
        directory.setDeeplyAccessible(true);
        
        router.attach("/NoteRegApplication/web/", directory);
        router.attach("/NoteRegApplication/web", directory);
	
      
        
   
		router.attach("/NoteRegApplication/size", guardSize);
        router.attach("/NoteRegApplication/notes",guardNoteReg);
        router.attach("/NoteRegApplication/notes/",guardNoteReg);
        router.attach("/NoteRegApplication/notes/",guardNoteReg);
        router.attach("/NoteRegApplication/notes/{title}", guardNote);
        router.attach("/NoteRegApplication/users", UserRegJSON.class);
        
        return router;
    }

    private ChallengeAuthenticator createAuthenticator() {
        ChallengeAuthenticator guard = new ChallengeAuthenticator(
                getContext(), ChallengeScheme.HTTP_BASIC, "Realm"); // Creo il ChallengeAuthenticator specificando il Basic Authentication Scheme offerto da HTTP
        
        // Imposta il verifier (per la verifica delle credenziali) 
        guard.setVerifier(realm.getVerifier());
        return guard;
    }
    
	public static void main(String[] args)  {
		
		Gson gson = new Gson();
		Settings settings = null;
		
		// Inizializzo le impostazioni del server basandomi sulle informazioni contenute nel file settings.json
		Scanner scanner=null;
		try
		{
			System.out.print(new File("").getAbsolutePath());
			System.out.print("stampa:"+System.getProperty("user.dir"));
			scanner = new Scanner(new File ("src/main/resources/settings.json"), "UTF-8");
			settings = gson.fromJson(scanner.nextLine(), Settings.class);
			scanner.close();
			System.err.println("Loading settings from file");
		}

		catch (IOException e)
		{
			System.err.println("Settings file not found");

		}
		
		rootDirForWebStaticFiles="file:///"+System.getProperty("user.dir")+"/src/main/resources/"+settings.web_base_dir;
		System.err.println("Web Directory: " + rootDirForWebStaticFiles);
		
		// Richiamo le singole istanze (sono singleton) delle API per i registri di oggetti e utenti  ed effettuo il restore
		
		
		
		NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
		nrapi.setStorageFiles(System.getProperty("user.dir") +"/src/main/resources/"+ settings.storage_base_dir + "\\", settings.storage_base_file); // Imposto i file di storage
		try {
			nrapi.restore();
		} catch (IOException e) {
			e.printStackTrace();
		}

		UserRegistryAPI urapi = UserRegistryAPI.instance();
		urapi.setStorageFiles(System.getProperty("user.dir") + "/src/main/resources/" + settings.users_storage_base_dir + "\\", settings.users_storage_base_file); // Imposto i file di storage
		realm = urapi.getRealm();
		urapi.restore();
        
	    try
	    {
	        Component component = new Component(); // Creo la componente Restlet
	        component.getServers().add(Protocol.HTTP, settings.port); // Imposto le informazioni relative al Server
	        component.getClients().add(Protocol.FILE); // Imposto le informazioni relative al Client
	        component.getDefaultHost().attach(new NoteRegistryWebApp()); // Allego l'Application al DefaultHost
	        
	        // Avvio la componente Restlet
	        component.start(); 
	    }
	    catch (Exception e)
	    {	  
	        e.printStackTrace();
	    }
	}
}

