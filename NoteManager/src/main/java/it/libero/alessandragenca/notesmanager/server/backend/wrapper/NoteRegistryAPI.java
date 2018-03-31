package it.libero.alessandragenca.notesmanager.server.backend.wrapper;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import it.libero.alessandragenca.notesmanager.server.backend.NoteRegistry;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class NoteRegistryAPI {


	public static synchronized NoteRegistryAPI instance() {

		if (instance == null)
			instance = new NoteRegistryAPI();


		return instance;
	}
	
	public synchronized int size(){
		return nr.size();
	}

	public synchronized Note get(String title) throws InvalidKeyException {
		return nr.get(title); 
	}
	
	

	public synchronized String[] titles(){
		return nr.titles();
	}
	
	public synchronized String[] getNotesByDate(Date date){
		return nr.getNotebyDate(date);
	}

	public synchronized void add(Note n) throws InvalidKeyException {
		nr.add(n);
		commit();
	}

	public synchronized void update(Note n) throws InvalidKeyException {
		nr.update(n);	
		commit();
	}

	public synchronized void remove(String title) throws InvalidKeyException{
		nr.remove(title);	
		commit();
	}
			
	protected NoteRegistryAPI(){
		nr = new NoteRegistry();
	}
	
	
	// Imposto le informazioni di storage degli utenti
	
		public void setStorageFiles(String rootDirForStorageFile, String baseStorageFile)
		{
			this.rootDirForStorageFile = rootDirForStorageFile;
			this.baseStorageFile = baseStorageFile;
			System.err.println("Users Storage Directory: " + this.rootDirForStorageFile);
			System.err.println("Users Storage Base File: " + this.baseStorageFile);
		}
		
		// Costruisco l'estensione del file in base ai file gi� presenti all'interno della cartella
		
		protected int buildStorageFileExtension() throws  NullPointerException
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
				nr.save(fileName);
			}
			catch (Exception e)
			{
				System.err.println("Commit filed " + e.getMessage() + " " + e.getCause());
			}		
		}
		
		// Effettuo il recupero delle informazioni degli utenti (restore)
		
		public synchronized void restore() throws IOException {
			int extension = buildStorageFileExtension();
			if (extension == -1){
				System.err.println("No data to load - starting a new registry");
			} else {
				String fileName = rootDirForStorageFile + baseStorageFile + "." + extension;
				System.err.println("Restore storage from: " + fileName);			
				try
				{
					nr.load(fileName);
				}
				catch (ClassNotFoundException  e)
				{
					System.err.println("Restore filed - starting a new registry " + e.getCause() + " " + e.getMessage());
					nr = new NoteRegistry();
				}
			}
		}
		
	private static NoteRegistryAPI instance;
	private NoteRegistry nr;
	private String rootDirForStorageFile;
	private String baseStorageFile;
}
