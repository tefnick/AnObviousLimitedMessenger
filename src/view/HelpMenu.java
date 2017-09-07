package view;

import java.awt.event.*;
import javax.swing.*;
/**
 * Create a Help menu and its submenus for the main message frame
 * @author kbarbora
 *
 */
public class HelpMenu extends JMenu implements ActionListener
{

	/**
	 * Make eclipse happy
	 */
	private static final long serialVersionUID = 1534; // random
	/**
	 * The submenus for this help menu
	 */
	private JMenuItem doc, about;
	/**
	 * The icon for the dialog boxes of the submenu
	 */
	private ImageIcon iconM, iconH;
	/**
	 * The Strings containing the help documentation
	 */
	private JList<String> list;
	/**
	 * The scroll bar for the help documentation
	 */
	private JScrollPane scroll;
	/**
	 * The main panel for the help documentation dialog box
	 */
	private JPanel panel;
	
	/**
	 * Constructs a Help Menu and its submenus
	 * @param frame
	 */
	public HelpMenu( MessageFrame frame)
	{
		super("Help");
//		mf = frame;
		doc = new JMenuItem("Documentation");
		about = new JMenuItem("About...");
		doc.addActionListener(this);
		about.addActionListener(this);
		add(doc);
		add(about);
		iconM = new ImageIcon("Messenger.png"); //We are not the author/creator of the icon
		iconH = new ImageIcon("help.png");	//We are not the author/creator of the icon
		String[] docText = {
			"Welcome to the Help Documentation",
			"The following are the procedures to run AOL Messenger",
			"First and the most important, we hope you have a computer with Java.",
			"Second you have to be connected to a local network.",
			"Third know the IP address of your server if you are client.",
			"\n",
			"Then just follow the steps to start server:",
			"1.Go to: File—> Start—> Start Server",
			"2.Choose the port number, and username.",
			"3.Now server is ready to start sending messages and listening for clients.",
			"\n",
			"If you are a client:",
			"1.Go to: File—> Start—> Connect as Client",
			"2.Type the server address, port number and username",
			"3.If the server is found, the messenger is ready to send messages.",
			"\n",
			"Once connected:",
			"You will see the send messages in the central chat panel,",
			"and to the right there is a panel with the users connected to the server.",
			"To send a message, you just type and enter/click send button.",
			"You can copy, cut and paste the recents messages in the Edit menu.",
			"To customise the window, the Preferences menu can change the background and font color.",
			"There is a way to import the activity in the chat, by simply going to", 
			"the Tools menu and selecting Import Log, then you choose the file name", 
			"and location to be saved.",
			"",
							};
		
	    list = new JList<String>(docText);
	    scroll = new JScrollPane(list); 
		panel = new JPanel(); 
	    panel.add(scroll);
	    scroll.getViewport().add(list);
		
	}
	
	/**
	 * Listens for the action and reacts to the event produced
	 * @param e The event produced by the menu/submenu
	 */
	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource().equals(doc))
		{
			JOptionPane.showMessageDialog(null, scroll,"Help Documentation",
					JOptionPane.INFORMATION_MESSAGE, iconH);
		}
		
		if( e.getSource().equals(about))
		{
			JOptionPane.showMessageDialog(null, 
					  "AOL Messenger \n"
					+ "Version 1.0                       \n" + 
					  "Authors:\n"
					+ "\t   Nick Ruiz\n\t   Kevin Barba\n\t   Julian Itwaru\n\t   Chandler Ortman"
					+ "\n\n ©All rights reserverd. 2016 AOL Messenger.",
					  "About this AOL Messenger",
					JOptionPane.INFORMATION_MESSAGE,
					iconM);
		}
	}

}
