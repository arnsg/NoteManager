var Note = function (title, text, date) {
	  this.title = title;
	  this.text = text;
	  this.date = date;	
    };
    
    Note.prototype.setFromJSON = function(json) {
  	  this.title=json.title;
  	  this.text=json.text;
  	  this.date=json.date;
  	  
  	};
  	
  	Note.prototype.setText = function(text) {
  	  this.text=text;
    };
  	
  	Note.prototype.toString = function() {
  	  return "Note [title=" + this.title + ", text=" + this.text + ", date=" + this.date + "]";	
    };
 