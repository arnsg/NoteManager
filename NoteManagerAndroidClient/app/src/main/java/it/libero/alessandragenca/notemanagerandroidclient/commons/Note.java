package it.libero.alessandragenca.notemanagerandroidclient.commons;

import java.util.Date;

public class Note {
	
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
	
	private String title, text;
	private Date date;

}
