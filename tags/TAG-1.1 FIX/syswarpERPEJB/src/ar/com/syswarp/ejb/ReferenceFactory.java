package ar.com.syswarp.ejb;

import javax.naming.*;
import javax.sql.DataSource;
import java.util.*;
import javax.ejb.*;

public class ReferenceFactory {

	private static ReferenceFactory instance = null;

	private Context initialContext;

	private Map references;

	private ReferenceFactory() throws NamingException {
		initialContext = new InitialContext();
		references = Collections.synchronizedMap(new HashMap());
	}

	public static ReferenceFactory getInstance() throws NamingException {
		if (instance == null) {
			instance = new ReferenceFactory();
		}
		return instance;
	}

	// For lookup of Local EJB’s
	public EJBLocalHome lookupByLocalEJBReference(String ejbReferenceComponent)
			throws NamingException {

		java.lang.Object home = references.get(ejbReferenceComponent);
		if (home == null) {
			home = initialContext.lookup("java:comp/env/ejb/"
					+ ejbReferenceComponent);
			references.put(ejbReferenceComponent, home);
		}
		return (EJBLocalHome) home;
	}

	// For look up of Remote EJB’s
	public EJBHome lookupByRemoteEJBReference(String ejbReferenceComponent,
			Class homeClass) throws NamingException {

		java.lang.Object home = references.get(ejbReferenceComponent);
		if (home == null) {
			java.lang.Object obj = initialContext.lookup("java:comp/env/ejb/"
					+ ejbReferenceComponent);
			home = javax.rmi.PortableRemoteObject.narrow(obj, homeClass);
			references.put(ejbReferenceComponent, home);
		}
		return (EJBHome) home;
	}

	public DataSource lookupByDataSourceReference(String jdbcReferenceComponent)
			throws NamingException {
		java.lang.Object dataSource = references.get(jdbcReferenceComponent);
		if (dataSource == null) {
			java.lang.Object obj = initialContext.lookup("java:comp/env/jdbc/"
					+ jdbcReferenceComponent);
			references.put(jdbcReferenceComponent, dataSource);
		}
		return (DataSource) dataSource;
	}
}