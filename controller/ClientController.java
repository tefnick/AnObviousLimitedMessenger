package controller;
import java.util.ArrayList;
import javax.swing.JTextArea;
import model.ClientModel;
import model.Message;
import view.Lobby;
/**
 * Controls the clients instance to send the message to
 * the server.
 * 
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 *
 */
public class ClientController{
	private ClientModel client;
	private JTextArea chat;
	private Lobby lobby;
	private ArrayList<String> connectedClients;
	
	
	/**
	 * Constructor for the Client Controller
	 * @param lobby 
	 * @param model
	 * @param view
	 */
	public ClientController(String servername, int portNumber, String username, JTextArea chat, Lobby lobby) {
		// TODO Auto-generated constructor stub
		this.chat = chat;
		this.client = new ClientModel(servername, portNumber, username, this);
		this.lobby = lobby;
		connectedClients = new ArrayList<String>();
	}

	
	/**
	 * Send this message to the chat client
	 * @param msg A String value representing the message to be send
	 */
	public void sendToChat(Message msg){
		client.sendMessage(msg);
	}
	
	/**
	 * Append this text to the chat text area
	 * @param text A String value representing the message to append
	 */
	public void appendChat(String text) {
		chat.append(text + "\n");
		chat.setCaretPosition(chat.getText().length() - 1);	
	}

	/**Append this event to the chat text area
	 * 
	 * @param string A String value representing the event to append
	 */
	public void appendEvent(String string) {
		// TODO change output so events go to separate console
		chat.append(string);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	/**
	 * Start a new thread for the client and connect to the
	 * server which is ready to accept clients
	 * @return A boolean value representing the start status of the method
	 */
	public boolean start() {
		// TODO Auto-generated method stub
		try{
			new ClientRunning(this).start();
			return true;
		}
		catch (RuntimeException e){
			chat.append(e.toString());
			return false;
		}
	}
 
	/** 
	 * Updates the view if a user has signed out.
	 */
	public void signOut() {
		// TODO Auto-generated method stub
		sendToChat(new Message(Message.LOGOUT, ""));
	}
		
	/**
	 * updates the view of current users signed in
	 */
	public void updateCurrentUsers(){
		// connectedClients.add(username);
		String output = "CONNECTED:\n";
		
		for(int i = 0; i < connectedClients.size(); i++){
			output += connectedClients.get(i).toString() + "\n"; 
		}
		lobby.appendLobby(output);
	}


	/**
	 * Adds the current user to the connected clients
	 * @param string A String value representing the users connected
	 */
	public void addToCurrentUsers(String string) {
		// TODO Auto-generated method stub
		connectedClients.add(string);
	}


	/**
	 * Remove the current users the selected user
	 * @param string A String value representing the user to be removed
	 */
	public void removeFromCurrentUsers(String string) {
		for(int i = 0; i < connectedClients.size(); i++){
			if(connectedClients.get(i).equals(string)){
				connectedClients.remove(i);
			}
		}
	}
	/**
	 * Getter for the ClientModel
	 * @return A ClientModel instance
	 */
	public ClientModel getClient()
	{
		return client;
	}
}

