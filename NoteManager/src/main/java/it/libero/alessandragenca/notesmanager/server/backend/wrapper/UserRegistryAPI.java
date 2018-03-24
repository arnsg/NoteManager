package it.libero.alessandragenca.notesmanager.server.backend.wrapper;

import java.io.File;
import java.io.IOException;

import org.restlet.security.MemoryRealm;
import org.restlet.security.User;

import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import it.libero.alessandragenca.notesmanager.server.backend.UserRegistry;



	

public class UserRegistryAPI {
	
	
	public static synchronized UserRegistryAPI instance(){
		if (instance==null) 
		instance= new UserRegistryAPI();
		
		return instance;
	}
	
	protected UserRegistryAPI(){ur= new UserRegistry();}
	
	
	public synchronized int size (){return ur.size();}
	
	public synchronized User get(String username) throws InvalidUsernameException{
		return ur.get(username);
		
	}

	public synchronized void add(User u) throws InvalidUsernameException{
		ur.add(u);
		commit();
		
	}
	

	
	public synchronized void remove(User u) throws InvalidUsernameException
	{
		ur.remove(u);
		commit();
	}
	
	// Imposto le informazioni di storage degli utenti
	
	public void setStorageFiles(String rootDirForStorageFile, String baseStorageFile)
	{
		this.rootDirForStorageFile = rootDirForStorageFile;
		this.baseStorageFile = baseStorageFile;
		System.err.println("Users Storage Directory: " + this.rootDirForStorageFile);
		System.err.println("Users Storage Base File: " + this.baseStorageFile);
	}
	
	// Costruisco l'estensione del file in base ai file già presenti all'interno della cartella
	
	protected int buildStorageFileExtension()
	{
		final File folder = new File(rootDirForStorageFile);
		int c;
		int max = -1;
		
		for(final File fileEntry : folder.listFiles())
		{
			if(fileEntry.getName().substring(0, baseStorageFile.length()).equalsIgnoreCase(baseStorageFile))
			{
				try
				{
					c = Integer.parseInt(fileEntry.getName().substring(baseStorageFile.length()+1));
				}
				catch(NumberFormatException | StringIndexOutOfBoundsException e)
				{
					c = -1;
				}
				if(c > max) max=c;
			}
		}
		return max;
	}
	
	// Effettuo il salvataggio (commit) delle modifiche su file
	
	public synchronized void commit()
	{
		int extension = buildStorageFileExtension();
		String fileName = rootDirForStorageFile + baseStorageFile + "." + (extension + 1);
		System.err.println("Commit storage to: " + fileName);
		try
		{
			ur.save(fileName);
		}
		catch (IOException e)
		{
			System.err.println("Commit filed " + e.getMessage() + " " + e.getCause());
		}		
	}
	
	// Effettuo il recupero delle informazioni degli utenti (restore)
	
	public synchronized void restore()
	{
		int extension = buildStorageFileExtension();
		if (extension == -1){
			System.err.println("No data to load - starting a new registry");
		} else {
			String fileName = rootDirForStorageFile + baseStorageFile + "." + extension;
			System.err.println("Restore storage from: " + fileName);			
			try
			{
				ur.load(fileName);
			}
			catch (ClassNotFoundException | IOException e)
			{
				System.err.println("Restore filed - starting a new registry " + e.getCause() + " " + e.getMessage());
				ur = new UserRegistry();
			}
		}
	}
	
	public MemoryRealm getRealm()
	{
		return ur.getRealm();
	}
	
	
	private static UserRegistryAPI instance;
	private UserRegistry ur;
	private String rootDirForStorageFile;
	private String baseStorageFile;
	

}
