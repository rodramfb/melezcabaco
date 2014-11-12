package ar.com.syswarp.ejb;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

public interface GeneralRemote extends EJBHome {
	public General create() throws CreateException, RemoteException;
}
