package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;





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
		throw new InvalidKeyException("Nota non presente nel registro");
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
	
	public void update(Note note) throws InvalidKeyException

	{
		if(!reg.containsKey(note.getTitle()))
			throw new InvalidKeyException("Nota non presente");
		reg.put(note.getTitle(), note);
	}
	
	// DELETE di una entry nella mappa
	
	public void remove(String title) throws InvalidKeyException
	{
		if(!reg.containsKey(title))
			throw new InvalidKeyException("Nota non presente");
		reg.remove(title);
	}
	
	// CONTAINS di una entry, data la key
	
	public boolean contains(String key) {
		return reg.containsKey(key);
	}
	

	public void save(String fileOutName) throws IOException{
		FileOutputStream fileOut=null;
	    try{
			fileOut = new FileOutputStream(fileOutName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(reg);
			out.close();
			fileOut.close();
		}catch (Exception e){
			if(fileOut!=null)fileOut.close();
	    	throw e;
		}
	}
	
	public void load(String fileName) throws IOException, ClassNotFoundException{
		FileInputStream fileIn=null;
		try{
			fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			reg = (HashMap<String,Note>) in.readObject();
			in.close();
			fileIn.close();
		}catch (Exception e){
			if(fileIn!=null)fileIn.close();
			throw e;
		}
	}
	
	
	public String[] getNotebyDate(Date date){
		int i=0;
		int size=0;
		String [] notes;
		Iterator keySetIterator = reg.keySet().iterator();
		while (keySetIterator.hasNext()){
		     Note note = reg.get(keySetIterator.next().toString());
		     if (note.getDate().before(date)){
		    	size= size+1;
		     }
		}
		notes = new String [size];
		//System.out.println(size);
		
		keySetIterator=reg.keySet().iterator();

		while (keySetIterator.hasNext()){
		     Note note2 = reg.get(keySetIterator.next().toString());
		     if (note2.getDate().before(date)){
		    	 notes[i]= note2.getTitle();
		    	 i++;
		}
			
		}
		
		
		return notes;
	}
	
	private HashMap<String,Note> reg;


}
