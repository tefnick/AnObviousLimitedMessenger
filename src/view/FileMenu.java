package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
/**
 * Creates a File Menu and its submenus for the main message frame.
 * 
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 */
public class FileMenu extends JMenu{
	
	/**
	 * Make eclipse happy
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The main message frame
	 */
	private MessageFrame mf;
	/**
	 * A submenu to start as a server
	 */
	JMenuItem startAsServerMenuItem;
	/**
	 * A submenu to start as a client
	 */
	JMenuItem startAsClientMenuItem;
	/**
	 * A submenu to logout the client
	 */
	JMenuItem logout;
	/**
	 * A submenu to stop the server and close connections
	 */
	JMenuItem stopServer;
	/**
	 * A submenu to start server/client
	 */
	JMenu start;
	
	/**
	 * Constructs this file menu for the main message frame
	 * @param frame
	 */
	public FileMenu(MessageFrame frame){
		super("File");
		this.mf = frame;
		
		// Constructing menu items
 		this.start = new JMenu("Start");
		this.startAsServerMenuItem = new JMenuItem("Start Server");
		this.startAsClientMenuItem = new JMenuItem("Connect as Client");
		this.logout = new JMenuItem("Logout");
		this.stopServer = new JMenuItem("Stop Server");
		
		// Add StartServer/StartClient to start menu
		start.add(startAsServerMenuItem);
		start.add(startAsClientMenuItem);
		
		// Add menu items to File menu
		this.add(start);
		this.addSeparator();
		this.add(logout);
		this.add(stopServer);

		// Add keystroke accelerators/action listeners/ExitListener
		startAsServerMenuItem.setAccelerator(KeyStroke.getKeyStroke(
		       KeyEvent.VK_S, ActionEvent.ALT_MASK));
		startAsClientMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_H, ActionEvent.ALT_MASK));
		startAsServerMenuItem.addActionListener(mf);
		startAsClientMenuItem.addActionListener(mf);
		ExitListener exit = new ExitListener(mf);
		stopServer.addActionListener(exit);
		logout.addActionListener(exit);
		
		
		logout.setEnabled(false);
		stopServer.setEnabled(false);
	}

	/**
	 * Sign in for the server, starter
	 */
	public void serverSignedIn() {
		this.stopServer.setEnabled(true);
		this.startAsServerMenuItem.setEnabled(false);
		this.startAsClientMenuItem.setEnabled(false);
		this.mf.setTitle("AOL Messenger - hosting chat as \'" + mf.getUsername() + "\'");
	}

	/**
	 * Sign out for the server, close connections
	 */
	public void serverSignedOut() {
		this.stopServer.setEnabled(false);
		this.startAsServerMenuItem.setEnabled(true);
		this.startAsClientMenuItem.setEnabled(true);
		this.mf.setTitle("AOL Messenger");
	}
	
	/**
	 * Notifies whenever a client just signed in
	 */
	public void clientSignedIn() {
		this.logout.setEnabled(true);
		this.startAsServerMenuItem.setEnabled(false);
		this.startAsClientMenuItem.setEnabled(false);
		this.mf.setTitle("AOL Messenger - connected as \'" + mf.getUsername() + "\'");
	}
	
	/**
	 * Notifies whenever a client sign out
	 */
	public void clientSignedOut() {
		this.logout.setEnabled(false);
		this.startAsServerMenuItem.setEnabled(true);
		this.startAsClientMenuItem.setEnabled(true);
		this.mf.setTitle("AOL Messenger");
	}
}
