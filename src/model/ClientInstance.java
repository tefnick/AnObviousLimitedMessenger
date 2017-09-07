package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create a new client and its connection
 * 
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 *
 */
public class ClientInstance extends Thread{
	
	/**
	 * The server to connect
	 */
	public ServerModel server;
	/**
	 * The id of this client
	 */
	private int clientID;
	/**
	 * The username of this client 
	 */
	private String username;
	/**
	 * The socket for the connection
	 */
	private Socket socket;
	/**
	 * The input stream
	 */
	private ObjectInputStream streamInput;
	/**
	 * The Output stream
	 */
	private ObjectOutputStream streamOutput;
	/**
	 * The message received by the server
	 */
	private Message receivedMessage;
	/**
	 * Format for the Date
	 */
	private SimpleDateFormat dateFormatting;
	/**
	 * The date for the events
	 */
	private Date date;
	/**
	 * To Date in the String format
	 */
	private String dateToStr;
	/**
	 * Boolean value if there is a username logged in
	 */
	boolean loggedIn;
	
	/**
	 * Constructor for ClientInstance, creates Object input/output stream
	 * 
	 * @param socketToClient socket for client
	 * @param ID Client ID, set based on ClientList.size()
	 */
	public ClientInstance(Socket socketToClient, int ID, ServerModel serverModel) {
		this.clientID = ID;
		this.socket = socketToClient;
		this.server = serverModel;
		this.dateFormatting = new SimpleDateFormat("hh:mm:ss");
		
		if(server.debuggerEnabled()){
			server.displayToServer("Trying to create object input/output streams\n");
		}
		else{
			System.out.println("Trying to create object input/output streams");
		}
		try{
			if(server.debuggerEnabled()){
				server.displayToServer("Trying to create object input/output streams\n");
				server.displayToServer("\tSetting up OUTPUT...\n");
				streamOutput = new ObjectOutputStream(socket.getOutputStream());
				server.displayToServer("\tSetting up INPUT...\n");
				streamInput = new ObjectInputStream(socket.getInputStream());
				server.displayToServer("Successful at creating I/O stream!\n\n");
			}
			if(!server.debuggerEnabled()){
				System.out.println("Trying to create object input/output streams");
				System.out.println("\tSetting up OUTPUT...");
				streamOutput = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("\tSetting up INPUT...");
				streamInput = new ObjectInputStream(socket.getInputStream());
				System.out.println("Successful at creating I/O stream!\n");
			}
		
			
			try {
				this.username = (String) streamInput.readObject();
			} catch (ClassNotFoundException e) {
				if(server.debuggerEnabled()){
					server.displayToServer("\tError reading username: " + e.toString() + "\n");
				}
				if(!server.debuggerEnabled()){
					System.out.println("\tError reading username: " + e.toString());
				}
			}
			
			// create string with current time/date
			date = new Date();
			dateToStr = dateFormatting.format(date);
			
			server.sendToClients(dateToStr + " " + username + " has just connected");
		}
		catch (IOException e) {
			// Not sure how I'd fall into here
			if(server.debuggerEnabled()){
				server.displayToServer("Exception while trying to create object I/O streams!\n" + e.toString());
			}
			if(!server.debuggerEnabled()){
				System.out.println("Exception while trying to create object I/O streams!\n" + e.toString());
			}
		}
	}

	/**
	 * Run the client and threads
	 */
	public void run() {
		// Boolean value changes to true/false when logged in/out
		loggedIn = true;
		
		while(loggedIn){
			try{			
				// read a String (which is an object)
				receivedMessage = (Message) streamInput.readObject();
			}
			catch (IOException e){ 
				if(server.debuggerEnabled()){
					server.displayToServer("Exception trying to receive message from streamInput: " + e.toString() + "\n");
				}
				if(!server.debuggerEnabled()){
					System.out.printf("Exception trying to receive message from streamInput: %s", e);
				}
			}
			catch(ClassNotFoundException e){
					// Required by compiler
				if(server.debuggerEnabled()){
					server.displayToServer("Exception trying to receive message from streamInput: " + e.toString() + "\n");;
				}
				if(!server.debuggerEnabled()){
					System.out.printf("Exception trying to receive message from streamInput: %s", e);
				}
			}
			
			date = new Date();
			String dateToStr = dateFormatting.format(date);
			// message will consist of "username: message"
			String message = receivedMessage.getMessage();
			
			switch (receivedMessage.getType()){
			case Message.LOGIN:
				server.updateClientsList(clientID);
				break;
			case Message.MESSAGE:
				server.sendToClients(dateToStr + " " + message);
				break;
			case Message.LOGOUT:
				server.sendToClients(dateToStr + " " + username + " has signed out");
				loggedIn = false;
				break;
			}
		}
		server.removeFromServer(clientID);
		close();
	}
	/**
	 * Check if it is ready to close the client
	 */
	public void prepareClose(){
		loggedIn = false;
		
	}
	/**
	 * Close the incoming/outgoing connections
	 */
	public void close() {		
		// TODO Auto-generated method stub
		try{
			// Try to close streamOutput
			if(streamOutput != null)
				streamOutput.close();
		}
		catch (Exception e){
			if(server.debuggerEnabled()){
				server.displayToServer("Exception while trying to create object I/O streams!\n" + e.toString() + "\n");
			}
			if(!server.debuggerEnabled()){
				System.out.println("Exception while trying to create object I/O streams!\n" + e.toString());
			}
		}
		try{
			// Try to close streamInput
			if(streamInput != null)
				streamInput.close();
		}
		catch (Exception e){
			if(server.debuggerEnabled()){
				server.displayToServer("Exception while closing streamInput: " + e.toString() + "\n");
			}
			if(!server.debuggerEnabled()){
				System.out.printf("Exception while closing streamInput: %s", e);
			}
		}
		try{
			// Try to close socket
			if(socket != null)
				socket.close();
		}
		catch (Exception e){
			if(server.debuggerEnabled()){
				server.displayToServer("Exception while closing socket: " + e.toString() + "\n");
			}
			if(!server.debuggerEnabled()){
				System.out.printf("Exception while closing socket: %s", e);
			}
		}
	}

	/**
	 * Write the message to the chat text area
	 * @param message The message to write
	 * @return A boolean value whether this message was written
	 */
	public boolean writeMessage(String message) {
		// error checking, verifying client is still connected
		if(!socket.isConnected()){
			close();
			return false;	
		}
		
		// write the message
		try {
			streamOutput.writeObject(message);
			return true;
		}
		// if an error occurs
		catch (IOException e) {
			if(server.debuggerEnabled()){
				server.displayToServer("Error sending message to " + username + "\n");
			}
			if(!server.debuggerEnabled()){
				System.out.printf("Error sending message to " + username);
			}
			return false;
		}
	}


	/**
	 * This class is called by the ServerModel to verify a client's ID
	 * before removing client from Server
	 * @return client's ID
	 */
	public int getID() {
		return this.clientID;
	}
	
	/**
	 * Return username
	 * @return
	 */
	public String getUserName(){
		return this.username;
	}

}
