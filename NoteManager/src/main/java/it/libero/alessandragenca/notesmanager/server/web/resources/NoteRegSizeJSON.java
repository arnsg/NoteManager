package it.libero.alessandragenca.notesmanager.server.web.resources;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

import it.libero.alessandragenca.notesmanager.commons.ErrorCodes;
import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.NoteRegistryAPI;



public class NoteRegSizeJSON extends ServerResource {
	
	@Get
    public String getSize() throws ParseException  {    	
    	Gson gson = new Gson();
    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
    	return gson.toJson(new Integer(nrapi.size()),Integer.class);   	
    }
	
	
	 @Post
	    public String getNotebyDate(String payload) throws ParseException {   	
	    	Gson gson = new Gson();
	    	NoteRegistryAPI nrapi = NoteRegistryAPI.instance();
	    	//simpleDateFormat df= new SimpleDateFormat();
	    	//Date d =df.parse(payload);
	    	Date d = gson.fromJson(payload, Date.class);
	    	return gson.toJson(nrapi.getNotesByDate(d),Note[].class);
	    	//Note n = gson.fromJson(payload, Note.class);
	    	/*try{
	    		nrapi.add(n);
	    	return gson.toJson("note added: " + n.getTitle(), String.class);
	    	} catch (InvalidKeyException e){    		
	    		Status s = new Status(ErrorCodes.INVALID_KEY_CODE);
	    		setStatus(s);
	    		return gson.toJson(e, InvalidKeyException.class);
	    	} */
	    	
	    }

}
