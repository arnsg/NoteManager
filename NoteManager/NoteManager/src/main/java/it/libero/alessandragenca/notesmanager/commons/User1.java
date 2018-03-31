package it.libero.alessandragenca.notesmanager.commons;

public class User1 {
	


	
	// Costruttore oggetto User
	public User1(String identifier, char [] secret)
	{
		this.identifier = identifier;
		this.secret = secret;
		//this.role = role;
	}
	
	// Costruttore vuoto per persistenza (richiesto dalla libreria JACKSON e dall'ObjectMapper)
	public User1(){};
	
	
	// GET dell'identifier
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	// GET della secret
	
	public char[] getSecret()
	{
		return secret;
	}
	
	
	
	private String identifier; 
	private char[] secret;

}
