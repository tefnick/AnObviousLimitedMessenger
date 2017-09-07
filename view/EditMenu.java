package view;

import java.awt.event.*;
import javax.swing.*;
/**
 * Represent the edit menu for the main frame
 * 
 * @author jitwaru
 * @author kbarbora
 * @author Nick Ruiz
 * @author Chandler
 *
 */
public class EditMenu extends JMenu{
	
	/**
	 * Make eclipse happy again
	 */
	private static final long serialVersionUID = 48279; //random
	/**
	 * The main message frame
	 */
	private MessageFrame mf;
	/**
	 * Constructs an Edit menu and its submenus
	 * @param frame The main frame where the edit menu is located
	 */
	public EditMenu( MessageFrame frame){
		super("Edit");
		mf = frame;
		JTextField text = mf.getText();
		JTextArea chat = mf.getChat();
		
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		JMenuItem cut = new JMenuItem("Cut");
		add(copy);
		add(cut);
		add(paste);
		cut.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  System.out.println("catched event " + e.getActionCommand());
		    	  chat.cut();
		    	  text.cut();
		      }});
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("catched event " + e.getActionCommand());
				chat.paste();
				text.paste();
			}});
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("catched event " + e.getActionCommand());
				chat.copy();
				text.copy();
			}});
		cut.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_C, ActionEvent.CTRL_MASK));
	}
}
