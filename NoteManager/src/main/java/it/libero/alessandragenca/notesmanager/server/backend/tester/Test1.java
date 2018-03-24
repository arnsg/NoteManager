package it.libero.alessandragenca.notesmanager.server.backend.tester;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import it.libero.alessandragenca.notesmanager.commons.InvalidKeyException;
import it.libero.alessandragenca.notesmanager.commons.Note;
import it.libero.alessandragenca.notesmanager.server.backend.NoteRegistry;

public class Test1 {
	


	public static void main(String[] args) {

		NoteRegistry nr = new NoteRegistry();
				
		System.out.println("Size: " + nr.size());
		
		//String myDateStr="09/11/17";
		//Date myDate=null;
		Date myDate =new Date();
		    /*try {
		     DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
		     myDate = dateFormat.parse(myDateStr);
		     //System.out.println(dateFormat.format(myDate));
		    } catch (ParseException e) {
		      e.printStackTrace();
		    }*/
		
		try {
			System.out.println("add-01");
			nr.add(new Note("Nota1", "Questo e' il testo della nota 1",myDate));
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		try {
			System.out.println("add-02");
			nr.add(new Note("Nota2", "Questo e' il testo della nota 2",myDate));
			System.out.println("OK");
			
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}

		try {
			System.out.println("add-03");
			GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2017, GregorianCalendar.NOVEMBER, 12);
			Date data1 = gregorianCalendar1.getTime();
			nr.add(new Note("Nota3", "Questo e' il testo della nota 3",data1));
			System.out.println("OK");
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}

		try {
			System.out.println("add-05");
			nr.add(new Note("Nota5", "Questo e' il testo della nota 5",myDate));
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		try {
			System.out.println("get-04");
			System.out.println(nr.get("Nota4").toString());
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}

		
		try {
			System.out.println("add-03");
			nr.add(new Note("Nota3", "Questo e' il testo della nota 3",myDate));
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		try {
			System.out.println("get-01");
			System.out.println(nr.get("Nota1").toString());
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		System.out.println("update-01");
		try {
			nr.add(new Note("Nota1", "Nuovo testo nota1", myDate));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("get-01");
			System.out.println(nr.get("Nota1").toString());
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		System.out.println("Size: " + nr.size());
		
		try {
			System.out.println("remove-01");
			nr.remove("Nota1");
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		System.out.println("Size: " + nr.size());
		
		try {
			System.out.println("get-01");
			System.out.println(nr.get("Nota1").toString());
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}

		try {
			System.out.println("remove-08");
			nr.remove("Nota8");
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		System.out.println("Size: " + nr.size());

		System.out.println("update-10");
		try {
			nr.add(new Note("Nota10", "Testo nota 10", myDate));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		System.out.println("Size: " + nr.size());
		
		try {
			System.out.println("get-10");
			System.out.println(nr.get("Nota10").toString());
			System.out.println("OK");
		} catch (InvalidKeyException e) {
			System.out.println("ERR: " + e.getMessage());
		}
		
		System.out.println("get-licences");
		for (String t : nr.titles())
				System.out.println("Titles: "+t);

		Date d = new Date();
		String notes[] = nr.getNotebyDate(d);
		for (int i=0; i<notes.length; i++)
			System.out.println(notes[i]);
	
	}
}
