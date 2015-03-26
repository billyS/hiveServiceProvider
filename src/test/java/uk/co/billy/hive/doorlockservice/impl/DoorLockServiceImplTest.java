package uk.co.billy.hive.doorlockservice.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class DoorLockServiceImplTest {
	public static int testServicePort;
	public boolean stopnow;
	public String   internalIP;	// Mac test server
	private String doorState;
	
	@Mock
	private ObjectOutputStream out;
	@Mock
	private ObjectInputStream in;
	@Mock
	private Socket clientSocket;
	@InjectMocks
	private DoorLockServiceImpl service;
	@Mock
	private ServerSocket ss;
	
	@Before
	public void setUp() throws Exception {
		testServicePort = 50004;
		stopnow 	    = false;
		internalIP      = "192.168.1.7";
		doorState          = "locked";
	}

	@Test
	public void testRun() throws IOException  {
		
		Mockito.when(clientSocket.getInputStream())
		.thenReturn(in);
		
		Mockito.when(clientSocket.getOutputStream())
		.thenReturn(out);
		
	}

	@Test
	public void testSendCommand()  {
		
	}

	@Test
	public void testSetDoorState() throws ClassNotFoundException, IOException{
		Mockito.when(clientSocket.getInputStream())
		.thenReturn(in);
		
		Mockito.when(clientSocket.getOutputStream())
		.thenReturn(out);
		
		Mockito.when(in.readObject())
		.thenReturn(doorState);
		
		service.startService();
		
	}

	@Test
	public void testStartService() {
		fail("Not yet implemented");
	}

	@Test
	public void testStopServiceAsync() {
		fail("Not yet implemented");
	}

}
