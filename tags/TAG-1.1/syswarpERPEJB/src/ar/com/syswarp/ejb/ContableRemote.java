package ar.com.syswarp.ejb;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

public interface ContableRemote extends EJBHome {
	public Contable create() throws CreateException, RemoteException;
	
}
