package view;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import controller.*;
import model.*;

/**
 * Then main frame for the app and its proper listeners.
 * Contains the chat area, the chat field text, the send button and the menus
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 *
 */
public class MessageFrame extends JFrame implements ActionListener, WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3443;
	/**
	 * The frame for the dialog boxes
	 */
	private JFrame frame;
	/**
	 * The chat area fro the messages
	 */
	private JTextArea chat;
	/**
	 * The lobby for the usernames
	 */
	private Lobby lobby;
	/**
	 * The Text area for the message
	 */
	private JTextField text;
	/**
	 * The menu bar for this frame
	 */
	private JMenuBar menuBar;
	/**
	 * The submenus for the menu bar
	 */
	public JMenu menu, submenu;
	/**
	 * a submenu for the menu bar
	 */
	public JMenuItem menuItem;
	/**
	 * submenu to start the server
	 */
	public JMenuItem startAsServerMenuItem;
	/**
	 * submenu to start a client
	 */
	public JMenuItem startAsClientMenuItem;
	/**
	 * the send button for the message
	 */
	public JButton sendButton;
	/**
	 * The boolean value whether there is a user signed in
	 */
	private Boolean signedIn;
	/**
	 * The submenus to stop/logout
	 */
	private JMenuItem logout, stopServer;
	/**
	 * the listener for the message field
	 */
	private MessageFieldListener mListener;
	

	/*
	 * Following variables are only temporary, more than
	 * likely will not be necessary after initial implementation
	 */
	private String username;
	public String servername;
	public int portNumber;
	public ServerModel server;
	public ServerController serverController;
	public ClientModel client; 
	public ClientController clientController;
	private int debuggerFlag;
	private FileMenu fileMenu;
	private EditMenu edit;
	private Preference p;

	
	
	/**
	 * Constructs a MessageFrame instance with a menu, a chat area,
	 * a text field and a send button with its respective listeners
	 */
	public MessageFrame(){
		super("AOL Messenger");
		
		server = null;
		client = null;
		signedIn = false;
		

		
		// Text Box for entering messages
		JPanel textBox = new JPanel(new GridLayout(1, 2));
		this.text = new JTextField("Enter message here...");
		text.setForeground(Color.GRAY);
		sendButton = new JButton("SEND");
		sendButton.addActionListener(this);
		sendButton.setToolTipText("Send this message");
		textBox.add(text);
		textBox.add(sendButton);
		add(textBox, BorderLayout.SOUTH);

		// JPanel for chat room 
		JPanel chatBox = new JPanel(new GridLayout(1,1));
		chat = new JTextArea("Welcome to AOL Messenger!\n", 30, 30);
		chat.setEditable(false);
		chat.setLineWrap(true);
		chatBox.add(new JScrollPane(chat));
		add(chatBox, BorderLayout.CENTER);
		

		//JPanel for lobby
		lobby = new Lobby(this);
		add(lobby, BorderLayout.EAST);
		
		// JPanel for menu bar
		// Constructing menu bar
		menuBar = new JMenuBar();
		
		// Construct/add File Menu 
		this.fileMenu = new FileMenu(this);
		menuBar.add(fileMenu);
	
		// Construct/add edit menu
		this.edit = new EditMenu(this);
		menuBar.add(edit);
		
		// Construct/add preference menu
		this.p = new Preference(this);
		menuBar.add(p);
		
		// Construct/add tool menu
		ToolMenu tool = null;
		try {
			tool = new ToolMenu(this);
			menuBar.add(tool);} 
		catch (IOException e1) {e1.printStackTrace();}
		
		mListener = new MessageFieldListener(this);
		text.addActionListener(mListener);
		text.addFocusListener(mListener);

		HelpMenu help = new HelpMenu(this);
		menuBar.add(help);
		setJMenuBar(menuBar);
		addWindowListener(this);
		setSize(600, 600);
		setResizable(true);
		setVisible(true);
		setLocation(425, 150);
	}
	
	/**
	 * Listener for the event produce in one of the components
	 * in this frame.
	 * 
	 * @param e The event produced by one of the components of this frame
	 */
	public void actionPerformed(ActionEvent e) {
				
		// If specified server from menu bar
		if(e.getActionCommand().equals("Start Server")){
			
			// Dialog boxes for Server
			Object[] possibilities = null;
			String port = (String) JOptionPane.showInputDialog(frame, "Port Number:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "1500");
			String username = (String) JOptionPane.showInputDialog(frame, "Username:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "host");
			
			
			// Prompt for debugger console
			JTextArea debugger = null;
			DebugFrame debugFrame = null;
			this.debuggerFlag = JOptionPane.showConfirmDialog(
				    frame,
				    "Would you like to enable the debugging console?\n",
				    "Warning: Server is still active",
				    JOptionPane.YES_NO_OPTION);
				
				// User selected YES to close
				if(debuggerFlag == 0){
					debugFrame = new DebugFrame();
					debugger = debugFrame.getdebugger();
					debugFrame.setVisible(true);
				}
				// User selected NO to keep open
				else {
					debugger = null;
				}

			//If a string was returned, say so.
			if ((port != null && username != null) && (port.length() > 0) && username.length() > 0) {
				
				if(debuggerFlag == 0){					
					debugFrame.append("Starting up server...\n");
					debugFrame.append("Username: " + username + "\nPort #: " + port +"\n\n");
				}
				else{ 
					System.out.println("Starting up server...");
				    System.out.println("Username: " + username + "\nPort #: " + port +"\n");
				}
							    
			    
			    // setting private username temporarily to test message capturing
				this.username = username;
				this.portNumber = Integer.parseInt(port);
			
				// consruct server and start
				this.serverController = new ServerController(portNumber, username, chat, debugger);
				this.serverController.start();
				
				// construct client and start connection to the server
				this.clientController = new ClientController("localhost", portNumber, username, chat, lobby);
				append("Trying to start server and connect as host...\n");
				if (!clientController.start()){
					if(debuggerFlag == 0){
						debugFrame.append("ClientController failed...\n");
					}
					else{
						System.out.println("ClientController failed...");
					}
					return;
				}
				signedIn = true;
				fileMenu.serverSignedIn();
			}
			// return necessary to properly escape from actionListener 
			return;
		}
		
		// If specified client from menu bar
		if(e.getActionCommand().equals("Connect as Client")){					
			
			// Dialog boxes for client
			Object[] possibilities = null;
			String serverAddress = (String) JOptionPane.showInputDialog(frame, "Server address:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "localhost");
			String port = (String) JOptionPane.showInputDialog(frame, "Port Number:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "1500");
			String username = (String) JOptionPane.showInputDialog(frame, "Username:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "username");
			
			// Setting debug flag to avoid errors
			debuggerFlag = 1;
			DebugFrame debugFrame = new DebugFrame();
			
			// If a string was returned, say so
			if ((serverAddress != null && port != null && username != null) 
					&& (serverAddress.length() > 0 && port.length() > 0) && username.length() > 0) {
				
				
				if(debuggerFlag == 0){
					debugFrame.append("Starting up client...\n");
					debugFrame.append("Server address: " + serverAddress + "\nUsername: " + username + "\nPort #: " + port +"\n\n");
				}
				else{ 
					// System.out.println("Starting up client...");
					// System.out.println("Server address: " + serverAddress + "\nUsername: " + username + "Port #: " + port +"\n");
				}
				
				
				// setting private client variables
				this.username = username;
				this.portNumber = Integer.parseInt(port);
				this.servername = serverAddress;
				
				// construct client
				this.clientController = new ClientController(servername, portNumber, username, chat, lobby);
				
				// startup client connection
				debugFrame.append("Trying to start connection to server...\n");
				if (!clientController.start()){
					if(debuggerFlag == 0){
						debugFrame.append("ClientController failed...\n");
					}
					else{
						System.out.println("ClientController failed...");	
					}
					return;
				}
				signedIn = true;
				fileMenu.clientSignedIn();

			}
			// return necessary to properly escape from actionListener 
			return;
		}
		if(e.getActionCommand().equals("SEND")){
			if(signedIn){
				// Construct message w/ type MESSAGE and text from text-field
				Message message = new Message(Message.MESSAGE,username + ": " + text.getText());
				clientController.sendToChat(message);
				text.setText("");
				return;
			}
			if(!signedIn){
				append("CANNOT SEND MESSAGE UNTIL SERVER IS STARTED OR CLIENT HAS SIGNED IN\n");
				return;
			}
		}
	}

	/**
	 * Listens and closes all the streams and end the server if it is running
	 * 
	 * @param e An WindowEvent representing produced by closing the window
	 */
	public void windowClosing(WindowEvent e) {
		if(signedIn){
			signedIn = false;
			try{
				clientController.signOut();
				clientController = null;
			}
			catch(Exception eClose){
				System.out.println("Something went wrong while closing the application: " + eClose.toString());
			}
		}
		// dispose the frame
		dispose();
		System.exit(0);
	}
	/**
	 * Appends the message to the chat room
	 * @param msg A string value representing the message to be send
	 */
	public void append(String msg) { 
		chat.append(msg);
		chat.setCaretPosition(chat.getText().length() - 1);
		
	}
	
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	/**
	 * Getter for the chat text area
	 * @return A TextArea value representing the chat text area
	 */
	public JTextArea getChat(){return chat;}
	/**
	 * Getter for the message text field 
	 * @return A TextField value representing the message to be send
	 */
	public JTextField getText(){return text;}
	/**
	 * Getter for the lobby panel
	 * @return A JPanel value representing the lobby 
	 */
	public Lobby getLobby(){return lobby;}
	/**
	 * Getter for the user boolean signedIn value
	 * @return A boolean value representing if the user name is signed
	 */
	public boolean signedIn(){return signedIn;}
	
	/**
	 * Getters and Setters for the different components and field of this message frame
	 * @return The getter for each field/component
	 * @param The setter parameter for each field/component
	 */
	public JButton getSendButton(){return sendButton;}
	public Boolean getSignedIn(){return signedIn;}
	public String getUsername(){return username;}
	public ClientController getClientController(){return clientController;}
	public JMenuItem getStartAsServerMenuItem(){return startAsServerMenuItem;}
	public JMenuItem getStartAsClientMenuItem(){return startAsClientMenuItem;}
	public JMenuItem getLogout(){return logout;}
	public void setSignedIn(Boolean signedIn){this.signedIn = signedIn;}
	public void setClientController(ClientController clientController){this.clientController = clientController;}
	public ServerController getServerController(){return serverController;}
	public ServerModel getServer(){return server;}
	public void setServer(ServerModel server){this.server = server;}
	public void setClient(ClientModel client){this.client = client;}
	public JMenuItem getStopServer(){return stopServer;}
	public void setServerController(ServerController serverController){this.serverController = serverController;}
	public JFrame getFrame(){return frame;}
	public FileMenu getFileMenu() {return this.fileMenu;}
	public String getServername() {return servername;}

	public void setServerName(String servername) {this.servername = servername;}
	
	public String getServerName() {return servername;}

	public int getPortNumber() {return portNumber;}

	public void setPortNumber(int portNumber) {this.portNumber = portNumber;}

	public void setUsername(String username) {this.username = username;}	
}
