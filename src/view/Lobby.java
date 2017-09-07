package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
/**
 * Creates a Lobby panel for the user connected in the server
 * @author kbarbora
 * @author Nick Ruiz
 *
 */
public class Lobby extends JPanel
{

	/**
	 * Random value to make eclipse happy
	 */
	private static final long serialVersionUID = 167547; //random
	private JTextArea lobbyArea;
	
	
	/**
	 * Construct the lobby panel for the users signed in
	 * @param frame A MessageFrame value representing the main frame
	 * @param s A ServerModel value representing the server
	 */
	public Lobby( MessageFrame frame)
	{
		super( new GridLayout(1,1) );
		lobbyArea = new JTextArea();
		lobbyArea.setEditable(false);
		lobbyArea.setLineWrap(true);
		add(new JScrollPane(lobbyArea));
		lobbyArea.setBackground(Color.white);
		lobbyArea.setForeground(Color.darkGray);						
	}

	//getter
	/**
	 * Get's this JTextArea to be manipulated in other classes.
	 * @return
	 */
	public JTextArea getLobbyArea() {
		return lobbyArea;
	}
	/**
	 * Appends the username to the lobby
	 * @param string A String value representing the users to be appended
	 */
	public void appendLobby(String string) {
		lobbyArea.setText(string);
	}
}
