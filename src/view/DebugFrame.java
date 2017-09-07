package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
	 * This is the debugger frame that is created if specified during the setup 
	 * of a server
	 * 
	 * @author Chandler Ortman
	 */
public class DebugFrame extends JFrame implements ActionListener {
	
	/**
	 * Make eclipse happy
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JTextArea debugger;
	/**
	 * 
	 */
	private JScrollPane scrollPane;
	/**
	 * 
	 */
	public DebugFrame(){
		this.setTitle("DEBUG CONSOLE");
		this.setSize(350, 400);
		this.setAlwaysOnTop(true);
		this.debugger = new JTextArea();
		this.debugger.setAutoscrolls(true);
		this.scrollPane = new JScrollPane(debugger);
		this.scrollPane.setAutoscrolls(true);
		this.debugger.setEditable(false);
		this.debugger.setLineWrap(true);
		this.add(scrollPane);
	}
	/**
	 * Append the text to the debugger console
	 * @param string The text to be appended
	 */
	public void append(String string){ 
		this.debugger.append(string);
	}
	/**
	 * Reacts to the event produced
	 */
	public void actionPerformed(ActionEvent e) {}
	/**
	 * Getter for the debugger console
	 * @return A JTextArea representing the debugger text area
	 */
	public JTextArea getdebugger() {
		return this.debugger;
	}

}
