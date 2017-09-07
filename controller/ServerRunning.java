package controller;

/**
 * Creates a new Thread for the Server Model
 * @author kbarbora
 *
 */
public class ServerRunning extends Thread
{
	/**
	 * The controller of the server
	 */
	private ServerController sc;
	
	/**
	 * Constructs a new ServerRunning thread
	 * @param sController The controller of the server
	 */
	public ServerRunning( ServerController sController )
	{
		sc = sController;
	}
	/**
	 * Run the thread
	 */
	public void run() {
		 // should execute until it fails
		sc.getServer().start();
		
		// the server failed
		sc.setServer(null);//server = null;
	}
	/**
	 * Close the thread and the server
	 */
	public void close() {
		sc.getServer().stop();
		sc.setServer(null);
	}
	
}
