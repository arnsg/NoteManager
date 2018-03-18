package it.libero.alessandragenca.notesmanager.server.web.resources;

import com.google.gson.Gson;
import it.libero.alessandragenca.notesmanager.commons.ErrorCodes;
import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.NoteRegistryAPI;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.text.ParseException;



public class NoteJSON extends ServerResource{
	
	@Get
    public String getNote() throws ParseException  {   	
    	Gson gson = new Gson();
    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
    	try{
    		Note n = nrapi.get(getAttribute("title"));
    		return gson.toJson(n, Note.class);   	
    	} catch (InvalidKeyException e){
    		Status s = new Status(ErrorCodes.INVALID_KEY_CODE);
    		setStatus(s);
    		return gson.toJson(e, InvalidKeyException.class);
    	}
    }
    
    
    @Delete
    public String deleteNote(){
    	Gson gson = new Gson();
    	// ottengo una istanza del registro
    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
    	
    	
    		try {
    			Note c = nrapi.get(getAttribute("title"));
				nrapi.remove(c.getTitle());
				return gson.toJson("note removed");
				
    	    	} catch (InvalidKeyException e) {
				Status s = new Status(ErrorCodes.INVALID_KEY_CODE);
	    		setStatus(s);
	    		return gson.toJson(e, InvalidKeyException.class);
			}
    	}

}
