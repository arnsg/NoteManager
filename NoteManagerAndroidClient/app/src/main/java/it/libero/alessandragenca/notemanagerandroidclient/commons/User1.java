package it.libero.alessandragenca.notemanagerandroidclient.commons;

import java.util.Arrays;

public class User1 {
	


	
	// Costruttore oggetto UserWrap
	
	public User1(String identifier, char [] secret)
	{
		this.identifier = identifier;
		this.secret = Arrays.copyOfRange(secret,0,secret.length);
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
		return Arrays.copyOfRange(secret,0,secret.length);
	}
	
	
	
	private String identifier; 
	private char[] secret;

}
