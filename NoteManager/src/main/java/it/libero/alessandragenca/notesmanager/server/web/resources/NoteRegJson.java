package it.libero.alessandragenca.notesmanager.server.web.resources;

import java.io.FileNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.data.Status;

import com.google.gson.Gson;

import it.libero.alessandragenca.notesmanager.commons.ErrorCodes;
import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.NoteRegistryAPI;



public class NoteRegJson extends ServerResource{
	
	@Get
    public String getTitles() throws ParseException, InvalidKeyException  {   	
    	Gson gson = new Gson();
    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
    	return gson.toJson(nrapi.titles(), String[].class);
    }
    
    @Post
    public String addNote(String payload) throws ParseException  {   	
    	Gson gson = new Gson();
    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
    	Note n = gson.fromJson(payload, Note.class);
    	try{
    		nrapi.add(n);
    	return gson.toJson("note added: " + n.getTitle(), String.class);
    	} catch (InvalidKeyException e){    		
    		Status s = new Status(ErrorCodes.INVALID_KEY_CODE);
    		setStatus(s);
    		return gson.toJson(e, InvalidKeyException.class);
    	}    		
    }
    	
    @Put
    public String updateNote(String payload) throws ParseException, FileNotFoundException {
    	Gson gson = new Gson();
    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
    	Note n = gson.fromJson(payload, Note.class);
    	nrapi.update(n);
		return gson.toJson("note modified: " + n.getTitle(), String.class); 
    	
    }
    
    
    @Delete
    public String deleteAll(){
    	Gson gson = new Gson();
    	// ottengo una istanza del registro
    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
    	int i=0;
    	
    		try {
    			String [] titles= nrapi.titles();
    	    	for (i=0; i<titles.length; i++){
    	    
				nrapi.remove(titles[i]);
			
    	    	}
    	    	return gson.toJson("notes removed");
			} catch (InvalidKeyException e) {
				Status s = new Status(ErrorCodes.INVALID_KEY_CODE);
	    		setStatus(s);
	    		return gson.toJson(e, InvalidKeyException.class);
			}
    	}

}
