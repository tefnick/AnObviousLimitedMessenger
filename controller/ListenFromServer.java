package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import model.ClientModel;
import view.MessageFrame;


/**
 * A class that waits for the message from the server and appends them to the JTextArea.
 * @author Nick Ruiz
 */
public class ListenFromServer extends Thread {
	
	/**
	 * Initializes the client model
	 */
	private ClientModel model;
	/**
	 * The controller of this client
	 */
	private ClientController controller;
	/**
	 * The socket input for incoming connections
	 */
	private ObjectInputStream socketInput;
	
	//initializes the view 
	MessageFrame view;

	/** Constructor 
	 * @param socketInput The input stream coming in */
	public ListenFromServer( ClientModel model, ClientController controller, ObjectInputStream socketInput) {
		this.model = model;
		this.controller = controller;
		this.socketInput = socketInput;
	}
	/**
	 * Run the new created Thread for the Server
	 * to listen for new incoming clients (threads)
	 */
	public void run() {
		
		while(true) {
			try {
				String msg = (String) socketInput.readObject();
				
				if(msg.startsWith("CONNECTED:")){
					String[] tokens = msg.split("\n");
					for(int i = 1; i < tokens.length; i++){
						controller.addToCurrentUsers(tokens[i]);
					}
					controller.updateCurrentUsers();
				}
				else if(msg.contains("has just connected")) {
					controller.appendChat(msg);
					String[] tokens = msg.split(" ");
					controller.addToCurrentUsers(tokens[1]);
					controller.updateCurrentUsers();
				}
				else if(msg.contains("has signed out")) {
					controller.appendChat(msg);
					String[] tokens = msg.split(" ");
					controller.removeFromCurrentUsers(tokens[1]);
					controller.updateCurrentUsers();
				}
				else{
					controller.appendChat(msg);
				}

			}
			catch(IOException e) {
				controller.appendChat("Server has closed the connection...");
				try {
					socketInput.close();
				} catch (IOException e2) {
					System.out.println("Unable to close socketInput: " + e2.toString());
				}
				break;
			}
			// can't happen with a String object but need the catch anyhow
			catch(ClassNotFoundException e2) {
				System.out.println("That thing that I didn't think could happen has happened...");
				e2.printStackTrace();
				break;
			}
		}
	}
}