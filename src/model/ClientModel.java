package model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import controller.ClientController;
import controller.ListenFromServer;
import view.MessageFrame;

/**
 * Model portion of the app from which handles 
 * more of the user facing features of the app
 * @author Nick Ruiz
 */
public class ClientModel {

	// Creates a new socket for the client to communicate to the server	 
	private Socket socket;	
	
	//input stream to read from the socket	 
	private ObjectInputStream socketInput;
	
	//output stream to read from the socket
	private ObjectOutputStream socketOutput;
	
	//the server and the username
	private String serverName, userName;
	
	//the port number
	private int port;
	
	//the view
	private MessageFrame view;
	
	// ClientController
	private ClientController cController;
	
 	
	/**
	 * Constructor for the Client model.
	 * @param serverName
	 * @param port
	 * @param userName
	 */
	public ClientModel(String serverName, int port, String userName, ClientController cController){
		this.serverName = serverName;
		this.port = port;
		this.userName = userName;
		this.cController = cController;
	}
	
	/**
	 * starts the chat and tries to connect to the server.
	 * @return a boolean value if connection was successful.
	 */
	public boolean start(){
		try{
			socket = new Socket(serverName, port);
		} catch(Exception e){
			System.out.println(e); // do we need to show the stack trace of this exception or is the JOptionPane enough?
			return false; 
		}
		String statusMessage = "Connected to " + socket.getInetAddress()+ ":" + socket.getPort();
		cController.appendChat(statusMessage);
		
		try{
			socketOutput = new ObjectOutputStream(socket.getOutputStream());
			socketInput = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e){
			statusMessage = "Error setting up Input/Output stream";
			System.out.println(statusMessage);
			return false;
		}
		
		// creates the Thread to listen from the server
		ListenFromServer listen = new ListenFromServer(this, cController, socketInput);
		listen.start();
		try{
			//send userName to the server.. note that any other messages sent to the server
			//will be a Message object defined from Message.java
			socketOutput.writeObject(userName);
		}catch(IOException e){
			System.out.println("Error logging in");
			disconnect();
			return false;
		}
		
		sendMessage(new Message(Message.LOGIN, ""));
		//otherwise if everything is successful, return true
		return true;
		
	} //end start method
	
	/**
	 * 
	 */
	public void getClientList() {
		sendMessage(new Message(Message.USERSONLINE, ""));
	}


	/**
	 * Send message and update the GUI
	 */
	public void display(String msg){
		view.append(msg + "\n");
	}
	
	/**
	 * Sends a message to the server
	 * @param msg
	 */
	public void sendMessage(Message msg){
		try {
			socketOutput.writeObject(msg);
		}catch(IOException e){
			System.out.println("I/O Error trying to sendMessage: " + e.toString());
		}
	}
	
	/**
	 * Disconnects from the server
	 */
	private void disconnect() {
		//close input Stream
		try { 
			if(socketInput != null){
				socketInput.close();
			}
		}
		catch(Exception e) {
		
		//close output stream	
		} 
		try {
			if(socketOutput != null) {
				socketOutput.close();
			}
		}
		catch(Exception e) {
			
		} 
		//lastly, close the socket
        try{
			if(socket != null) {
				socket.close();
			}
		}
		catch(Exception e) {
		} 
	}

	/**
	 * Returns the socket Input
	 */
	public ObjectInputStream getSocketInput(){
		return this.socketInput;
		
	}
	/**
	 * Returns the socket output
	 */
	public ObjectOutputStream getSocketOutput(){
		return this.socketOutput;
	}

	/**
	 * Returns the view
	 * @return
	 */
	public MessageFrame getView() {
		return view;
	}
	

	//setters
	/**
	 * updates the server name
	 * @param serverName
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * updates the username
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * updates the port number
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * 
	 * @param string
	 */

	
}
