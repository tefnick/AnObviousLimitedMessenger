package controller;


import javax.swing.JTextArea;
import model.ServerModel;

/**
 * Controller for the server 
 * 
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 *
 */
public class ServerController {

	ServerModel server;
	JTextArea chat;
	ServerRunning serverRun;
	JTextArea lobbyView;
	JTextArea debugger;

	/**
	 * Constructs a new controller
	 * 
	 * @param portNumber An int value for the port number
	 * @param username	A String value for the username
	 * @param chat	The text area for the chat messages
	 * @param debugger	The text are for the debugger messages
	 */
	public ServerController(int portNumber, String username, JTextArea chat, JTextArea debugger) {
		// TODO Auto-generated constructor stub
		this.chat = chat;
		this.debugger = debugger;
		this.server = new ServerModel(portNumber, username, this);
	}
	/**
	 * Start a new thread for the server and connect to the
	 * client 
	 * @return A boolean value representing the start status of the method
	 */
	public void start() {
		// TODO Auto-generated method stub
		this.serverRun = new ServerRunning(this);
		this.serverRun.start();
	}
	/**
	 * Append this text to the chat text area
	 * @param text A String value representing the message to append
	 */
	public void appendChat(String message) {
		server.sendToClients(message);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	/**Append this event to the chat text area
	 * 
	 * @param string A String value representing the event to append
	 */
	public void appendEvent(String string) {
		debugger.append(string);
		chat.setCaretPosition(chat.getText().length() - 1);

	}

	/**
	 * Stop the server controller and the server thread
	 */
	public void stop() {
		this.serverRun.interrupt();
		this.server = null;
		this.serverRun = null;
	}
	/**
	 * Know if the debugger is active
	 * @return A boolean value whether the debugger mode is active
	 */
	public boolean debuggerWindow() {
		if(this.debugger != null)
			return true;
		else
			return false;
	}
	/**
	 * Getter for the server		
	 * @return The server for this controller
	 */
	public ServerModel getServer()
	{
		return server;
	}

	/**
	 * Setter for the server
	 * @param server The server for this controller
	 */
	public void setServer(ServerModel server)
	{
		this.server = server;
	}
}