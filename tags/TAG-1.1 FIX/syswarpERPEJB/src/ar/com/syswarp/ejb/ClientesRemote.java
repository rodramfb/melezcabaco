package ar.com.syswarp.ejb;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

public interface ClientesRemote extends EJBHome {
	public Clientes create() throws CreateException, RemoteException;
	
}
