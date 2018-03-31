

var User= function (identifier,secret) {
	  this.identifier = identifier;
	  this.secret = secret;
	  	
    };
    
    User.prototype.setFromJSON = function(json) {
  	  this.identifier=json.identifier;
  	  this.secret=json.secret;
  	
  	  
  	};
  	
  	User.prototype.toString = function() {
  	  return "User [identifier=" + this.identifier + ", secret=" + this.secret +  "]";	
    };
 