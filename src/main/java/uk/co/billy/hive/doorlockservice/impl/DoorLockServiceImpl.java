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
			
		public static void main(String[] args) {}
		public DoorLockServiceImpl() {}
			
		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#getDoorState()
		 */
		public String getDoorState() {
			
			return doorState;
		}

		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#run()
		 */
		public void run() {
			while(!stopnow) {
				Socket clientSocket=null;
				try {
					
					clientSocket = new Socket(internalIP, testServicePort);
					
					ObjectInputStream in =  new ObjectInputStream(clientSocket.getInputStream());
					out = new ObjectOutputStream(clientSocket.getOutputStream());
					
					//XXX may need BufferdReader for Arduino. above only for testing !!
					//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			        //String message = (String) inFromServer.readLine();
			        String message = (String) in.readObject();

					System.out.println("Current Door Lock State Sent from Server: " + message);
					if(message == "") break;
					if(message != "") setDoorState(message);

				}catch(IOException | ClassNotFoundException ex) {
					ex.printStackTrace();
				}finally {
					try {
						if (clientSocket != null) {
							clientSocket.close();
							clientSocket = null;
						}
					}catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}	

		}

		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#sendCommand(java.lang.String)
		 */
		public void sendCommand(String command) {
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
			new Thread(this).start();
		}

		/* (non-Javadoc)
		 * @see uk.co.hive.doorlockservice.api.DoorLockServiceAPI#stopServiceAsync()
		 */
		public void stopServiceAsync() {
			// assume stop service successful
					stopnow = true;
		}
}
