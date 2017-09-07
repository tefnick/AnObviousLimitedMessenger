package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.ClientController;
/**
 * The client listener and the reaction for the event produced
 * 
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 *
 */
public class ClientListener implements ActionListener
{
	/**
	 * The main message frame
	 */
	private MessageFrame mf;
	/**
	 * The frame for the dialog boxes produced
	 */
	private JFrame frame;
	/**
	 * A Menu item to start the server
	 */
	private JMenuItem startS;
	/**
	 * A menu item to start the client
	 */
	private JMenuItem startC;
	
	/**
	 * Constructs a client listener and initialize fields
	 * @param mFrame
	 */
	public ClientListener( MessageFrame mFrame )
	{
		mf = mFrame;
		frame = mFrame.getFrame();
		startS  = mFrame.getStartAsServerMenuItem();
		startC = mFrame.getStartAsClientMenuItem();		
	}

	/**
	 * Listens and react to the event produced by the client
	 * @param e The event produced by the component
	 */
	public void actionPerformed(ActionEvent e)
	{					
			// Dialog boxes for client
			Object[] possibilities = null;
			String serverAddress = (String) JOptionPane.showInputDialog(frame, "Server address:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "localhost");
			String port = (String) JOptionPane.showInputDialog(frame, "Port Number:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "1500");
			String username = (String) JOptionPane.showInputDialog(frame, "Username:\n", "Chat Server Settings",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "username");

			// If a string was returned, say so.
			if ((serverAddress != null && port != null && username != null) 
					&& (serverAddress.length() > 0 && port.length() > 0) && username.length() > 0) {
				System.out.println("Starting up client...");
				System.out.println("\tServer address: " + serverAddress + "\n\tUsername: " + username + "\nPort #: " + port +"\n");

				// setting private username temporarily to test message capturing
				mf.setUsername(username);
				mf.setPortNumber(Integer.parseInt(port));
				mf.setServerName(serverAddress);

				mf.setClientController( new ClientController( mf.getServerName(), mf.getPortNumber(), username, mf.getChat(), mf.getLobby() ));

				mf.append("Trying to start connection to server...\n");

				if (!mf.getClientController().start()){
					System.out.println("ClientController failed...");
					return;
				}
				mf.setSignedIn(true);
				startS.setEnabled(false);
				startC.setEnabled(false);
				mf.getLogout().setEnabled(true);
				mf.setTitle("AOL Messenger - connected as \'" + username + "\'");
		}
	}
}
