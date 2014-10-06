package ar.com.syswarp.ejb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.ejb.Stateless;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.sql.Date;

import org.postgresql.*;
//import javax.sql.*;
import java.util.*;

import org.apache.log4j.*;
import org.postgresql.jdbc2.TimestampUtils;

import ar.com.syswarp.db.Postgres;
import ar.com.syswarp.ejb.*;

/**
 * XDoclet-based session bean. The class must be declared public according to
 * the EJB specification.
 * 
 * To generate the EJB related files to this EJB: - Add Standard EJB module to
 * XDoclet project properties - Customize XDoclet configuration for your
 * appserver - Run XDoclet
 * 
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="CRM" display-name="Name for CRM" description="Description for
 *           Clientes" jndi-name="ejb/CRM" type="Stateless" view-type="remote"
 */
@Stateless
public class EnterpriseBean implements Enterprise {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger log = Logger.getLogger(EnterpriseBean.class);

	String empresa;

	String nombreUsuario;

	String test;

	//transient EJBContext context;

	public EnterpriseBean() {
	}

	// public void setSessionContext(SessionContext sc) {}
	public void ejbRemove() throws EJBException, RemoteException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
		log.info("ACTIVATE");
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	public void setSessionContext(SessionContext context) {
		context = context;
	}
/*
	public SessionContext getSessionContext() {
		return (SessionContext) context;

	}
*/
	public void ejbCreate() throws CreateException {

	}
	

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}
