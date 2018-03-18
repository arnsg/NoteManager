package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;





public class NoteRegistry {
	
	public NoteRegistry()
	{
		reg = new HashMap<String, Note>();
	}
	
	// GET della dimensione della mappa di oggetti

	public int size()
	{
		return reg.size();
	}
	
	// GET di uno specifico oggetto data la particolare key
	
	public Note get(String title) throws InvalidKeyException
	{
		Note note = reg.get(title);
		if(note != null) return note;
		throw new InvalidKeyException("Chiave non valida: " + title);
	}
	
	// GET di tutte le note 
	
	public String [] titles()
	{
		return reg.keySet().toArray(new String[reg.keySet().size()]);
	}
	
	// PUT di una nuova entry nella mappa
	
	public void add(Note note) throws InvalidKeyException
	{
		if(reg.containsKey(note.getTitle()))
			throw new InvalidKeyException("Key duplicata: " + note.getTitle());
		reg.put(note.getTitle(), note);
	}
	
	// UPDATE di una entry nella mappa
	
	public void update(Note note)
	{
		reg.put(note.getTitle(), note);
	}
	
	// DELETE di una entry nella mappa
	
	public void remove(String title) throws InvalidKeyException
	{
		if(!reg.containsKey(title))
			throw new InvalidKeyException("Chiave non valida: " + title);
		reg.remove(title);
	}
	
	// CONTAINS di una entry, data la key
	
	public boolean contains(String key) {
		return reg.containsKey(key);
	}
	

	public void save(String fileOutName) throws IOException{		
	    FileOutputStream fileOut = new FileOutputStream(fileOutName);
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(reg);
	    out.close();
	    fileOut.close();
	}
	
	public void load(String fileName) throws IOException, ClassNotFoundException{
	    FileInputStream fileIn = new FileInputStream(fileName);
	    ObjectInputStream in = new ObjectInputStream(fileIn);
	    reg = (HashMap<String,Note>) in.readObject();
	    in.close();
	    fileIn.close();
	}
	
	
	public String[] getNotebyDate(Date date){
		int i=0;
		int size=0;
		String [] notes =null;
		Set <String> keySet = reg.keySet();
		for(String  key:keySet){
		     Note note = reg.get(key);
		     if (note.getDate().before(date)){
		    	size= size+1;
		     }
		}
		notes = new String [size];
		//System.out.println(size);
		
		
		for(String  key2:keySet){
		     Note note2 = reg.get(key2);
		     if (note2.getDate().before(date)){
		    	 notes[i]= note2.getTitle();
		    	 i++;
		}
			
		}
		
		
		return notes;
	}
	
	private HashMap<String,Note> reg;


}
