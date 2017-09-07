package view;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
/**
 * Create a preference JMenu with options to change the background color,
 * the font color and the font type
 * @author kbarbora
 *
 */
public class Preference extends JMenu {
	
	private static final long serialVersionUID = 17844;
	private MessageFrame mf;
	/**
	 * Construct a Preference objects with a MessaFrame as parameter
	 * @param frame A frame representing the the container for this menu
	 */
	public Preference(MessageFrame frame)
	{
		super("Preferences");
		mf = frame;
		JTextArea chat = mf.getChat();
		Lobby lobby = mf.getLobby();
		JMenuItem fontColor = new JMenuItem("Font Color");
		JMenuItem background = new JMenuItem("Background Color");
		fontColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
		fontColor.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				Color color = Wallpaper.showDialog(
						null, "Choose a color", chat.getForeground());
				if( color != null ){
					chat.setForeground(color);
					lobby.getLobbyArea().setForeground(color);
				}
				chat.repaint();
				lobby.getLobbyArea().repaint();
			}
		});
		background.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
		background.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				Color color = Wallpaper.showDialog(
						null, "Choose a color", chat.getBackground());
				if( color != null ){
					chat.setBackground(color);
					lobby.getLobbyArea().setBackground(color);
				}
				chat.repaint();
				lobby.repaint();
			}
		});
		add(fontColor);
		add(background);
	}
}