package uk.co.billy.hive.doorlockservice.impl;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import uk.co.billy.hive.doorlockservice.api.DoorLockServiceAPI;
import uk.co.billy.hive.doorlockservice.impl.DoorLockServiceImpl;

public class Activator implements BundleActivator {

	private BundleContext m_context = null;
	private ServiceRegistration registration;
	private DoorLockServiceImpl service;
	 
	public void start( BundleContext context) {
		
		 m_context = context;
		
		 Hashtable<String, String> dict = new Hashtable<String, String>();
		 dict.put("service", "TheHiveDoorlockService");
		 dict.put("service.exported.interfaces", "*");
		 dict.put("service.exported.configs", "org.apache.cxf.ws");
		 dict.put("org.apache.cxf.ws.address", "http://localhost:9090/doorlock");
	
		 //service = new DoorLockServiceImpl();
	     //service.startService();
	     //m_context.registerService( DoorLockServiceImpl.class.getName(), service, dict);
	     registration = m_context.registerService(DoorLockServiceAPI.class.getName(), new DoorLockServiceImpl(), dict);
		
	}
	
	public void stop(BundleContext context) throws Exception
	{
		registration.unregister();
		/*ServiceReference[] refs = context.getServiceReferences( DoorLockServiceImpl.class.getName(), "(uk.co.billy.service.name=TheHiveDoorlockService)");
		
		if (refs != null) {
			((DoorLockServiceImpl) context.getService(refs[0])).stopServiceAsync();
		}*/
	}

}
