package uk.co.billy.hive.doorlockservice.impl;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;

import uk.co.billy.hive.doorlockservice.api.DoorLockServiceAPI;

@RunWith(MockitoJUnitRunner.class)
public class ActivatorTest {

	private String serviceAPIName;
	@Mock
	private DoorLockServiceImpl service;
	@Mock
	private BundleContext context;
	@InjectMocks
	private Activator activator;
	
	private Hashtable <String, String>dict;
	
	@Before
	public void setUp() throws Exception {
		serviceAPIName = "uk.co.billy.hive.doorlockservice.api.DoorLockServiceAPI";
		//service = new DoorLockServiceImpl();
		dict = getTestServiceProperties("valid");
		
	}

	@Test
	public void testStart() {
		
		activator.start(context);
		assertNotNull(context);
		assertNotNull(dict);
		Mockito.verify(context, Mockito.atLeastOnce())
		.registerService(Mockito.any(String.class), Mockito.any(DoorLockServiceImpl.class), Mockito.any(Hashtable.class));
		
		//activator.start(context);
		
		//fail("Not yet implemented");
	}

	@Test
	public void testStop() {
		//fail("Not yet implemented");
	}
	
	public Hashtable <String, String> getTestServiceProperties(String type) {
		Hashtable <String, String> aDict = null;
		switch (type) {
		case "valid":
			aDict = new Hashtable<String, String>();
			aDict.put("service", "TheHiveDoorlockService");
			aDict.put("service.exported.interfaces", "*");
			aDict.put("service.exported.configs", "org.apache.cxf.ws");
			aDict.put("org.apache.cxf.ws.address", "http://localhost:9090/doorlock");
			break;
		case "invalid":
			 
			break;
		}
		return aDict;
	}

}
