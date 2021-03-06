package it.libero.alessandragenca.notesmanager.server.backend.wrapper;

import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import it.libero.alessandragenca.notesmanager.server.backend.UserRegistry;
import org.restlet.security.MemoryRealm;
import org.restlet.security.User;

import java.io.File;
import java.io.IOException;



	

public class UserRegistryAPI {
	
	
	public static synchronized UserRegistryAPI instance(){
		if (instance==null) 
		instance= new UserRegistryAPI();
		
		return instance;
	}
	
	private UserRegistryAPI(){ur= new UserRegistry();
	baseStorageFile=null;
	rootDirForStorageFile=null;}
	
	
	public synchronized int size (){return ur.size();}
	
	public synchronized User get(String username) throws InvalidUsernameException{
		return ur.get(username);
		
	}

	public synchronized void add(User u) throws InvalidUsernameException{
		ur.add(u);
		commit();
		
	}
	
	public synchronized void update(User u) throws InvalidUsernameException
	{
		ur.update(u);
		commit();
	}
	
	public synchronized void remove(User u) throws InvalidUsernameException
	{
		ur.remove(u);
		commit();
	}
	
	// Imposto le informazioni di storage degli utenti
	
	public synchronized void setStorageFiles(String rootDirForStorageFile, String baseStorageFile)
	{
		this.rootDirForStorageFile = rootDirForStorageFile;
		this.baseStorageFile = baseStorageFile;
		System.err.println("Users Storage Directory: " + this.rootDirForStorageFile);
		System.err.println("Users Storage Base File: " + this.baseStorageFile);
	}
	
	// Costruisco l'estensione del file in base ai file gi� presenti all'interno della cartella
	
	private int buildStorageFileExtension()
	{
		final File folder = new File(rootDirForStorageFile);
		int c;
		int max = -1;

		File[] listFiles=folder.listFiles();

		if(listFiles!=null){
			for(final File fileEntry : listFiles)
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
		}

		return max;
	}
	
	// Effettuo il salvataggio (commit) delle modifiche su file
	
	private synchronized void commit()
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
			catch (IOException e)
			{
				System.err.println("Restore filed - starting a new registry " + e.getCause() + " " + e.getMessage());
				ur = new UserRegistry();
			}
		}
	}
	
	public synchronized MemoryRealm getRealm()
	{
		return ur.getRealm();
	}
	
	
	private static UserRegistryAPI instance;
	private UserRegistry ur;
	private String rootDirForStorageFile;
	private String baseStorageFile;



	public synchronized String getRootDirForStorageFile() {
		return rootDirForStorageFile;
	}

	public synchronized String getBaseStorageFile() {
		return baseStorageFile;
	}
}
