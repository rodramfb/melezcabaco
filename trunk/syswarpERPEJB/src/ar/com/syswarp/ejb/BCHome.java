package ar.com.syswarp.ejb;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

public interface BCHome extends EJBHome {
	public BC create() throws CreateException, RemoteException;
	
}
