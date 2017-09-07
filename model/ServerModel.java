package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import controller.ServerController;
/**
 * The main class for the server logic and model
 * 
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 *
 */
public class ServerModel {
	
	/**
	 * The port number for the connexion
	 */
	private int port;
	/**
	 * The username for the server
	 */
	/**
	 * The clients connected to this server
	 */
	private ArrayList<ClientInstance> ClientList;
	/**
	 * Boolean value whether this server is enabled
	 */
	private boolean serverEnabled;
	/**
	 * The controller of this server
	 */
	private ServerController sController;
	/**
	 * The socket for the incoming connection
	 */
	private ServerSocket serverSocket;
	/**
	 * The type of message sent
	 */
	
	/**
	 * Constructor for server. Initializes ClientList, sets port and
	 * ServerView variables.
	 * @param actionListener 
	 */
	public ServerModel(int port, String username, ServerController sController){
		ClientList = new ArrayList<ClientInstance>();
		this.port = port;

		this.sController = sController;
	}
	
	/**
	 * Enable the debugger
	 * @return Boolean value whether the debugger is enabled
	 */
	public boolean debuggerEnabled(){
		if(sController.debuggerWindow())
			return true;
		else
			return false;
	}
	/**
	 * Will be called to start the server behavior
	 */
	public void start(){
		serverEnabled = true;
		
		
		try{
			// Establish a ServerSocket on specified port
			this.serverSocket = new ServerSocket(port);
			
			while(serverEnabled){
				// Display to ServerView that server is now waiting for 
				// clients on specified port 
				if(sController.debuggerWindow()){
					sController.appendEvent("Listening on port " + port + " for clients...\n");
				}
				else{
					System.out.println("Listening on port " + port + " for clients...");
				}
				
				if(serverEnabled == false)
					break;
				
				
				// Blocking method that listens and waits for connection, creates
				// new socket to the Client
				Socket socketToClient = serverSocket.accept();
				
				
				
				// If asked to stop while/during blocking method above?
				if(serverEnabled == false)
					break;
	
				// Create new client (constructor has caller pass itself), 
				// add to ClientList, and start
				ClientInstance clientT = new ClientInstance(socketToClient, ClientList.size(), this);
				
				ClientList.add(clientT);
				clientT.start();

			}
			
			// Server has been told to stop
			try{
				serverSocket.close();
				//Create loop to go through ClientList and close all sockets.
			}
			// Exception while trying to stop server
			catch (Exception e) {
				System.out.printf("Exception encountered while trying to stop server: %s\n", e);

			}
			
		}
		// Catching unsuccessful ServerSocket construction
		catch(IOException e){
				System.out.printf("Exception encountered during ServerSocket construction: %s\n", e);
		}
	}
	
	/**
	 * This method stops the server by setting the boolean value to false
	 */
	public synchronized void stop() {
		this.serverEnabled = false;
		
		
		for(int i = 0; i < ClientList.size(); i++){
			ClientInstance clientT = ClientList.get(i);
			clientT.prepareClose();
			ClientList.remove(i);

		}
	}
		
	/**
	 * This method displays messages to the server log (server-side)
	 * @param event The event (String) to be displayed
	 */
	public void displayToServer (String event) {
		sController.appendEvent(event);
	}
	
	/**
	 * This method sends a message to the ClientInstance threads
	 * @param message The message (String) to be sent
	 */
	public synchronized void sendToClients(String message) {
		// Append String with chat message to server view
		// Loop over clients and send message
		for(int i = 0; i < ClientList.size(); i++){
			ClientInstance clientT = ClientList.get(i);
			clientT.writeMessage(message);
		}	
	}
	
	/**
	 * This method removes a client from the server in the event
	 * that they've requested to logout
	 */
	public synchronized void removeFromServer(int clientID) { // assuming we always pass a valid clientID
		for(int i = 0; i < ClientList.size(); i++){
			ClientInstance clientT = ClientList.get(i);
			if(clientT.getID() == clientID) {
				ClientList.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Set the port number for this server
	 * @param port An int value representing the port number of this server
	 */
	public void setPort(int port)
	{
		this.port = port;
	}
	
	
	/**
	 * Returns the list of users online.
	 * @return an ArrayList of users
	 */
	public ArrayList<ClientInstance> getClientList(){
		return this.ClientList;
	}


	/**
	 * Updates the clients logged in
	 * @param clientID The client to be updated
	 */
	public void updateClientsList(int clientID) {
		for(int i = 0; i < ClientList.size(); i++){
			ClientInstance clientT = ClientList.get(i);
			if(clientT.getID() == clientID) {
				String output = "CONNECTED:\n";
				for(int j = 0; j < ClientList.size(); j++){
					ClientInstance clientJ = ClientList.get(j);
					
					output += clientJ.getUserName() + "\n";
				}
				clientT.writeMessage(output);
				return;
			}
		}
	}
}
