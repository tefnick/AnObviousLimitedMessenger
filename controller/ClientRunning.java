package controller;
/**
 * Creates a new Thread for the client running.
 * 
 * @author kbarbora
 *
 */
public class ClientRunning extends Thread
{
	/**
	 * A Client Controller for the Client Model
	 */
	public ClientController cc;
	/**
	 * Constructs a new ClientRunnig with the controller as parameter
	 * @param cController
	 */
	public ClientRunning( ClientController cController )
	{
		this.cc = cController;
	}
	
	/**
	 * Run the new created Thread
	 */
	public void run() {
		 // should execute until it fails
		if( !(cc.getClient().start()) ) {
			System.out.println("ClientRunning will not start ClientModel...");
		}
			
		
		// the server failed
		if( cc.getClient() == null )
			System.out.println("ClientController failed...");
	}
}
