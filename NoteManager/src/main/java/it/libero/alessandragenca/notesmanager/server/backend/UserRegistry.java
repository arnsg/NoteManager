package it.libero.alessandragenca.notesmanager.server.backend;

import it.libero.alessandragenca.notesmanager.commons.InvalidUsernameException;
import it.libero.alessandragenca.notesmanager.commons.User1;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.security.MemoryRealm;
import org.restlet.security.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;



public class UserRegistry {
	
	public UserRegistry(){
		
		realm= new MemoryRealm();
	}
	
	public int size (){
		return realm.getUsers().size();
	}
	
	public User get (String username) throws InvalidUsernameException{
		User u= realm.findUser(username);
		if (u!=null)return u;
		throw new InvalidUsernameException("Username non valido: " + username);
			
		
	}
	
	public void add (User u) throws InvalidUsernameException{
		//controlliamo se c'e' gia un user con quell'identifier
		for (User users: realm.getUsers()){
			if(u.getIdentifier().equalsIgnoreCase(users.getIdentifier()))
				throw new InvalidUsernameException("Username Duplicato:" + u.getIdentifier());
			}
		realm.getUsers().add(u);

	}
	
	public void update(User u){
		realm.getUsers().add(u);
		}
	
	public void remove (User u) throws InvalidUsernameException{
		//for (User users: realm.getUsers()){
			//if(u.getIdentifier().equalsIgnoreCase(users.getIdentifier()))
				//realm.getUsers().remove(u);
		User u1= realm.findUser(u.getIdentifier());
		if (u1!=null) realm.getUsers().remove(u);
		else throw new InvalidUsernameException("Username non presente, non posso rimuovere l'utente: " + u.getIdentifier());



	}

		
	public void save(String fileOutName) throws IOException{		
	    FileOutputStream fileOut = new FileOutputStream(fileOutName);
	    PrintStream out = new PrintStream(fileOut);
	   
	    ArrayList<User1> userList = new ArrayList<User1>();
	    ObjectMapper objectMapper = new ObjectMapper(); // Classe della libreria JACKSON per la memorizzazione della lista di utenti con codifica JSON
	    for(User user : realm.getUsers())
	    {
	    	
	    		
	    		userList.add(new User1(user.getIdentifier(), user.getSecret()));
	    }
	    String json = objectMapper.writeValueAsString(userList); // Ottengo la stringa (con codifica JSON) rappresentante la lista di utenti
	    out.print(json); // Scrivo su file (con codifica JSON) l'intera lista di utenti
	    out.close();
	    fileOut.close();
	}
	
	// Caricamento della lista utenti da file 
	
	public void load(String fileName) throws IOException, ClassNotFoundException{
	    FileInputStream fileIn = new FileInputStream(fileName);
	    ArrayList<User1> userList;
	    ObjectMapper mapper = new ObjectMapper();
	    // Tramite la funzione readValue riottengo la lista degli utenti a partire dalla stringa con codifica JSON
	    userList = mapper.readValue(fileIn, new TypeReference<ArrayList<User1>>() {});
	    for(User1 userWrapped : userList) // Per ogni utente nella lista, riottengo l'oggetto User, andando a ripopolare il Realm (ripristino)
	    {
	    	User user = new User(userWrapped.getIdentifier(), userWrapped.getSecret());
	    	realm.getUsers().add(user); // Aggiungo l'utente alla lista
	     }
	    fileIn.close();
	} 
	
	
	
	public MemoryRealm getRealm(){
		return realm;
	}
	
	
	private  MemoryRealm realm;
	
}

