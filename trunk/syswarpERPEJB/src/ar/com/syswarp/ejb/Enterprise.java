package ar.com.syswarp.ejb;

import java.rmi.RemoteException;

import javax.ejb.Local;

@Local
public interface Enterprise {
	public String getEmpresa() throws RemoteException;
	public void setEmpresa(String empresa) throws RemoteException;
	public String getTest() throws RemoteException;
	public void setTest(String test) throws RemoteException;
}
