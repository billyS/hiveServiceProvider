package uk.co.billy.hive.doorlockservice.impl;

import uk.co.billy.hive.doorlockservice.api.DoorLockServiceAPI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DoorLockServiceImpl implements DoorLockServiceAPI{

			//public static int servicePort   = 80;			//Raspberry Pi doorlock port //possibly change this
			public static int testServicePort = 50004;
			public boolean stopnow 			  = false;
			//public String publicUniIP  	  = "89.243.62.195";
			//public String publicHomeIP      = "88.107.65.111";
			//public String   internalIP      = "192.168.1.6";	// Raspberry Pi
			public String   internalIP      = "192.168.1.7";	// Mac test server 
			private String doorState          = "";
			private ObjectOutputStream out ;
			Socket clientSocket=null;	
			ServerClient client = null;
			
		public static void main(String[] args) {}
		public DoorLockServiceImpl() {}
			
		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#getDoorState()
		 */
		public String getDoorState() {
			
			return doorState;
		}

		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#sendCommand(java.lang.String)
		 */
		public void sendCommand(String command) {
			out = client.getTheOut();
			 try {
		        	if(out != null){
		        		out.writeObject(command);
		            }
				}catch(Exception e) {
					e.printStackTrace();
				}	
		}

		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#setDoorState(java.lang.String)
		 */
		public void setDoorState(String message) {
			doorState = message;
		}

		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#startService()
		 */
		public void startService() {
			stopnow = false;
			//new Thread(this).start();
			makeContactWithServer();   
		}
		
		public void makeContactWithServer(  ) {
		    Socket tcpS;
		    try {
		        tcpS = new Socket("192.168.1.7", 50004);
		        client = new ServerClient(tcpS);
		        client.start();
		    }
		    catch ( Exception err ) {
		      
		    }
		  }

		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#stopServiceAsync()
		 */
		public void stopServiceAsync() {
			// assume stop service successful
					stopnow = true;
					try {
						if (clientSocket != null) {
							clientSocket.close();
							clientSocket = null;
						}
					}catch (Exception ex) {
						ex.printStackTrace();
					}
		}
		
		
		
		class ServerClient extends Thread {
		    private Socket          theSocket;            		// Socket used
		    private ObjectInputStream theIn  = null;        	// Input stream
		    private ObjectOutputStream theOut = null;        	// Output stream


		    /**
		     * Constructor
		     * @param model - model of the game
		     * @param s - Socket used to communicate with server
		     */
		    public ServerClient( Socket s ) {
		  	  
		      theSocket       = s;                     // Remember socket
		      try {
		      	theOut = new ObjectOutputStream(theSocket.getOutputStream());
		  		theIn = new ObjectInputStream(theSocket.getInputStream());
		  		
		  	} catch (IOException e) {
		  		// TODO Auto-generated catch block
		  		e.printStackTrace();
		  	}
		    }

		    
		    public void run()                             // Execution
		    {
		      // Listen to network to get the latest state from the server
		      try
		      {
		        while ( true )                           // Loop
		        {
		          String mes = (String) theIn.readObject();
		          if ( mes == null ) break;              // No more data 
		        }

		        theIn.close();                            // Close Read
		        theOut.close();                           // Close Write

		        theSocket.close();                        // Close Socket
		      }
		      catch ( Exception err )
		      {
		        err.printStackTrace();
		       
		      }

		    }
		    
		    private ObjectOutputStream getTheOut(){
		    	return theOut;
		    }
		  }

		
		
		
		
		
}
