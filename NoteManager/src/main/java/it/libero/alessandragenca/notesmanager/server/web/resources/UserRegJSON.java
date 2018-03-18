package it.libero.alessandragenca.notesmanager.server.web.resources;

import java.text.ParseException;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.security.User;

import com.google.gson.Gson;

import it.libero.alessandragenca.notesmanager.commons.ErrorCodes;
import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.UserRegistryAPI;




	
public class UserRegJSON extends ServerResource{
		
	    @Post
	    public String addUser(String payload) throws ParseException  {  
	    	Gson gson = new Gson();
	    	UserRegistryAPI urapi = UserRegistryAPI.instance();
	    	User u = gson.fromJson(payload, User.class);
	    	try{
	    		urapi.add(u);
	    		
	    		return gson.toJson("User added: " + u.getIdentifier(), String.class);
	    	} catch (InvalidUsernameException e){    		
	    		Status s = new Status(ErrorCodes.INVALID_KEY_CODE);
	    		setStatus(s);
	    		return gson.toJson(e, InvalidUsernameException.class);
	    	}
	    }
	    
	}


