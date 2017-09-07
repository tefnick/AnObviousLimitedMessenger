package view;
import javax.swing.*;

import java.awt.event.*;
import java.io.*;

/**
 * Create the tools menu for the main frame
 * @author kbarbora
 *
 */
public class ToolMenu extends JMenu 
{

	/**
	 * Make eclipse happy
	 */
	private static final long serialVersionUID = 11269; //random
	/**
	 * The main message frame
	 */
	private MessageFrame mf;
	/**
	 * The chat text to be imported
	 */
	private String chat;
	/**
	 * The writer for the file	
	 */
	private FileWriter output;
	/**
	 * The file to be imported
	 */
	private File file;
	
	/**
	 * Construct a ToolMenu instance with the message frame as parameter
	 * @param frame A MessageFrame value representing the container of this JMenu
	 * @throws IOException
	 */
	public ToolMenu( MessageFrame frame ) throws IOException
	{
		super("Tools");
		mf = frame;
		JFileChooser save = new JFileChooser();
		file = new File("/home/users"); // pathname for Linux, Mac
		save.setCurrentDirectory( file );
		JMenuItem imp = new JMenuItem("Import Log");
		imp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				chat = mf.getChat().getText();
				int ret = save.showSaveDialog(null);
				if( ret == JFileChooser.APPROVE_OPTION){
					try{
						 output = new FileWriter( save.getSelectedFile() + ".txt");
				            writeInformation();
				            closeFile();
					}
					catch (IOException exc){exc.printStackTrace();}}
			}});
		add(imp);	
	}

	/**
	 * Adds information to the transcript.
	 * @param nextLine The next line to add to the transcript file
	 * @throws IOException 
	 */
	private void writeInformation() throws IOException
	{
		output.write(chat);
	}
	
	/**
	 * Closes the file so that the data is saved to the file.
	 * @throws IOException 
	 */
	private void closeFile() throws IOException
	{
		output.close();
	}
}
