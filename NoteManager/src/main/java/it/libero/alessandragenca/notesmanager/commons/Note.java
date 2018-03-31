package it.libero.alessandragenca.notesmanager.commons;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
	

	private static final long serialVersionUID = 1L;

	public Note (String title, String text, Date date){
		this.title=title;
		this.text= text;
		this.date=date;
		
		
		
	}

	public String getTitle(){
		return title;
	}
	
	public String getText(){
		return text;
	}
	
	public Date getDate(){
		return date;
	}

	public void setTitle(String title){
		this.title=title;
	}
	
	public void setText(String text){
		this.text=text;
	}
	
	public void setDate(Date date){
		this.date=date;
	}
	
	public String toString() {
		
		return "Note [Title=" + title + ", Text=" + text + ", Date=" + date.toString() + "]";
	}
	@Expose
	private String title;
	@Expose
    private String text;
	@Expose
	private Date date;

}
