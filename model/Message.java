package model;
import java.io.Serializable;

/**
 * A java object that is used to encapsulate data
 * for transferring messages from the client to the server and vice versa.
 * Sockets work nicer when handling java objects as opposed to Strings, int...etc
 * @author Nick Ruiz
 */
public class Message implements Serializable{

	protected static final long serialVersionUID = 3443;	//eclipse is happy!!!
	
	/**
	 * USERSONLINE displays an arrayList of users that are online in the chat
	 */
	public static final int USERSONLINE = 0;
	
	/**
	 * MESSAGE is a regular text message  in the chat
	 */
	public static final int MESSAGE = 1;
	
	/**
	 * LOGOUT to disconnect from the server
	 */
	public static final int LOGOUT = 2;
	
	/**
	 * LOGIN to the server/client
	 */
	public static final int LOGIN = 3;
	/**
	 * The message to be send
	 */
	private String message;	
	/**
	 * The type of event
	 */
	private int type;
	
	/**
	 * Constructor for Message
	 * @param type
	 * @param message
	 */
	public Message(int type, String message){
		this.type = type;
		this.message = message;
	}
	
	/**
	 * Get the type of action called from the client
	 * @return An integer of the type of message received from the client
	 */
	
	int getType() {
		return type;
	}
	
	/**
	 * Gets the text message sent by the client
	 * @return a String of the text message sent by the client.
	 */
	String getMessage() {
		return message;
	}
	/**
	 * Setter for the message
	 * @param readObject The Object containing the message
	 * @param type the type of object sent
	 */
	public void setMessage(Object readObject, int type) {
		this.message = readObject.toString();
		this.type = type;
	}
}
