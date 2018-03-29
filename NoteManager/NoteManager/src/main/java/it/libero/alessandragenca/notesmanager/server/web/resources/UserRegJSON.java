package it.libero.alessandragenca.notesmanager.server.web.resources;

import com.google.gson.Gson;
import it.libero.alessandragenca.notesmanager.commons.ErrorCodes;
import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import it.libero.alessandragenca.notesmanager.server.backend.wrapper.UserRegistryAPI;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.security.User;

import java.text.ParseException;
import java.util.StringTokenizer;


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

	@Put
	public String checkUser(String payload){
		Gson gson = new Gson();
		UserRegistryAPI urapi = UserRegistryAPI.instance();
		String response=gson.fromJson(payload, String.class);
		StringTokenizer st = new StringTokenizer(response,";");
		String username = st.nextToken();
		String password = st.nextToken();
		User u= urapi.getRealm().findUser(username);
		if(u!=null && u.getIdentifier().equalsIgnoreCase(username) && String.copyValueOf(u.getSecret()).equalsIgnoreCase(password))
			return gson.toJson(true, Boolean.class);
		 return gson.toJson(false, Boolean.class);


	}
	    
	}


