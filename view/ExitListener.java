package view;

import java.awt.event.*;
import javax.swing.JOptionPane;
/**
 * Listens whenever the user wants to logout/close server
 * @author kbarbora
 *
 */
public class ExitListener implements ActionListener
{
	/**
	 * The main message frame
	 */
	private MessageFrame mFrame;
	/**
	 * Constructor for the listener
	 * @param mf The main message frame
	 */
	public ExitListener( MessageFrame mf )
	{
		mFrame = mf;
	}
	/**
	 * Reacts to the event produced and processes it
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Logout")){
			mFrame.setSignedIn( false );
			mFrame.getClientController().signOut();
			mFrame.setClientController(null);
			mFrame.getFileMenu().clientSignedOut();
			return;
		}
		if(e.getActionCommand().equals("Stop Server")){

			int confirmServerClose = JOptionPane.showConfirmDialog(
					mFrame.getFrame(),
					"Are you sure you would like to stop the server?\n"
							+ "Any connected clients will be disconnected and the program will exit",
							"Warning: Server is still active",
							JOptionPane.YES_NO_OPTION);

			// User selected YES to close
			if(confirmServerClose == 0){
				try {
					mFrame.getServer().stop();			// ask the server to close the connection
					mFrame.setSignedIn(false);
					mFrame.getClientController().signOut();
				}
				catch(Exception eClose) {
				}
				mFrame.setServer( null );

				// dispose the frame
				mFrame.dispose();
				System.exit(0);
			}
			// User selected NO to keep open
			else {
				return;
			}	
		}
	}

}
