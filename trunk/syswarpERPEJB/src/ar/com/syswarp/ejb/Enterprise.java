package ar.com.syswarp.ejb;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.Local;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.math.*;
import javax.ejb.Local;
@Local

public interface Enterprise {
	public String getEmpresa() throws RemoteException;
	public void setEmpresa(String empresa) throws RemoteException;
	public String getTest() throws RemoteException;
	public void setTest(String test) throws RemoteException;
}
