package it.libero.alessandragenca.notesmanager.commons;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Note (String title, String text, Date date){
		this.title=title;
		this.text= text;
		Date newDate=new Date();
		newDate.setTime(date.getTime());
		this.date=newDate;
		
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getText(){
		return text;
	}
	
	public Date getDate(){
		Date newDate=new Date();
		newDate.setTime(date.getTime());
		return newDate;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	
	public void setText(String text){
		this.text=text;
	}
	
	public void setDate(Date date){
		Date newDate=new Date();
		newDate.setTime(date.getTime());
		this.date=newDate;
	}
	
	public String toString() {
		
		return "Note [Title=" + title + ", Text=" + text + ", Date=" + date.toString() + "]";
	}
	
	private String title, text;
	private Date date;

}
