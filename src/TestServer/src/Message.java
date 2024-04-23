import java.io.Serializable;
import java.lang.annotation.ElementType;

public class Message implements Serializable {
    protected final Type type;
    protected Status status;
    protected String text;

    public Message(){
        this.type = Type.UNDEFINED;
        this.status = Status.NOT_SERVICED;
        this.text = "Undefined";
    }

    public Message(Type type, Status status, String text){
        this.type = type;
        this.status = status;
        this.text = text;
    }
    
    //login/logout message
    public Message(Type type) {
	    this.type = type;
	    this.status = Status.NOT_SERVICED;
	    this.text = "";
    }

//    private void setType(Type type){
//    	this.type = type;
//    }

    public void setStatus(Status status){
    	this.status = status;
    }

    public void setText(String text){
    	this.text = text;
    }

    public Type getType(){
    	return type;
    }

    public Status getStatus(){
    	return status;
    }

    public String getText(){
    	return text;
    }
    

}
