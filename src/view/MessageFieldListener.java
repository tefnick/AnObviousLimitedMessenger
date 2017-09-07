package view;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
/**
 * Create a Listener for the message field to react when a event is produced
 * @author kbarbora
 *
 */
public class MessageFieldListener implements ActionListener, FocusListener
{
	/**
	 * The message to be send
	 */
	private JTextField message;
	/**
	 * The send button for the message
	 */
	private JButton sendButton;
	
	/**
	 * Constructor for the message field listener
	 * @param m The main message frame
	 */
	public MessageFieldListener(MessageFrame m)
	{
		message = m.getText();
		sendButton = m.getSendButton();
	}
	/**
	 * Listen and Reacts to the event produced
	 * @param e The event produced by the message field
	 */
	public void actionPerformed(ActionEvent e)
	{
		sendButton.doClick();
	}
	/**
	 * Listen and reacts whenever the focus is gained
	 * @param arg A focus event produced by this message field
	 */
	public void focusGained(FocusEvent arg)
	{
		if(message.getText().equals("Enter message here..."))
			message.setText("");
		message.setForeground(Color.BLACK);
		
	}
	/**
	 * Listen and reacts whenever the focus is lost
	 * @param arg A focus event produced by this message field
	 */
	public void focusLost(FocusEvent arg)
	{
		if(message.getText().equals(""))
			message.setText("Enter message here...");
		message.setForeground(Color.GRAY);
	}

}
